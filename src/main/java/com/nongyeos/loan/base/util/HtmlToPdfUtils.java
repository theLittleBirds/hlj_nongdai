package com.nongyeos.loan.base.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.Charset;

import org.springframework.util.ResourceUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HtmlToPdfUtils {
	
	private static String simheiPath = null;
	
	private static String simsunPath = null;
	
	static{
		try {
			File simheiFile =  ResourceUtils.getFile("classpath:static/font/simhei.ttf");
			simheiPath = simheiFile.getAbsolutePath();
			File simsunFile =  ResourceUtils.getFile("classpath:static/font/simsun.ttc");
			simsunPath = simsunFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void createPdf(String html,String targetPath) throws Exception{
		// step 1 A4纸  页边距 顺序左右上下  
        Document document = new Document(PageSize.A4,50,50,50,50);
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(targetPath)));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider provider = new XMLWorkerFontProvider();
        //注册字体        
        provider.register(simheiPath);
        provider.register(simsunPath);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
        		new ByteArrayInputStream(html.getBytes()), Charset.forName("UTF-8"),provider);
        // step 5
        document.close();
	}
	
	public static void addImageToPdf(String pdfPath,String targetPath,String leftUrl,String rightUrl) throws Exception{
		PdfReader reader = new PdfReader(pdfPath);//指定将和 图片拼接的 PDF
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetPath));//生成的PDF 路径
		PdfContentByte overContent = stamper.getOverContent(1);
		Image image = Image.getInstance(new URL(leftUrl));
		image.setAbsolutePosition(40,530);
		image.scaleAbsolute(250, 180);
		overContent.addImage(image);
		Image image1 = Image.getInstance(new URL(rightUrl));
		image1.setAbsolutePosition(300,530);
		image1.scaleAbsolute(250, 180);
		overContent.addImage(image1);
		overContent.stroke();
		stamper.close();
		reader.close();
	}
}
