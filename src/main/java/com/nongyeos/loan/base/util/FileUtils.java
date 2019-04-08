/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.nongyeos.loan.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    // ExcelType
    public static String EXCEL_TYPE = "application/vnd.ms-excel;";
    // WordType
    public static String WORD_TYPE = "application/vnd.ms-word;";

	/**
	 * 读取文件，返回byte[] 如果不存在，返回null
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static byte[] read(String path) throws IOException {
		int base_size = 1024;
		File file = new File(path);
		// 不存在创建
		if (!file.exists()) {
			return null;
		}

		FileInputStream fis = new FileInputStream(file);
		int len = 0;
		byte[] dataByte = new byte[base_size];

		ByteArrayOutputStream out = new ByteArrayOutputStream(base_size);
		while ((len = fis.read(dataByte)) != -1) {
			out.write(dataByte, 0, len);
		}
		byte[] content = out.toByteArray();

		fis.close();
		out.close();

		// 没有读取到数据
		if (content.length == 0) {
			return null;
		}

		return content;
	}

	/**
	 * 写文件，如果存在，删除
	 * 
	 * @param path
	 * @param data
	 * @throws IOException
	 */
	public static void write(String path, byte[] data) throws IOException {
		File file = new File(path);
		// 不存在，创建
		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream(file);
		fos.write(data);
		fos.flush();
		fos.close();
	}

	/**
	 * 查找当前文件下所有properties文件
	 * 
	 * @param baseDirName
	 *            查找的文件夹路径
	 */
	public static List<String> findFiles(String baseDirName) {
		List<String> files = new ArrayList<String>();
		// 判断目录是否存在
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			System.err.println("search error：" + baseDirName + "is not a dir！");
		} else {
			String[] filelist = baseDir.list();
			for (String fileName : filelist) {
				files.add(fileName);
			}
		}
		return files;
	}

	/**
	 * 查找当前文件下所有properties文件
	 * 
	 * @param baseDirName
	 *            查找的文件夹路径
	 */
	public static List<String> findFileNames(String baseDirName, FileFilter fileFilter) {
		List<String> files = new ArrayList<String>();
		// 判断目录是否存在
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			System.err.println("search error：" + baseDirName + "is not a dir！");
		} else {
			File[] filelist = baseDir.listFiles(fileFilter);
			for (File file : filelist) {
				if (file.isFile())
					files.add(file.getName());
			}
		}
		return files;
	}
	
    /**
     * 获取文件扩展名*
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getExtension(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i < 0) return null;

        return fileName.substring(i+1);
    }

    /**
     * 获取文件扩展名*
     * @param file 文件对象
     * @return 扩展名
     */
    public static String getExtension(File file) {
        if (file == null) return null;

        if (file.isDirectory()) return null;

        String fileName = file.getName();
        return getExtension(fileName);
    }

    /**
     * 读取文件*
     * @param filePath 文件路径
     * @return 文件对象
     */
    public static File readFile(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) return null;

        if (!file.exists()) return null;

        return file;
    }
    /**
     * 复制文件
     * @param oldFilePath 源文件路径
     * @param newFilePath 目标文件毒经
     * @return 是否成功
     */
    public static boolean copyFile(String oldFilePath,String newFilePath) {
        try {
            int byteRead = 0;
            File oldFile = new File(oldFilePath);
            if (oldFile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldFilePath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newFilePath);
                byte[] buffer = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
                fs.close();
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错 ");
            e.printStackTrace();
           return false;
        }
    }

    /**
     *删除文件
     * @param filePath 文件地址
     * @return 是否成功
     */
    public static boolean delFile(String filePath) {
        return delFile(new File(filePath));
    }

    /**
     * 删除文件
     * @param file 文件对象
     * @return 是否成功
     */
    public static boolean delFile(File file) {
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * png图片转jpg*
     * @param pngImage png图片对象
     * @param jpegFile jpg图片对象
     * @return 转换是否成功
     */
    public static boolean png2jpeg(File pngImage, File jpegFile) {
        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(pngImage);

            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            ImageIO.write(bufferedImage, "jpg", jpegFile);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否是图片*
     * @param imgFile 文件对象
     * @return
     */
    public static boolean isImage(File imgFile) {
        try {
            BufferedImage image = ImageIO.read(imgFile);
            return image != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否是视频*
     * @param videoFile  文件对象
     * @return
     */
    public static boolean isVideo(File videoFile){
    	try {

            FileType type = getType(videoFile);
            
            return type == FileType.AVI || 
                    type == FileType.RAM || 
                    type == FileType.RM || 
                    type == FileType.MOV || 
                    type == FileType.ASF ||
                    type == FileType.MPG;
        } catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    /**
     * 根据系统当前时间，返回时间层次的文件夹结果，如：upload/2015/01/18/0.jpg
     * @return
     */
    public static String getTimeFilePath(){
    	return new SimpleDateFormat("/yyyyMMdd").format(new Date())+"/";
    }

    /**
     * 将文件头转换成16进制字符串
     *
     * @param src 原生byte
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     *
     * @param file 文件
     * @return 文件头
     * @throws IOException
     */
    private static String getFileContent(File file) throws IOException {

        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }


    /**
     * 判断文件类型
     *
     * @param file 文件
     * @return 文件类型
     */
    public static FileType getType(File file) throws IOException {

        String fileHead = getFileContent(file);

        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }


    /**
     * 创建目录
     *
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            log.debug("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            log.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            log.debug("目录 " + descDirNames + " 创建失败!");
            return false;
        }

    }

    public static String getSuffix(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return null;
    }

    /**
     * 下载Office文件
     *
     * @param response
     * @param type
     * @param fileName
     * @param path
     * @throws Exception
     */
    public static void downloadOffice(HttpServletResponse response, String type, String fileName, String path) throws Exception {

        InputStream in = null;
        OutputStream out = null;

        try {
            response.setContentType(type + " charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename='" + fileName + "'");

            in = new BufferedInputStream(new FileInputStream(path));
            out = response.getOutputStream();

            byte[] buf = new byte[1024];
            int tempbyte = 0;
            while ((tempbyte = in.read(buf)) != -1) {
                out.write(buf, 0, tempbyte);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * 字节转换KB、MB
     * @param size 文件大小（字节）
     * @return 大于1MB，则转换为MB，否则转换为KB
     */
    public static String convertSize(long size) {
        if (size > 0) {
            if (size / 1024 > 1024) {
                return (long)((double)size / 1024 / 1024 + 0.5) + " MB";
            } else {
                return (long)((double)size / 1024 + 0.5) + " KB";
            }
        } else {
            return "0 KB";
        }
    }

    public static String parseGBK(String sIn) {
        if (sIn == null || sIn.equals(""))
            return sIn;
        try {
            return new String(sIn.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException usex) {
            return sIn;
        }
    }
    
    /*
     * 生成随机文件名  
     */
    public static String generateRandomFilename() {
        String randomFilename = "";
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();

        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        int intHour = calCurrent.get(Calendar.HOUR_OF_DAY);
        int intMinute = calCurrent.get(Calendar.MINUTE);
        int intSecond = calCurrent.get(Calendar.SECOND);
        String now = String.valueOf(intYear) + String.valueOf(intMonth) + String.valueOf(intDay)
                + String.valueOf(intHour) + String.valueOf(intMinute) + String.valueOf(intSecond);
        randomFilename = now + String.valueOf(random > 0 ? random : (-1) * random);

        return randomFilename;
    }
}
