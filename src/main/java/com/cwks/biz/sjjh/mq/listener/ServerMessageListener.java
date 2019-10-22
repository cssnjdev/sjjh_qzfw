package com.cwks.biz.sjjh.mq.listener;

import com.alibaba.fastjson.JSONObject;
import com.cwks.bizcore.sjjh.core.service.JhQzFwService;
import com.cwks.bizcore.sjjh.core.utils.RestfulHttpClient;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.log.LogWritter;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ServerMessageListener implements ChannelAwareMessageListener {

    @Autowired
    private JhQzFwService jhQzFwService;

    @Autowired
    private RestTemplate restTemplate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void onMessage(Message message, Channel channel) throws Exception {
    	LogWritter.bizDebug("consumer--:"+message.getMessageProperties()+":"+message.toString());
        try{
        	String reqJson=new String(message.getBody(),"UTF-8");
        	Map reqMap=(Map) JSONObject.parse(reqJson);
        	if("01".equals(reqMap.get("LOCAL-MODEL"))){
        		 LogWritter.bizDebug("进入本地单例模式");
        		 RequestEvent deal002_req = new RequestEvent();
        		 deal002_req.setBeanId("jhQzFwService");
                 deal002_req.setHandleCode("jhdl_qz_deal002");
                 deal002_req.getReqCurMap().putAll(reqMap);
                 deal002_req.setCallMethodName("performTask");
                 ResponseEvent deal002_resEvent  = jhQzFwService.jhdl_qz_deal002(deal002_req);
                 if(deal002_resEvent.getResCode()==1) {
                	 LogWritter.bizDebug("请求单例模式资源成功，开始调用回调资源");
                	 callbackResource(reqMap,deal002_resEvent.getResStr());
                 }
        	}else if("02".equals(reqMap.get("LOCAL-MODEL"))) {
        		LogWritter.bizDebug("进入本地多例模式，调用内部服务");
        		String localRestFulApiPath = (String)reqMap.get("localRestFulApiPath");
        		String tran_id= (String)reqMap.get("ACCESS-CLIENT-ID");
        		String resource_id=(String)reqMap.get("RESOURCE_ID");
        		//String clientIP = (String)reqMap.get("REQUSET-CLIENT-IP");
        		ConcurrentHashMap resMap = restTemplate.postForObject(localRestFulApiPath, reqMap,ConcurrentHashMap.class);
                 if(resMap == null){
                     LogWritter.bizDebug("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则处理异常，返回空!");
                 }
                 else if(resMap.get("code") == null || "".equals(resMap.get("code"))){
                     LogWritter.bizDebug("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则返回执行状态码为空!");
                 }
                 else if(resMap.get("message") == null || "".equals(resMap.get("message"))){
                     LogWritter.bizDebug("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则返回执行状态消息为空!");
                 }
                 else if(resMap.get("resdata") == null || "".equals(resMap.get("resdata"))){
                     LogWritter.bizDebug("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则返回执行结果信息报文为空! 失败原因："+(String)resMap.get("message"));
                 }
        	}
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch(Exception e){
        	LogWritter.bizDebug(e.toString());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
        }
    }

	private void  callbackResource(Map<?, ?> reqMap,String resXml) throws Exception{
		LogWritter.bizDebug("进入回调资源模式");
		String tran_id= (String)reqMap.get("ACCESS-CLIENT-ID");
		String clientIP = (String)reqMap.get("REQUSET-CLIENT-IP");
		String transactionID = (String)reqMap.get("TRANSACTION_ID");
		String resResourceId=(String) reqMap.get("resResourceId");
		String callbackRestFulApi=(String) reqMap.get("callbackRestFulApi");
		RestfulHttpClient.HttpClient client = RestfulHttpClient.getClient(callbackRestFulApi);
		LogWritter.bizDebug("\n client:::::::::::::::::"+client.getUrl());
		client.post();
		client.connectTimeout(20000);
		client.readTimeout(60*10000);
		client.addHeader("Content-type", "application/json;charsert=UTF-8");
		client.addHeader("ACCESS-CLIENT-ID",tran_id);
		client.addHeader("TRANSACTION-ID",transactionID);
		client.addHeader("RESOURCE-ID",resResourceId);
		client.addHeader("REQUSET-CLIENT-IP",clientIP);
		client.body(resXml);
		//发起请求，获取响应结果
		RestfulHttpClient.HttpResponse response = null;
		try {
			response = client.request();
		} catch (IOException e) {
			LogWritter.sysError("\n调用restful接口异常::::::::::::::::::::::",e);
		}
		if(response != null) {
			if (response.getCode() == 200) {
				 LogWritter.bizDebug("请求回调资源成功");
			}
		}
	}
}

