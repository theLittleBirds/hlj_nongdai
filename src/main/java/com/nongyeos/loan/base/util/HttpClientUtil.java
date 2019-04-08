package com.nongyeos.loan.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/* 
 * 利用HttpClient进行post请求的工具类 
 */  
public class HttpClientUtil {  
    public static String doPost(String url, Map<String,String> map,String encoding) throws Exception{  
        String body = "";  
        
        //创建httpclient对象  
        CloseableHttpClient client = HttpClients.createDefault();  
        //创建post方式请求对象  
        HttpPost httpPost = new HttpPost(url);  
          
        //装填参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        if(map!=null){  
            for (Entry<String, String> entry : map.entrySet()) {  
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
            }  
        }  
        //设置参数到请求对象中  
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  
  
        System.out.println("请求地址："+url);  
        System.out.println("请求参数："+nvps.toString());  
          
        //设置header信息  
        //指定报文头【Content-type】、【User-Agent】  
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
          
        //执行请求操作，并拿到结果（同步阻塞）  
        CloseableHttpResponse response = client.execute(httpPost);  
        //获取结果实体  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, encoding);  
        }  
        EntityUtils.consume(entity);  
        //释放链接  
        response.close();  
        return body;  
    }
    
    public static String doGet(String url) throws Exception{  
    	HttpClient client = HttpClients.createDefault(); 
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());
    }
    
    //httpclient测试
    public static void main(String[] args) throws Exception {
//    	String url = "http://sandbox.firstpay.com/security/gateway.do";
//    	
//    	Map<String, String> map = new HashMap<String, String>(); 
//    	map.put("service", "REQ_WITHOIDING_BATCH");
//    	map.put("secId", "RSA");
//    	map.put("version", "4.0.0");
//    	map.put("reqSn", "21B843F43C35B52E52BF49536720501C");
//    	map.put("merchantId", "M200000550");
//    	map.put("data", "yrlK3OyQ9PrmHEY7kvYxymnZ9zIuR+qzRzYFasGfcSGU5SYXgauWXUXNDy6ChUW1Jb8WpfKnSClLpEZKx+Bn8lJuU0CyEj2MXH2TCXYv1bMvaJLs8QllUGZtJ3TnA8I2l1nvHypZYLMCCQaB6CTwz2HoX/X9OFaMmqxhHATns4tW5IUTIEu/Fh+a5wGsw9TIZ4KE6Fe2ZWV/0ikPqjVf3JUSzdmvEYYYSYLWE9mHUy3G7QQis/sYqGbWmfh0GFLbpuO+AjAZ/U3Hk7ixqDRVpYgntuB04isf6IckK+tF0FDsbDeI40YFQlD2TAaxzhpm3aidrSdQtXM9BgA/QWjFdjoVgMzgCg20J3wOaJsecKV6ev0Q9Bo78WppIfQesZP+RojdOEIu9HnHK6FVC7O71hVjVyTkOHVMsJo11lPHs2PCpPHUJhxUG3Y4xdxFXiys2zhlkGZ2jL0+EB32ikVmOBZESHro3e6b5DRKWB/8pffEJq7klXYhsf9QpQjVZA2hWBA4t7IOV/BQrEwlOBmBhncwAqmI+/uU60C46+kdhoVAIhwho6UTMr8G4eh4S7kTUzfN+IUquL9KCGw4/oiO2Y71JbzyNN55xB2Mhg+gLIHp4Jd9DVlENCrgnQHdxo3hNg6z0mY1egh55YaXD8bXaH+oeaF/ZBkF65nxe7MeQIvZxE0nsfD3zNMtZ8gVeJvhVQc8A+7MLeY2EjVDTx0WZRtf1kTUk7ThISTRUIIA+JUbYyfL8EfOEIfHvZQFyUYMvY2tGd+Iz1Wli6iluBFTjHW9gJMxS1GJ+P6M2TEWfLG6V51KHHAZgMDEn/rIaKlzyZSfVDq29/1tknP5om/WS2d78/7SWTZ/MslpPXMugl+L3g97xG9qaAwLDwfRm23rrdvCWb2MNMUm1wgVtlzgF+xGCMFRxnpPWLG175EtQhsp+KxDub7zF+hGfuBgjCip3k4kxL4dK61JQd5yefu/d2bCV977VA8JO2UyqOgR4VASUCkYHJUkltK1fhvuYpCTama4oIcc46MWGvKZ5Bb5JzNqu0xZs52+qt/mzoHQuW1evageQ+HEm5dEfYcldNJ95oMgwa0xQpRIb3/g/1UjjO2h6aOW/BcHNJYruUAwNgixmuGaHeWQjGbOatu4eioCPn8T1876J4qrXvt6zypvEAXF1RCmhxcYvz7LMqnWf3IHqhZXvuPLtGBwvJF/owBDl+L7R1bSwbs9+Xx4JEQ+IyE3YOH4DNCpdKmOIr1c7eZEVKxNX1d+IwmotGw7xi2+Yxo8unVAqKHyLjVeuTLbFw==");
//    	map.put("sign", "bsIhyYpF9lp49rILmc0mDeTI6xEcl/1fyz2/JhWXQ9qp9OTU5wk3Z2PRHaey7KZvXqFUh9EfvK6VjSszJZlER1Wa3Fu8JQV6wvt15ju/ASeLzqSiG+SPGgrxLRpYa1DKY1ah0FrnDTqvQVSA+cGLWjpZVawDT4M5omkTLW0NAKU=");
//
//    	String body = HttpClientUtil.doPost(url, map,"utf-8");  
//        System.out.println("交易响应结果：");  
//        System.out.println(body);  
        
//        String decrypt = AESCoder.decrypt(
//        		"yrlK3OyQ9PrmHEY7kvYxymnZ9zIuR+qzRzYFasGfcSEHSkpbbb5YWUr1ZB+fiMtsZsmsBuuC7KO7z531xxyeMwN9La8RURIB3sOIZjh1VfxlXAgntsZUXmpRqJ0TZ1jGLSkU+SGzDLrvL6F1QTOFSQ==", 
//        		"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChFetx5+VKDoEXzZ+5Wozt3MfWMM/TiKMlWmAKXBViv8/e6j6SU/lSlWkMajd59aiWczs+qf9dMuRpe/l9Qke9DnVMn24JNLXjWD+y+w3yKRwd3CTtF7gx8/ToZl5XqFIT5YB1QfQCdAf8Z18IdQrJIijs8ssczY/RfqKZLo+KLQIDAQAB");
    }
    
}  