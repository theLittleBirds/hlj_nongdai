package com.nongyeos.loan.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.afterturn.easypoi.exception.excel.ExcelExportException;

import com.nongyeos.loan.admin.entity.ReportEntry;
import com.nongyeos.loan.admin.entity.ReportSourcesql;
import com.nongyeos.loan.admin.entity.ReportTemplate;
import com.nongyeos.loan.admin.service.IReportEntryService;
import com.nongyeos.loan.admin.service.IReportSourcesqlService;
import com.nongyeos.loan.admin.service.IReportTemplateService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.ExcelShower;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.ReportUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller  
@RequestMapping("/reportEntry")
public class ReportEntryController {

	@Resource
	private IReportEntryService reportEntryService;
	@Resource
	private IReportTemplateService reportTemplateService;
	@Resource
	private IReportSourcesqlService reportSourceSqlService;
	@Resource
	private ISysSenoService sysSenoService;
	
	private int curYear;
	private int curMonth;
	private String curDay;
	
	public static String outFile_Erro = "Load Template Erro，文件加载失败！！"; 
	FileOutputStream  fileOutputStream  =  null; 
//    HSSFWorkbook workbook = null; 
//    XSSFWorkbook workbook1 = null;
    Workbook wb = null;
    HSSFSheet sheet = null;//2003-
    XSSFSheet   sheet1 = null; //2007+
	
	@RequestMapping(value = "selectByGroupId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ReportEntry> partnerPage(String groupId) {
		PageBeanUtil<ReportEntry> pi = new PageBeanUtil<ReportEntry>();
		try {
			List<ReportEntry> list = reportEntryService.selectByGroupId(groupId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ReportEntry reportEntry){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (reportEntry != null) {
				if (reportEntry.getRptId().equals("") || reportEntry.getRptId()==null) {
					reportEntry.setRptId(sysSenoService.getNextString(Constants.TABLE_NAME_RPTENTRY, 10, "AG", 1));
					reportEntryService.addEntry(reportEntry);
					map.put("msg", "添加成功");
				} else {
					reportEntryService.updateEntry(reportEntry);
					map.put("msg", "修改成功");
				}
			} else {
				map.put("msg", "操作失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(String currIds){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(currIds != null && !currIds.equals("")){
				String[] rptIds = currIds.split(",");
				for(String rptId : rptIds){
					reportEntryService.delRptEntry(rptId);
				}
				map.put("msg", "删除成功");
			}else{
				map.put("msg", "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "selectAll", method = RequestMethod.POST)
	@ResponseBody
	public String applicationList() throws Exception {
		return reportEntryService.appTreeString();
	}
	
	//模板导出
	@RequestMapping(value = "exportModel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> exportModel(HttpServletRequest request,String rptId,String startDate,String expiredDate){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		ReportTemplate reportTemplate;
		List<ReportSourcesql> listReportSourceSql;
		ReportSourcesql sourceSql;
		String sqlstr = "";
		List list = null;
		String startCol = "";			 //起始列号
		Integer startRow = 1;			 //起始行号
		int stepRow = 1;				 //行间距
		int stepCol = 1;				 //列间距
		Short type = 0;				     //类型	
		String hz="";
		String filePathStr = "";
		String fileNameStr;
		try{
			//根据报表id获取模板，数据源，并得到相应的数据
			reportTemplate = reportTemplateService.selectByRptIdAndStatus(rptId,Constants.MODEL_STATUS_OPEN);
			if(reportTemplate != null){
				if(reportTemplate.getFilepath() != null && !"".equals(reportTemplate.getFilepath())){
					//将原始模板copy一份副本到指定位置
//					filePathStr = ReportUtils.string2json(reportTemplate.getFilepath());
					filePathStr = reportTemplate.getFilepath();
					fileNameStr = filePathStr.substring(filePathStr.lastIndexOf("/")+1, filePathStr.length());
	                filePathStr = ReportUtils.copyFileExcel(request,filePathStr,fileNameStr);//copy副本的路径
	                //加载一个模板
	                loadTemplate(filePathStr);
					listReportSourceSql =  reportSourceSqlService.selectByTplIdAndStatus(reportTemplate.getTplId(),Constants.SQL_STATUS_OPEN);
					for(int i = 0; i < listReportSourceSql.size(); i ++){
						if(listReportSourceSql.get(i)!=null){
							sourceSql = listReportSourceSql.get(i);
							sqlstr = sourceSql.getSqlCode();
							//标记--执行sql之前需要对sql中的日期进行处理
							sqlstr = replaceDate(sqlstr,startDate,expiredDate);
							//得到数据源
							list = reportEntryService.doSql(sqlstr);//list里面存放的数据
							//将得到的list数据往excel里面写
							startCol = sourceSql.getStartCol();
							startRow = sourceSql.getStartRow();
							stepRow = sourceSql.getStepRow();
							stepCol = sourceSql.getStepCol();
							type = sourceSql.getType();
							//1--平面式  2--行式  3--列式  4--交叉式  5--混合式
							if(type == 3){
								map = ReportUtils.getDisplayRow(startCol,stepRow,startRow,stepCol,list);
							}else if(type == 2){
								map = ReportUtils.getDisplayCol(startCol,stepRow,startRow,stepCol,list);
							}else{
								map = null;
							}
							//向模板中写入数据
							for(Map.Entry<String, Object> entry:map.entrySet()){
								String key = entry.getKey();
								Object value = (Object) entry.getValue();
								String cell = key.replaceAll("\\d+", "");
								int cell_int = 0;
								if(cell.length() == 1){
									cell_int =changeToNum1(cell) ;
								}
								if(cell.length() == 2){
									cell_int =changeToNum2(cell) ;
								}
								String row = key.replaceAll("\\D+", "");
								writeInTemplate(value,Integer.parseInt(row)-1,cell_int);
							}
							//将处理之后的数据源写入到复制在之后的excel表格副本中，完成统计报表的生成
							SaveTemplate(filePathStr);
						}
					}
//					result.put("msg", "统计报表导出成功,位置："+filePathStr);
					result.put("msg", filePathStr);
				}else{
					result.put("msg", "模板文件不存在，请上传模板！");
				}
			}
		}catch(Exception e){
			//发生异常后，删除已经copy的文件，防止脏数据越堆越多
			File file = new File(filePathStr);
			file.delete();
			result.put("msg", "报表导出异常，请检查sql");
			e.printStackTrace();
		}
		return result;
	}
	private String replaceDate(String sqlstr, String startDate,String expiredDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String today = sdf.format(date);
		if((startDate == null || "".equals(startDate)) && (expiredDate == null || "".equals(expiredDate) )){
			sqlstr = sqlstr.replaceAll("startDate","'1972-01-01'");
			sqlstr = sqlstr.replaceAll("endDate","'"+today+"'");
		}else if(startDate != null && !"".equals(startDate) && expiredDate != null && !"".equals(expiredDate)){
			sqlstr = sqlstr.replaceAll("startDate","'"+startDate+"'");
			sqlstr = sqlstr.replaceAll("endDate","'"+expiredDate+"'");
		}else if(startDate != null && !"".equals(startDate) && (expiredDate == null || "".equals(expiredDate))){
			sqlstr = sqlstr.replaceAll("startDate","'"+startDate+"'");
			sqlstr = sqlstr.replaceAll("endDate","'"+today+"'");
		}else if((startDate == null || "".equals(startDate)) && expiredDate != null && !"".equals(expiredDate)){
			sqlstr = sqlstr.replaceAll("startDate","'1972-01-01'");
			sqlstr = sqlstr.replaceAll("endDate","'"+expiredDate+"'");
		}
		return sqlstr;
	}

	/**
	 * 处理sql语句里面的日期问题
	 * @param srcStr
	 * @return
	 */
	private String replaceVarName(String srcStr)
	{
		String regEx = "(#[^#]*#)";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(srcStr);

		List<String> varList = new ArrayList<String>();
		while(m.find())
		{
			varList.add(m.group());
		}

		for(String var:varList)
		{
			if(var.equalsIgnoreCase("#curYear#"))
				srcStr = srcStr.replaceAll(var, Integer.toString(curYear));
			else if(var.equalsIgnoreCase("#curMonth#"))
				srcStr = srcStr.replaceAll(var, Integer.toString(curMonth));
			else if(var.equalsIgnoreCase("#curDay#"))
				srcStr = srcStr.replaceAll(var, curDay);
			else if(var.equalsIgnoreCase("#lastYear#"))
				srcStr = srcStr.replaceAll(var, Integer.toString(curYear-1));
			else if(var.equalsIgnoreCase("#lastMonth#"))
				srcStr = srcStr.replaceAll(var, Integer.toString(curMonth-1));
		}
		return(srcStr);
	}
	/** 
     * @throws IOException 
	 * @throws FileNotFoundException 
	 * @用途：加载一个已经存在的模板，将生成的内容保存到 workbook中 
     * @参数：String templateFile：指索要加载的模板的路径，如："C:/Tamplates/texting-1.xls" 
     * @用法：templateFile：  String  templateFile_Name1 = "C:/Tamplates/texting-1.xls"          
     */  
    public  void loadTemplate(String templateURL) throws FileNotFoundException, IOException 
    { 
        boolean a = templateURL.trim().indexOf(".xls") == -1; 
        boolean b = templateURL.trim().indexOf(".XLS") == -1; 
        boolean c = templateURL.trim().indexOf(".xlsx") == -1; 
        boolean d = templateURL.trim().indexOf(".XLSX") == -1; 
      if(templateURL == null  || templateURL.trim().equals("") ) 
        { 
            System.out.println("文件不能为空提示"); 
        } 
        else if(a && b && c && d) 
        { 
            System.out.println("文件格式不正确！"); 
             
        } 
        else{ 
            try{ 
            	wb = new HSSFWorkbook(new FileInputStream(templateURL));
                sheet =  (HSSFSheet) wb.getSheetAt(0); 
            }catch(Exception e){
            	wb = new XSSFWorkbook(new FileInputStream(templateURL));
            	sheet1 = (XSSFSheet) wb.getSheetAt(0); 
            }    
        } 
                  
    } 
	 /** 
     * 写入信息 
	 * @throws ParseException 
     * @描述：这是一个实体类，提供了相应的接口，用于操作Excel，在任意坐标处写入数据。 
     * @参数：  String newContent：你要输入的内容 
     *       int beginRow ：行坐标，Excel从 0 算起 
     *       int beginCol ：列坐标，Excel从 0 算起 
     */ 
    public void writeInTemplate(Object data, int beginRow, int beginCell) throws ParseException 
    {   
    	if(data != null && !"".equals(data)){
	    	 Boolean isNum = false;//data是否为数值型
	         Boolean isInteger=false;//data是否为整数
	         Boolean isPercent=false;//data是否为百分数
	    	if(sheet != null){
		        HSSFRow  row  = sheet.getRow(beginRow);  
		        //如果不做空判断，你必须让你的模板文件画好边框，beginRow和beginCell必须在边框最大值以内 
		        //否则会出现空指针异常 
		        if(null == row ){ 
		            row = sheet.createRow(beginRow); 
		        } 
		        HSSFCell   cell   = row.getCell(beginCell); 
		        if(null == cell){ 
		            cell = row.createCell(beginCell); 
		        } 
		       
		        if (data != null || "".equals(data)) {
	                //判断data是否为数值型
	                isNum = data.toString().matches("^(-?\\d+)(\\.\\d+)?$");
	                //判断data是否为整数（小数部分是否为0）
	                isInteger=data.toString().matches("^[-\\+]?[\\d]*$");
	                //判断data是否为百分数（是否包含“%”）
	                isPercent=data.toString().contains("%");
	            }
		        if(isNum && !isInteger && !isPercent){
		        	cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			        cell.setCellValue(Double.parseDouble(data.toString())); 	
		        }
		        else if(!isNum && isInteger && !isPercent){
		        	cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			        cell.setCellValue(Double.parseDouble(data.toString())); 
		        }
		        else if(!isNum && !isInteger && isPercent){
		        	cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			        cell.setCellValue(Double.parseDouble(data.toString())); 
		        }else if(isValidDate(data)){
			        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			        String data0 = data.toString();
	    	    	String data2 =data0.substring(0, 10);
                    cell.setCellValue(data2);
		        }else{
		        	if(data == null || "".equals(data)){
		        		 //设置存入内容为字符串 
				        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
				        //向单元格中放入值 
				        cell.setCellValue("-"); 
		        	}else{
				        //设置存入内容为字符串 
				        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
				        //向单元格中放入值 
				        cell.setCellValue(data.toString());     
		        	}
		        }
	    	}
	    	if(sheet1 != null){
	    		XSSFRow  row  = sheet1.getRow(beginRow);  
		        if(null == row ){ 
		            row = sheet1.createRow(beginRow); 
		        } 
		        XSSFCell   cell   = row.getCell(beginCell); 
		        if(null == cell){ 
		            cell = row.createCell(beginCell); 
		        } 
		        if (data != null || "".equals(data)) {
	                //判断data是否为数值型
	                isNum = data.toString().matches("^(-?\\d+)(\\.\\d+)?$");
	                //判断data是否为整数（小数部分是否为0）
	                isInteger=data.toString().matches("^[-\\+]?[\\d]*$");
	                //判断data是否为百分数（是否包含“%”）
	                isPercent=data.toString().contains("%");
	            }
		        if(isNum && !isInteger && !isPercent){
		        	cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			        cell.setCellValue(Double.parseDouble(data.toString())); 	
		        }
		        else if(!isNum && isInteger && !isPercent){
		        	cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			        cell.setCellValue(Double.parseDouble(data.toString())); 
		        }
		        else if(!isNum && !isInteger && isPercent){
		        	cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			        cell.setCellValue(Double.parseDouble(data.toString())); 
		        }else if(isValidDate(data)){
		        	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        	String data0 = data.toString();
	    	    	String data2 =data0.substring(0, 10);
                    cell.setCellValue(data2);
		        }else{
		        	if(data == null || "".equals(data)){
				        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
				        cell.setCellValue("-"); 
		        	}else{
				        //设置存入内容为字符串 
				        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
				        //向单元格中放入值 
				        cell.setCellValue(data.toString());     
		        	}    
		        }  
	    	}
    	}else{
    		return;
    	}
    } 
    
    private static boolean isValidDate(Object data) {
    	      boolean convertSuccess=true;
    	      try {
    	    	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	    	  String data0 = data.toString();
    	    	  String data1 =data0.substring(0, data0.lastIndexOf("."));
    	    	  Date data2 = format.parse(data1);
    	       } catch (Exception e) {
    	           convertSuccess=false;
    	       } 
    	       return convertSuccess;
	}

	/** 
     * 保存模板 
     * @描述：这个方法用于保存workbook(工作薄)中的内容，并写入到一个Excel文件中 
     * @参数：String templateFile：取得已经保存的类模板 路径名称 
     * @用法： templateFile：String  templateFile_Name1 = "C:/Tamplates/texting-1.xls" 
     *       emplateAdapter ta  = new TemplateAdapter(); 
     *       ta.SaveTemplate(templateFile_Name1); 
     * @param templateFile 
     */ 
    public void SaveTemplate(String templateFile) 
    { 
        try{ 
            //建立输出流 
            fileOutputStream = new FileOutputStream(templateFile); 
            wb.write(fileOutputStream); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        }finally 
        { 
            if(fileOutputStream != null) 
            { 
                try{ 
                    fileOutputStream.close(); 
                }catch(IOException io){ 
                    io.printStackTrace(); 
                } 
            } 
        } 
    } 
    //将A-Z转成1-26
    private int changeToNum1(String cell){
    	 int c = cell.hashCode()-65;
		return c;
    }
    //将AA-ZZ转成数字
    private int changeToNum2(String cell){
    	String first = cell.substring(0,1);
    	String second = cell.substring(1, 2);
    	int f = first.hashCode()-64;
    	int c = second.hashCode()-64;
    	for(int i = 1; i <= 26; i++){
    		if(i == f){
    			c+=26*i;
    		}
    	}
		return c;
    }
    
    /**
	 * 浏览器下载
	 */
	@RequestMapping("/downloadExcel")
	@ResponseBody
	public void downloadExcel(String fileName,HttpServletRequest request, HttpServletResponse response){
		String strPath = fileName;// 文件路径
		if (strPath != null && !"".equals(strPath)) {
			File file = new File(strPath);
			String filename = generateFileName();
			String extension = ".xls";
	        if (file.exists()) {
	            response.setContentType("application/force-download");// 设置强制下载不打开
	            response.addHeader("Content-Disposition", "attachment;fileName=" + filename + extension);// 设置文件名
	            byte[] buffer = new byte[1024];
	            FileInputStream fis = null;
	            BufferedInputStream bis = null;
	            try {
	                fis = new FileInputStream(file);
	                bis = new BufferedInputStream(fis);
	                OutputStream os = response.getOutputStream();
	                int i = bis.read(buffer);
	                while (i != -1) {
	                    os.write(buffer, 0, i);
	                    i = bis.read(buffer);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                deleteFile(strPath);
	            } finally {
	                if (bis != null) {
	                    try {
	                        bis.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                        deleteFile(strPath);
	                    }
	                }
	                if (fis != null) {
	                    try {
	                        fis.close();
	                        deleteFile(strPath);
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                        deleteFile(strPath);
	                    }
	                }
	            }
	        }
		}
	}
	
	/** 以时间生成文件名方法(解决相同文件和并发上传的问题) */
	private String generateFileName() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS"); // 指定格式
		String formDate = format.format(new Date());// 格式化当前时间
		int random = new Random().nextInt(10000);// 生成一个四位数的随机数
		String fileName = formDate + random;
		return fileName;
	}
	
	  /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
            	String filePath = fileName.substring(0, fileName.lastIndexOf("/"));
            	for(int i = 0; i < 3; i ++){
	                filePath = filePath.toString();  
	                java.io.File myFilePath = new java.io.File(filePath);  
	                myFilePath.delete(); //删除空文件夹  
	                filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            	}
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    //只针对简单的报表
    @RequestMapping("/look")
	public ModelAndView rightConfirm(String rptId) {
    	List tableHead = null;
    	List listSqlSource = null;
    	ReportTemplate reportTemplate = null;
    	List<ReportSourcesql> listReportSourceSql = null;
    	ReportSourcesql sourceSql = null;
    	String sqlstr = null;
    	StringBuffer sb=new StringBuffer();
    	String filePath = "";
		try {
			reportTemplate = reportTemplateService.selectByRptIdAndStatus(rptId,Constants.MODEL_STATUS_OPEN);
			listReportSourceSql =  reportSourceSqlService.selectByTplIdAndStatus(reportTemplate.getTplId(),Constants.SQL_STATUS_OPEN);
			for(int i = 0; i < listReportSourceSql.size(); i ++){
				if(listReportSourceSql.get(i)!=null){
					sourceSql = listReportSourceSql.get(i);
					sqlstr = sourceSql.getSqlCode();
					//标记--执行sql之前需要对sql中的日期进行处理
					sqlstr = replaceDate(sqlstr,"","");
					//得到数据源
					listSqlSource = reportEntryService.doLookSql(sqlstr);//list里面存放的数据
				}
			}
			if(reportTemplate != null){
				filePath = reportTemplate.getFilepath();
				File file = new File(filePath);
				if(file.exists()){
					//读取模板文件的头部信息
					tableHead = readExcel(file);
					sb = writeHtmlTable(tableHead,listSqlSource);
				}else{
					throw new Exception("模板文件不存在！！！");
				}
			}else{
				throw new Exception("该统计报表没有模板，请添加！！！");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			throw new Exception("模板文件不存在或者数据源不存在，请检查！！！");
		}
		return new ModelAndView("/report/report_look","sb",sb);
	}
    
    /**
     * 读取Excel，兼容 Excel .xls和.xlsx
     */
    private static List readExcel(File excelFile)
    {
    	List listHead = new ArrayList();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //同时支持Excel 2003、2007
            FileInputStream is = new FileInputStream(excelFile); //文件流
            Workbook workbook = WorkbookFactory.create(is);
            int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
            //遍历每个Sheet
            for (int s = 0; s < sheetCount; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
                if(rowCount != 0)
                {
                	Row row = sheet.getRow(0);
                    int cellCount = row.getPhysicalNumberOfCells();
                    //遍历每一行
                    for (int r = 0; r < rowCount; r++) {
                        row = sheet.getRow(r);
                        if(row != null){
                        //遍历每一列
	                        for (int c = 0; c < cellCount; c++) {
	                            Cell cell = row.getCell(c);
	                            if(cell != null)
	                            {
	                            	int cellType = cell.getCellType();
	                                String cellValue = null;
	                                switch(cellType) {
	                                    case Cell.CELL_TYPE_STRING: //文本
	                                        cellValue = cell.getStringCellValue();
	                                        break;
	                                    case Cell.CELL_TYPE_NUMERIC: //数字、日期
	                                        if(DateUtil.isCellDateFormatted(cell)) {
	                                            cellValue = fmt.format(cell.getDateCellValue()); //日期型
	                                        }
	                                        else {
	                                            cellValue = String.valueOf(cell.getNumericCellValue()); //数字
	                                        }
	                                        break;
	                                    case Cell.CELL_TYPE_BOOLEAN: //布尔型
	                                        cellValue = String.valueOf(cell.getBooleanCellValue());
	                                        break;
	                                    case Cell.CELL_TYPE_BLANK: //空白
	                                        cellValue = cell.getStringCellValue();
	                                        break;
	                                    case Cell.CELL_TYPE_ERROR: //错误
	                                        cellValue = "错误";
	                                        break;
	                                    case Cell.CELL_TYPE_FORMULA: //公式
	                                        cellValue = "错误";
	                                        break;
	                                    default:
	                                        cellValue = "错误";
	                                }
	                                if(cellValue.trim()!=null && !"".equals(cellValue.trim())){
	                                	listHead.add(cellValue.trim());
	                                }
	                            }
	                        }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
           e.printStackTrace();
        }
		return listHead;
    }
    
    private static StringBuffer writeHtmlTable(List tableHead,List<List> tableBody){
    	StringBuffer sb = new StringBuffer();
    	sb.append("<table class=\"table table-bordered\">");
    	sb.append("<thead>");
    	for(int i = 0;i < tableHead.size(); i++){
    		sb.append("<th>"+tableHead.get(i)+"</th>");
    	}
    	sb.append("</thead>");
    	sb.append("<tbody>");
    	for(int i = 0;i < tableBody.size(); i++){
    		sb.append("<tr>");
    		for (int j = 1; j < tableBody.get(i).size(); j++) {
    			if(isValidDate(tableBody.get(i).get(j))){
    				String data0 = tableBody.get(i).get(j).toString();
	    	    	String data2 =data0.substring(0, 10);
	    	    	sb.append("<td>"+data2+"</td>");
    			}else{
    				sb.append("<td>"+tableBody.get(i).get(j)+"</td>");
    			}
			}
    		sb.append("</tr>");
    	}
    	sb.append("</tbody>");
    	sb.append("</table>");
		return sb;
    }
    
    
    //可以针对复杂的报表
    @RequestMapping("/look1")
    public ModelAndView excelShower(HttpServletRequest request,String rptId) {
    	StringBuffer sb=new StringBuffer();
    	Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		ReportTemplate reportTemplate;
		List<ReportSourcesql> listReportSourceSql;
		ReportSourcesql sourceSql;
		String sqlstr = "";
		List list = null;
		String startCol = "";			 //起始列号
		Integer startRow = 1;			 //起始行号
		int stepRow = 1;				 //行间距
		int stepCol = 1;				 //列间距
		Short type = 0;				     //类型	
		String hz="";
		String filePathStr = "";
		String fileNameStr;
		try{
			//根据报表id获取模板，数据源，并得到相应的数据
			reportTemplate = reportTemplateService.selectByRptIdAndStatus(rptId,Constants.MODEL_STATUS_OPEN);
			if(reportTemplate != null){
				if(reportTemplate.getFilepath() != null && !"".equals(reportTemplate.getFilepath())){
					//将原始模板copy一份副本到指定位置
//					filePathStr = ReportUtils.string2json(reportTemplate.getFilepath());
					filePathStr = reportTemplate.getFilepath();
					fileNameStr = filePathStr.substring(filePathStr.lastIndexOf("/")+1, filePathStr.length());
	                filePathStr = ReportUtils.copyFileExcel(request,filePathStr,fileNameStr);//copy副本的路径
	                //加载一个模板
	                loadTemplate(filePathStr);
					listReportSourceSql =  reportSourceSqlService.selectByTplIdAndStatus(reportTemplate.getTplId(),Constants.SQL_STATUS_OPEN);
					for(int i = 0; i < listReportSourceSql.size(); i ++){
						if(listReportSourceSql.get(i)!=null){
							sourceSql = listReportSourceSql.get(i);
							sqlstr = sourceSql.getSqlCode();
							//标记--执行sql之前需要对sql中的日期进行处理
							sqlstr = replaceDate(sqlstr,"","");
							//得到数据源
							list = reportEntryService.doSql(sqlstr);//list里面存放的数据
							//将得到的list数据往excel里面写
							startCol = sourceSql.getStartCol();
							startRow = sourceSql.getStartRow();
							stepRow = sourceSql.getStepRow();
							stepCol = sourceSql.getStepCol();
							type = sourceSql.getType();
							//1--平面式  2--行式  3--列式  4--交叉式  5--混合式
							if(type == 3){
								map = ReportUtils.getDisplayRow(startCol,stepRow,startRow,stepCol,list);
							}else if(type == 2){
								map = ReportUtils.getDisplayCol(startCol,stepRow,startRow,stepCol,list);
							}else{
								map = null;
							}
							//向模板中写入数据
							for(Map.Entry<String, Object> entry:map.entrySet()){
								String key = entry.getKey();
								Object value = (Object) entry.getValue();
								String cell = key.replaceAll("\\d+", "");
								int cell_int = 0;
								if(cell.length() == 1){
									cell_int =changeToNum1(cell) ;
								}
								if(cell.length() == 2){
									cell_int =changeToNum2(cell) ;
								}
								String row = key.replaceAll("\\D+", "");
								writeInTemplate(value,Integer.parseInt(row)-1,cell_int);
							}
							//将处理之后的数据源写入到复制在之后的excel表格副本中，完成统计报表的生成
							SaveTemplate(filePathStr);
						}
					}
					sb.append(getExcelHtml(filePathStr));
					result.put("msg", filePathStr);
					 deleteFile(filePathStr);
				}else{
					result.put("msg", "模板文件不存在，请上传模板！");
				}
			}
		}catch(Exception e){
			//发生异常后，删除已经copy的文件，防止脏数据越堆越多
			File file = new File(filePathStr);
			file.delete();
			result.put("msg", "报表查看异常，请检查数据源是否正确");
			e.printStackTrace();
		}
		return new ModelAndView("/report/report_look","sb",sb);
    }
   	public StringBuffer getExcelHtml(String filePath) {
       	StringBuffer sb=new StringBuffer();
       	List<String[]> listString = new ArrayList<String[]>();
       	String[] everyRow =  null;
   		try {
   				File file = new File(filePath);
   				if(file.exists()){
   					listString = ExcelShower.readExcel(filePath,0,0);
   					sb.append("<table class=\"table table-bordered\" style = \"table-layout:fixed\">");
   					sb.append("<tbody>");
   					for (int i = 0; i < listString.size(); i++) {
   						everyRow = listString.get(i);
   						sb.append("<tr>");
   						for (int j = 0; j < everyRow.length; j++) {
   							sb.append("<td>"+everyRow[j]+"</td>");
						}
   						sb.append("</tr>");
					}
   					sb.append("</tbody>");
   			    	sb.append("</table>");
   		} }catch (Exception e) {
   			e.printStackTrace();
   		}
   		return sb;
   	}
       
}
