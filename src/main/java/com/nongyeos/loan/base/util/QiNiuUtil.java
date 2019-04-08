package com.nongyeos.loan.base.util;

import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.nongyeos.loan.admin.entity.BusMedia;
import com.nongyeos.loan.admin.entity.BusMemberOperateMedia;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiNiuUtil {
	
	private static String AccessKey = "YjbWFZ2IYF365zFCNvvrwGXXAfrx8M5p1F3SrCSB";
	private static String SecretKey = "VIyyVsGizOBu22FVLCLfpa11zqMxeGMLnKRrZZdV";
	private static String bucket = "image";
	private static String url = "http://s.boyashanshui.com/";
	private static String limit10k = "?imageMogr2/thumbnail/10240@";
	
	public static String domainUrl(){
		return url;
	}

	/**
	 * 
	 * @return 移动端上传文件凭证
	 */
	public static String upToken(){
	    Auth auth = Auth.create(AccessKey, SecretKey);
	    return auth.uploadToken(bucket);
	}
	
	/**
	 * 上传文件到七牛，返回hash key
	 */
	public static String upLoadFile(byte[] file){
		//构造一个带指定Zone对象的配置类
	    Configuration cfg = new Configuration(Zone.zone0());
	    //...其他参数参考类注释
	    UploadManager uploadManager = new UploadManager(cfg);
	    //...生成上传凭证，然后准备上传
	    //默认不指定key的情况下，以文件内容的hash值作为文件名
	    String key = null;
	    Auth auth = Auth.create(AccessKey, SecretKey);
	    String upToken = auth.uploadToken(bucket);
	    try {
	        Response response = uploadManager.put(file, key, upToken);
	        //解析上传成功的结果
	        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
	        return putRet.key;
	    } catch (QiniuException ex) {
	        Response r = ex.response;
	        System.err.println(r.toString());
	        try {
	            System.err.println(r.bodyString());
	        } catch (QiniuException ex2) {
	            //ignore
	        }
	    }
	    return null;
	}
	/**
	 * @param rount 图片字节流方式上传七牛
	 * @return 七牛文件key：即上传图片指定的名字，默认不指定key的情况下，以文件内容的hash值作为文件名
	 */
	public static String upLoadByteData(byte[] rount, String key){
		//构造一个带指定Zone对象的配置类
	    Configuration cfg = new Configuration(Zone.zone0());
	    //...其他参数参考类注释
	    UploadManager uploadManager = new UploadManager(cfg);
	    //...生成上传凭证，然后准备上传
	    Auth auth = Auth.create(AccessKey, SecretKey);
	    String upToken = auth.uploadToken(bucket);
	    try {
	        Response response = uploadManager.put(rount, key, upToken);
	        //解析上传成功的结果
	        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
	        return putRet.key;
	    } catch (QiniuException ex) {
	        Response r = ex.response;
	        System.err.println(r.toString());
	        try {
	            System.err.println(r.bodyString());
	        } catch (QiniuException ex2) {
	            //ignore
	        }
	    }
	    return null;
	}	
	/**
	 * 根据七牛key返回文件地址
	 * @param key
	 * @return
	 */
	public static String getUrl(String key){	
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		return auth.privateDownloadUrl(url+key, expireInSeconds);	
	}
	
	/**
	 * 根据七牛key返回文件地址
	 * @param key
	 * @return
	 */
	public static String getJpgUrl(String key){	
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		key = key +"?attname="+URLEncoder.encode(key+".jpg");
		return auth.privateDownloadUrl(url+key, expireInSeconds);	
	}
	
	/**
	 * 根据七牛key返回文件地址
	 * @param list
	 * @return
	 */
	public static List<String> getUrl(List<String> list){	
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		List<String> path = new LinkedList<String>();
		for(int i = 0; i<list.size();i++){
			String url_path = auth.privateDownloadUrl(url+list.get(i), expireInSeconds);
			path.add(url_path);
		}
		return path;	
	}
	/**
	 * 根据七牛key返回文件地址
	 * @param list
	 * @return
	 */
	public static List<BusMedia> getUrlByMedia(List<BusMedia> list){	
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		for(int i = 0; i<list.size();i++){
			String key = list.get(i).getQiniuKey();
			if(key != null && !"".equals(key)){
				String url_path = auth.privateDownloadUrl(url+key, expireInSeconds);
				list.get(i).setQiniuKey(url_path);
			}	
		}
		return list;	
	}
	/**
	 * 根据七牛key返回png后缀的图片
	 * @param list
	 * @return
	 */
	public static JSONArray getUrlEndPNG(List<BusMedia> list){
		JSONArray arr = new  JSONArray();
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		for(int i = 0; i<list.size();i++){
			String key = list.get(i).getQiniuKey();
			if(key != null && !"".equals(key)){
				key = key +"?attname="+URLEncoder.encode(key+".jpg");
				String big = auth.privateDownloadUrl(url+key, expireInSeconds);
				String small = auth.privateDownloadUrl(url+key+"&imageMogr2/thumbnail/10240@", expireInSeconds);
				JSONObject json = new JSONObject();
				json.put("id", list.get(i).getId());
				json.put("big", big);
				json.put("small", small);
				json.put("fileType", list.get(i).getFileType());
				json.put("creDate", list.get(i).getCreDate());
				arr.add(json);
			}	
		}
		return arr;	
	}
	
	public static JSONArray getUrlEndJpg(List<BusMemberOperateMedia> list){	
		JSONArray arr = new  JSONArray();
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		for(int i = 0; i<list.size();i++){
			String key = list.get(i).getQiniuKey();
			if(key != null && !"".equals(key)){
				key = key +"?attname="+URLEncoder.encode(key+".jpg");
				String big = auth.privateDownloadUrl(url+key, expireInSeconds);
				String small = auth.privateDownloadUrl(url+key+"&imageMogr2/thumbnail/10240@", expireInSeconds);
				JSONObject json = new JSONObject();
				json.put("id", list.get(i).getId());
				json.put("big", big);
				json.put("small", small);
				json.put("creDate", list.get(i).getCreDate());
				arr.add(json);
			}	
		}
		return arr;	
	}
	
	public static JSONArray getUrlEndJpg(JSONArray imageArr){	
		JSONArray arr = new  JSONArray();
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		for(int i = 0; i<imageArr.size();i++){
			JSONObject obj = (JSONObject) imageArr.get(i);
			String key = obj.getString("key");
			if(key != null && !"".equals(key)){
				key = key +"?attname="+URLEncoder.encode(key+".jpg");
				String big = auth.privateDownloadUrl(url+key, expireInSeconds);
				String small = auth.privateDownloadUrl(url+key+"&imageMogr2/thumbnail/10240@", expireInSeconds);
				JSONObject json = new JSONObject();
				json.put("id", obj.getString("id"));
				json.put("big", big);
				json.put("small", small);
				json.put("fileType", obj.getInteger("fileType"));
				arr.add(json);
			}	
		}
		return arr;	
	}
	
	/**
	 * 根据七牛key返回文件地址,带文件格式，如果数据库中没有保存格式，跳过这个文件
	 * @param list
	 * @return
	 */
	public static List<BusMedia> getFileUrl(List<BusMedia> list){	
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		for(int i = 0; i<list.size();i++){
			String key = list.get(i).getQiniuKey();
			String ext = list.get(i).getExtName();
			String name = list.get(i).getFileTypeName();
			if(name == null)
				name = "文件";
			if(key != null && !"".equals(key)){
				if(ext != null && !"".equals(ext)){
					key = key +"?attname="+URLEncoder.encode(name+"."+ext);
					String url_path = auth.privateDownloadUrl(url+key, expireInSeconds);
					list.get(i).setQiniuKey(url_path);
				}		
			}	
		}
		return list;	
	}
	/**
	 * 根据七牛key返回10k以内文件地址
	 * @param key
	 * @return
	 */
	public static String getUrlLimit10k(String key){
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		return auth.privateDownloadUrl(url+key+limit10k, expireInSeconds);	
	}
	/**
	 * 根据七牛key返回10k以内文件地址
	 * @param list
	 * @return
	 */
	public static List<String> getUrlLimit10k(List<String> list){
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		List<String> path = new LinkedList<String>();
		for(int i = 0; i<list.size();i++){
			String url_path = auth.privateDownloadUrl(url+list.get(i)+limit10k, expireInSeconds);
			path.add(url_path);
		}
		return path;	
	}
	/**
	 * 根据缩略图地址获取原图地址
	 * @param thumbnailUrl
	 * @return
	 */
	public static String getOriginalByThumbnail(String thumbnailUrl){
		if(thumbnailUrl.startsWith(url)){
		String OriginalUrl = thumbnailUrl.substring(0, thumbnailUrl.indexOf("?"));
		Auth auth = Auth.create(AccessKey, SecretKey);
		long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
		return auth.privateDownloadUrl(OriginalUrl, expireInSeconds);
		}else{
			return thumbnailUrl;
		}
	}
}

