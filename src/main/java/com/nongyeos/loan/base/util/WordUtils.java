package com.nongyeos.loan.base.util;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;

import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.WordImageEntity;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by maf on 2017/3/27.
 */
public class WordUtils {

	private static final Logger log = LogManager.getLogger(WordUtils.class);

    /**
     * 读取Word模板并填入数据，保存到本地
     *
     * @param tmpPath    模板文件路径（包含后缀）
     * @param map        数据
     * @param targetPath 目标文件路径
     * @return 返回保存状态
     * @throws Exception
     */
    public static JSONObject writeWordToLocal(String tmpPath, Map<String, Object> map, String targetPath) {

        JSONObject res = new JSONObject();

        try {
            // 模板文件路径
            XWPFDocument document = WordExportUtil.exportWord07(tmpPath, map);

            // 写入新文件
            FileOutputStream fos = new FileOutputStream(targetPath);
            document.write(fos);
            fos.close();

            res.put("code", 200);
            res.put("msg", targetPath);
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.info("====================================合同写入本地异常：" + sw.toString());

            res.put("code", 400);
            res.put("msg", e.getMessage());
            return res;
        }

    }
    public static void main(String[] args) {
    	Map<String, Object> map = new HashMap<String, Object>();   
         map.put("address", "北京海淀区");
         map.put("main", "李测试");
         map.put("name", "张测试");
         map.put("relation", "兄弟");
         map.put("no", "123");
         map.put("time", "2018年10月1日");
         writeWordToLocal("F:\\sqsx.docx",map,"F:\\模板.docx");
	}
}
