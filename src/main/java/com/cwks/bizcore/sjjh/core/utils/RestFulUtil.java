package com.cwks.bizcore.sjjh.core.utils;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.cwks.bizcore.sjjh.core.utils.ComProConf;
import com.cwks.bizcore.sjjh.core.utils.RestfulHttpClient;
import com.cwks.common.log.LogWritter;

public class RestFulUtil {

	public static JSONObject sendJhdlService(String reqXml, String clientIp, String resourceId) {
		 	String result=null;
		 	JSONObject obj=new JSONObject();
		    RestfulHttpClient.HttpClient client = RestfulHttpClient.getClient(ComProConf.JHDL_RESTAPI_WBFW_URL);
		    LogWritter.bizDebug("\n client:::::::::::::::::"+client.getUrl());
	        client.post();
	        client.connectTimeout(20000);
	        client.readTimeout(60*10000);
	        client.addHeader("Content-type", "application/json;charsert=UTF-8");
	        client.addHeader("ACCESS-CLIENT-ID", "GOV.JS.SW.SJ.JRKZ");
	        client.addHeader("REQUSET-CLIENT-IP", clientIp);
	        client.addHeader("RESOURCE-ID", resourceId);
	        client.body(reqXml);
	        //发起请求，获取响应结果
	        RestfulHttpClient.HttpResponse response = null;
	        try {
	            response = client.request();
	        } catch (IOException e) {
	        	result=e.toString();
	        	LogWritter.bizDebug("\n调用restful接口异常::::::::::::::::::::::"+e.toString());
	        }
	        //根据状态码判断请求是否成功
			if(response != null) {
				if (response.getCode() == 200) {
					//获取响应内容
					result = response.getContent();
					obj = (JSONObject) JSONObject.parse(result);
				}
			}
			return obj;
	}
}
