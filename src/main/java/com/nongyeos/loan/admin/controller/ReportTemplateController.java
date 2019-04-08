package com.nongyeos.loan.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nongyeos.loan.admin.entity.ReportEntry;
import com.nongyeos.loan.admin.entity.ReportRptgroup;
import com.nongyeos.loan.admin.entity.ReportTemplate;
import com.nongyeos.loan.admin.service.IReportTemplateService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/reportTemplate")
public class ReportTemplateController {

	@Resource
	private IReportTemplateService reportTemplateService;

	@Resource
	private ISysSenoService sysSenoService;

	@RequestMapping(value = "selectByReptId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ReportTemplate> partnerPage(String rptId) {
		PageBeanUtil<ReportTemplate> pi = new PageBeanUtil<ReportTemplate>();
		try {
			List<ReportTemplate> list = reportTemplateService
					.selectByRptId(rptId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ReportTemplate reportTemplate) {
		// 提交
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String rptId = reportTemplate.getRptId();
			Short status = reportTemplate.getStatus();
			String tplId = reportTemplate.getTplId();
			//删除老模板，清理脏数据
			ReportTemplate reportTemplate1 =reportTemplateService.selectByTplId(tplId);
			if(reportTemplate1 != null){
				String oldFilePath = reportTemplate1.getFilepath();	
				if(oldFilePath != null && !"".equals(oldFilePath)){
					File file  = new File(oldFilePath);
					if(file.exists()){
						file.delete();
					}
				}
			}
			if (status == 1
					&& reportTemplateService.existenceRptId(rptId, tplId)) {
				map.put("msg", "已有模板被启用");
			} else {
				if (reportTemplate != null) {
					if (reportTemplate.getTplId().equals("")
							|| reportTemplate.getTplId() == null) {
						reportTemplate
								.setTplId(sysSenoService.getNextString(
										Constants.TABLE_NAME_RPTTEMPLATE, 10,
										"TPL", 1));
						reportTemplateService.addTemplate(reportTemplate);
						map.put("msg", "添加成功");
					} else {
						reportTemplateService.updateTemplate(reportTemplate);
						map.put("msg", "修改成功");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] templateIds = currIds.split(",");
				for (String templateId : templateIds) {
					reportTemplateService.delRptTemplate(templateId);
				}
				map.put("msg", "删除成功");
			} else {
				map.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "getTemplateDS", method = RequestMethod.POST)
	@ResponseBody
	public String getTemplateDS(String rptId) throws Exception {
		String strJson = "[";
		List<ReportTemplate> list = reportTemplateService.selectByRptId(rptId);
		if (list != null && list.size() > 0) {
			ReportTemplate beanTemp = null;
			for (int i = 0; i < list.size(); i++) {
				beanTemp = list.get(i);
				if (beanTemp != null) {
					if (i > 0)
						strJson = strJson + ", ";

					strJson = strJson + "{parameterName:'" + beanTemp.getName()
							+ "', parameterValue:'" + beanTemp.getTplId()
							+ "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}

	@RequestMapping(value = "upload")
	public @ResponseBody String upload(HttpServletRequest request,MultipartFile file) throws IllegalStateException, IOException {
		String str = "";
		try{
			String name = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath("/upload/");// 上传保存的路径
			String dividerSign = "";
			String osName = System.getProperties().getProperty("os.name"); // 操作系统名称
			if (osName.indexOf("Linux") != -1) {
				path = Constants.UPLOAD_FILE_ROOT_Linux;
				dividerSign = "/";
			} else if (osName.indexOf("Windows") != -1) {
				path = Constants.UPLOAD_FILE_ROOT_Windows;
				dividerSign = "/";
			}
			String fileName = generateFileName(name);
			String filePath = path + dividerSign;
			// 创建文件夹
			File fileFolder = new File(filePath); // new file;
			// 判断文件夹是否存在
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			fileName = path + dividerSign + fileName;
			fileName = new String(fileName.getBytes(),"utf-8");

//			File file1 = new File(fileName);
//			file.transferTo(file1);
			File oldFile = new File(fileName);
				try {	
					if(!oldFile.exists()){
						oldFile.createNewFile();
						FileOutputStream fos = new FileOutputStream(oldFile);
				        byte[] f = file.getBytes();
				        fos.write(f); 
				        fos.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			str = "{\"src\":\"" + fileName + "\"}";
			System.out.println(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}

	/** 以时间生成文件名方法(解决上传相同文件和并发上传的问题) */
	private String generateFileName(String fileName) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HHmmss"); // 指定格式
		String formDate = format.format(new Date());// 格式化当前时间
		int random = new Random().nextInt(10000);// 生成一个四位数的随机数
		String extension = fileName.substring(fileName.lastIndexOf("."));// 得到文件的后缀
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		return "[" + fileName + "]" + formDate + random + extension;
	}
}
