package com.nongyeos.loan.base.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util
{
	
	
	// 获取指定文件二进制数据Base64编码字符串
	public static String file2Str(String filePath)
	{
		InputStream in = null;
		byte[] data = null;
	        
        // 读取文件字节数组
        try
        {
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
        	try
        	{
        		in.close();
        	}
        	catch(IOException e)
        	{
        		e.printStackTrace();
        	}
        }
        
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return(encoder.encode(data));	// 返回Base64编码过的字节数组字符串
	}
	
	
	// 生成指定路径文件由二进制数据Base64编码字符串
//	public static boolean str2File(String fileStr, String filePath)
//	{
//		OutputStream out = null;
//		
//		filePath = filePath + "test/";
//		
//		if(fileStr == null)
//		{
//			// 文件数据字符串为空
//			return(false);
//		}
//            
//        BASE64Decoder decoder = new BASE64Decoder();
//        try
//        {
//            // Base64解码
//            byte[] bytes = decoder.decodeBuffer(fileStr);
//            for(int i = 0; i < bytes.length; i++)
//            {
//                if(bytes[i] < 0)
//                {	// 调整异常数据
//                    bytes[i] += 256;
//                }
//            }
//            
//            // 生成文件
//            out = new FileOutputStream(filePath);
//            out.write(bytes);
//            out.flush();
//        
//            return(true);
//        }
//        catch(Exception e)
//        {
//        	e.printStackTrace();
//            return(false);
//        }
//        finally
//        {
//        	try
//        	{
//        		out.close();
//        	}
//        	catch(Exception e)
//        	{
//        		e.printStackTrace();
//        	}
//        }
//	}
		//将base64转成二进制，将二进制转成file文件
		public static String str2File(String imgStr,String imageName)  
	    {   //对字节数组字符串进行Base64解码并生成图片  
	        if (imgStr == null) //图像数据为空  
	            return "0";  
	        BASE64Decoder decoder = new BASE64Decoder();  
	        try   
	        {  
	            //Base64解码  
	            byte[] b = decoder.decodeBuffer(imgStr);  
	            for(int i=0;i<b.length;++i)  
	            {  
	                if(b[i]<0)  
	                {//调整异常数据  
	                    b[i]+=256;  
	                }  
	            }  
	            //生成jpeg图片  
	            imageName = imageName+System.currentTimeMillis()+".jpg";
	            String imgFilePath = imageName;//新生成的图片  
	            OutputStream out = new FileOutputStream(imgFilePath);      
	            out.write(b);  
	            out.flush();  
	            out.close();  
	            return imageName;  
	        }   
	        catch (Exception e)   
	        {  
	            return "0";  
	        }  
	    }  
	 
	public static List stringUitls(String base64File){
		List<String> listFiles = new ArrayList<String>();
		String[] files = base64File.split(",");
		for (int i = 0; 2*i+1 < files.length; i++) {
			String[] file = files[2*i+1].split("\"");
			if(file != null){
				listFiles.add(file[0]);
			}
		}
		return listFiles;
	}
}
