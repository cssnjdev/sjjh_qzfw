package com.cwks.biz.sjjh.mq.service;

import com.alibaba.fastjson.JSONObject;
import com.cwks.common.core.systemConfig.SpringContextUtil;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service("jhDlQzMqService")
public class JhDlQzMqService {

	//@Resource
	@Autowired
	private AmqpTemplate amqpTemplate;

	@SuppressWarnings("rawtypes")
	public ResponseEvent sendDataToQueue(RequestEvent requestEvent) throws Exception {
		if(amqpTemplate == null){
			amqpTemplate = (AmqpTemplate)SpringContextUtil.getBean("amqpTemplate");
		}
		ConcurrentHashMap reqMap = requestEvent.getReqCurMap();
		ResponseEvent resEvent = new ResponseEvent();
		try{
			String message=net.sf.json.JSONObject.fromObject(reqMap).toString();
			amqpTemplate.convertAndSend((String) reqMap.get("mqPattern"), message.getBytes("UTF-8"));
			resEvent.setResCode(1);
			resEvent.setResMsg("异步消息发送成功。");
			resEvent.setResWsXml(getResMapJsonStr("1","异步消息发送成功。"));
		}catch (Exception e){
			resEvent.setResCode(-1);
			resEvent.setResMsg("异步消息发送异常："+e.getMessage());
			resEvent.setResWsXml(getResMapJsonStr("1","异步消息发送异常："+e.getMessage()));
		}
		return resEvent;
	}

	/**
	 * <p>Description:包装异常情况的返回请求端的对象</p>
	 *
	 * @version 1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getResMapJsonStr(String code, String msg) {
		ConcurrentHashMap resMap = new ConcurrentHashMap();
		resMap.put("code", code);
		resMap.put("message", (msg==null)?"":msg);
		return JSONObject.toJSONString(resMap);
	}


}
