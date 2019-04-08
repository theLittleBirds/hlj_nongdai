package com.nongyeos.loan.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.nongyeos.loan.admin.model.ItemCellMgr;


public class ReportUtils {
	/**
	 * 初始字符:cellch 
	 * 字符步长:stepT
	 * 
	 * 初始下标:initC
	 * 下标步长:stepC
	 * */
	public static Map<String, Object> getDisplayRow(String str, int stepT, int initC, int stepC, List list)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		String display="";
		int k=1;
		int j=1;
		int ascii = 0;
		int m=0;
		char cell = '0';
		char cellch=0;
		String csllStr="";
		
		char[] cellchArray=str.toCharArray();
		if(cellchArray.length >1){
			cell =  cellchArray[0];
			ascii =  (int)cellchArray[1];
			m = (int)cellchArray[1];
		}
		else
		{
			ascii = (int) cellchArray[0];
		}
		
		if(list.size()>0)
		{
			j = initC;
			
			for(int i=0;i<list.size();i++)
			{
//				Object[] objs =  list.toArray(new Object[list.size()]);
				Object[] objs = (Object[]) list.get(i);
				//循环所有数据字段
				k = ascii;
				if(cellchArray.length >1)
				{
					m = (int)cellchArray[1];
					cell =  cellchArray[0];
				}
				else
				{
					m=0;
					cell = '0';
				}
				for(int l=0;l<objs.length;l++)
				{
					Integer addCell = null;
					try{
						Double d = (Double) objs[0];
						addCell = (new Double(d)).intValue();
					}catch(Exception e){
						 Long lb=new Long((long) objs[0]);
						 addCell = lb.intValue();
					}
				
					if(l >0){
						j = initC+stepT*(addCell-1);
						
						if(k >90)
						{
							//cell = (char) (65+m-90+1);
							//2015-05-23 如果第一位超过A  就需要重新计算第一位减去90再加上A的1
							if(m<65)
							{
								cell = (char) (65+m);
							}else {
								cell = (char) (65+m-90+1);
							}
							//k=65+k-91;
							k -=26;
							m++;
						}
						cellch = (char)k;
						
						if(cell != '0')
						{
							csllStr = String.valueOf(cell)+ String.valueOf(cellch);
						}
						else
						{
							csllStr = String.valueOf(cellch);
						}
					
						if("".equals(display))
						{
							display = "{cell:'"+csllStr+j+"',value:'"+ objs[l] +"'}";
							map.put(csllStr+j,objs[l]);
						}
						else
						{
							display += ",{cell:'"+csllStr+j+"',value:'"+ objs[l] +"'}";
							map.put(csllStr+j,objs[l]);
						}
						
						k += stepC;
					}
				}
			}
		}
		else
		{
			display = "{}";
		}
		return map;
	}
	
	
	/**
	 * 初始字符:cellch 
	 * 字符步长:stepT
	 * 
	 * 初始下标:initC
	 * 下标步长:stepC
	 * */
	public static Map<String, Object> getDisplayCol(String initCol, int stepRow, int initRow, int stepCol, List list)
	{
		ItemCellMgr icMgr = null;
		String display = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(int i=0;i<list.size();i++)
		{
			Object[] objs = (Object[]) list.get(i);
			
			if(i == 0)
			{
				icMgr = new ItemCellMgr((short)3, initRow, initCol, stepRow, stepCol, objs.length-1, null);
			}
			
			String[] cells = null;
			for(int j=0;j<objs.length;j++)
			{
				if(j == 0)
				{
					try{
						Double d = (Double) objs[j];
						cells = icMgr.getCells(((new Double(d)).intValue())-1);
					}catch(Exception e){
						 Long lb=new Long((long) objs[j]);
						cells = icMgr.getCells(lb.intValue()-1);
					}
				}
				else
				{
					map.put(cells[j-1], objs[j]);
					if(display == null)
					{
						display = "{cell:'" + cells[j-1] + "',value:'" + objs[j] + "'}";
					}
					else
					{
						display += ",{cell:'" + cells[j-1] + "',value:'" + objs[j] + "'}";
					}
				}
			}
		}
		
		if(display == null)
		{
			display = "{}";
		}
//		return(display);
		return map;
	}
	
	//拷贝文件到指定位置
		public static String copyFileExcel(HttpServletRequest request,String srcFileName,String filename) throws IOException
		{
			String path = request.getServletContext().getRealPath("/upload/");// 上传保存的路径
			String dividerSign = "";
//			String filePath = "";
			String osName = System.getProperties().getProperty("os.name"); // 操作系统名称
			if (osName.indexOf("Linux") != -1) {
				path = Constants.UPLOAD_FILE_ROOT_Linux;
				dividerSign = "/excel/template";
//				filePath ="/disk/appdata/prorms/excel/template";
			} else if (osName.indexOf("Windows") != -1) {
				path = Constants.UPLOAD_FILE_ROOT_Windows;
				dividerSign = "/excel/template";
//				filePath ="E:/prorms/file/excel/template";
			}
			String filePath = path + dividerSign;
			
			//创建文件夹
			File fileFolder =new File(filePath);	//new file;
			//判断文件夹是否存在
			if (!fileFolder.exists()) 
			  {
				fileFolder.mkdirs(); 
			  }
			filename = generateFileName(filename);
			filePath = filePath + "/" + filename;
			
			InputStream is = null;
			FileOutputStream os = null;
			int byteread = 0;
			byte[] buff = new byte[1024];
			File oldFile = new File(srcFileName);
			try {	
				if(oldFile.exists()){
					is = new FileInputStream(srcFileName);
					os = new FileOutputStream(filePath);
					while ( (byteread = is.read(buff)) != -1) {
						os.write(buff, 0, byteread);
						}
				}else{
					System.out.println("linux下模板文件不存在");
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
					is.close();
					os.close();	
				}
			return filePath;
		}
		
		/**以时间生成文件名方法(解决上传相同文件和并发上传的问题)*/
		private static String generateFileName(String fileName)
		{
			int random =new Random().nextInt(10000);//生成一个四位数的随机数
			String extension = fileName.substring(fileName.lastIndexOf("."));//得到文件的后缀
			String houzhui = ".xls";
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			return  fileName +"-副本"+random+ extension;
		}
		
		public static String string2json(String s) {
			if (s == null)
				return "";
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				char ch = s.charAt(i);
				switch (ch) {
				case '"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '/':
					sb.append("\\/");
					break;
				default:
					if (ch >= '\u0000' && ch <= '\u001F') {
						String ss = Integer.toHexString(ch);
						sb.append("\\u");
						for (int k = 0; k < 4 - ss.length(); k++) {
							sb.append('0');
						}
						sb.append(ss.toUpperCase());
					} else {
						sb.append(ch);
					}
				}
			}
			return sb.toString();
		}
		
		public static void updateExcel(String srcStr,String displayStr) throws Exception
		{
			//-------------------------------------------获得excel文件和创建副本文件-----------------------------------------------------------------
			// Excel获得文件
	        Workbook wb = Workbook.getWorkbook(new File(srcStr));
	        // 打开一个文件的副本，并且指定数据写回到原文件
	        WritableWorkbook book = Workbook.createWorkbook(new File(srcStr),wb);
	        // 添加一个工作表
	        WritableSheet sheet = book.getSheet(0);
	        //------------------------------------------------------------------------------------------------------------
	        
	        //解析json数组
			JSONArray jsonArray = JSONArray.fromObject(displayStr);
			HashMap<String, Object> map = new HashMap<String, Object>(); 
			map = returnCellMap(jsonArray);
			 
			String cellValue="";
			int cellN = 0;
			int cellN2 = 0;
			int kk=0;
			for(String key : map.keySet())
			{
				cellValue = (String) map.get(key);
				if(cellValue == null || cellValue.equalsIgnoreCase("NULL") )
				{
					continue;
				}
				
				//字母
				String z = key.replaceAll("\\d+", "");
				 //数字
				String n = key.replaceAll("\\D+", "");
				
				char[] cellchArray=z.toCharArray();
				if(cellchArray.length >1){
					cellN =  (int)cellchArray[0];
					cellN2 =  (int)cellchArray[1];
					cellN = (cellN -65+1)*26;
					cellN2 = cellN2-65;
					cellN = cellN + cellN2;
				}
				else
				{
					cellN = (int) cellchArray[0];
					cellN = cellN -65;
				}
				WritableCell cell = null;  
			    Label label = null;  
			    
			    //获得原cell
			    cell = sheet.getWritableCell(cellN,Integer.valueOf(n)-1);
			    
			    
			    cell =getWritableCell(sheet,cellN,Integer.valueOf(n)-1,cellValue,cell.getCellFormat());
			    sheet.addCell(cell);
			    kk++;
			} 
	        //写入
	        book.write();
	        //关闭
	        book.close();
		}
		
		/**
		 * 根据不同的属性类型，返回相应的WritableCell
		 * 
		 * @param col           列号
		 * @param row           行号
		 * @param data          内容
		 * @param typeName      类型
		 * @return              与类型一致的cell
		 */
		private static WritableCell getWritableCell(WritableSheet sheet,Integer col,Integer row,String vl,CellFormat cellFormat){
			WritableCell result = null;
			if (vl != null) {
				if(isNumeric(vl))
				{
					result = new jxl.write.Number(col, row, Double.valueOf(vl.trim()));
				}else {
					vl=vl.replaceAll("#%%#%#", "'");
					result = new Label(col, row, vl);
				}
			} else {
				result = new EmptyCell(col,row);
			}
			 if(cellFormat != null)
			 {
				 result.setCellFormat(cellFormat);
			 }
			return result;
		}
		//判断是否为数字
		private static  boolean isNumeric(String str){ 
			try { 
				str = str.trim();
				Boolean strResult =true;
				//正则表达式 判断是否为小数(正负)
				//strResult = str.matches("-?[0-9]+.[0-9]{0,6}");
				BigDecimal   b   =   new   BigDecimal(str); 
				
			    return strResult;
			} catch (NumberFormatException e) { 
				return false; 
			} 
		}
		
		//解析display中所有单元格对应的值
		private static HashMap<String, Object> returnCellMap(JSONArray jsonArray)
		{
			HashMap<String, Object> map = new HashMap<String, Object>(); 
			String cellStr ="";
			String valueStr ="";
			JSONObject info=null;
			
			for(int i=0;i<jsonArray.size();i++)
			{
				info=null;
				info = jsonArray.getJSONObject(i);
				if(info != null && info.size()>0)
				{
					try {
						cellStr=info.getString("cell");
						valueStr=info.getString("value");
						if(cellStr !=null && !cellStr.equals(""))
						{
							map.put(cellStr, valueStr);
						}
					} catch (Exception e) {
						continue;
					}
				}
			}
			return map;
		}
}
