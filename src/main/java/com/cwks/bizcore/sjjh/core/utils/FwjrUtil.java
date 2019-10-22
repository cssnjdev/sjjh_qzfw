package com.cwks.bizcore.sjjh.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.cxf.endpoint.Client;

import com.cwks.common.log.LogWritter;
import com.cwks.common.core.systemConfig.BizContext;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class FwjrUtil {

	public static String sendGt3(String xml, String uuid) {
		Map<String, String> resMap = new HashMap<String, String>();
		String results = null;
		Object[] resXml = null;
		String gt3OSBUrl = BizContext.singleton().getValueAsString("gt3OSBUrl");
		try {
			LogWritter.bizDebug("---------开始发送金三osb系统---------\n,会话uuid:" + uuid + "，请求金三osb系统地址：" + gt3OSBUrl);
			resXml=cxfServiceSend(xml,gt3OSBUrl,"sendBaseXMLEsbWebService");
			if(resXml == null || resXml.length <=0){
				LogWritter.bizDebug("---------结束发送金三osb系统---------\n,会话uuid:" + uuid + "，返回数据：为空");
			}else{
				LogWritter.bizDebug("---------结束发送金三osb系统---------\n,会话uuid:" + uuid + "，返回数据：" + resXml[0].toString());
			}
			if (resXml != null) {
	            final Object object = resXml[0];
	            if (object instanceof org.w3c.dom.Document) {
	                final org.w3c.dom.Document res_dom = (org.w3c.dom.Document) resXml[0];
	                final org.w3c.dom.Element e = res_dom.getDocumentElement();
	                results = e.getTextContent();
	            } else {
	                results = (String) object;
	            }
		  	}else{
		  		resMap.put("Code", "012");
				resMap.put("Reason", "调用金三osb报错：返回为空！");
				return FwjrUtil.returnErrMsg(resMap, uuid);
		  	}
		} catch (Exception e) {
			resMap.put("Code", "012");
			resMap.put("Reason", "调用调用金三osb报错：Exception：" + e + ",请求url:" + gt3OSBUrl);
			return FwjrUtil.returnErrMsg(resMap, uuid);
		}
		return results;
	}

	public static String sendSbf(String xml, String uuid) {
		Map<String, String> resMap = new HashMap<String, String>();
		String results = null;
		Object[] resXml = null;
		String sbfOSBUrl = BizContext.singleton().getValueAsString("sbfOSBUrl");
		try {
			LogWritter.bizDebug("---------开始发送社保费征收子系统---------\n,会话uuid:" + uuid + "，请求社保费征收子系统地址：" + sbfOSBUrl);
			resXml=cxfServiceSend(xml,sbfOSBUrl,"sendBaseXMLEsbWebService");
			if(resXml == null || resXml.length <=0){
				LogWritter.bizDebug("---------结束发送社保费征收子系统---------\n,会话uuid:" + uuid + "，返回数据：为空");
			}else{
				LogWritter.bizDebug("---------结束发送社保费征收子系统---------\n,会话uuid:" + uuid + "，返回数据：" + resXml[0].toString());
			}
			if (resXml != null) {
	            final Object object = resXml[0];
	            if (object instanceof org.w3c.dom.Document) {
	                final org.w3c.dom.Document res_dom = (org.w3c.dom.Document) resXml[0];
	                final org.w3c.dom.Element e = res_dom.getDocumentElement();
	                results = e.getTextContent();
	            } else {
	                results = (String) object;
	            }
		  	}else{
		  		resMap.put("Code", "012");
				resMap.put("Reason", "调用社保费征收子系统报错：返回为空！");
				return FwjrUtil.returnErrMsg(resMap, uuid);
		  	}
		} catch (Exception e) {
			resMap.put("Code", "012");
			resMap.put("Reason", "调用社保费征收子系统报错：Exception：" + e + ",请求url:" + sbfOSBUrl);
			return FwjrUtil.returnErrMsg(resMap, uuid);
		}
		return results;
	}

	public static String sendShij(String xml, String uuid) {
		Map<String, String> resMap = new HashMap<String, String>();
		String results = null;
		Object[] resXml = null;
		String sjOSBUrl = BizContext.singleton().getValueAsString("sjOSBUrl");
		try {
			LogWritter.bizDebug("---------开始发送市局osb---------\n,会话uuid:" + uuid + "，请求市局osb地址：" + sjOSBUrl);
			resXml=cxfServiceSend(xml,sjOSBUrl,"sendBaseXMLEsbWebService");
			if(resXml == null || resXml.length <=0){
				LogWritter.bizDebug("---------结束发送市局osb---------\n,会话uuid:" + uuid + "，返回数据：为空");
			}else{
				LogWritter.bizDebug("---------结束发送市局osb---------\n,会话uuid:" + uuid + "，返回数据：" + resXml[0].toString());
			}
			if (resXml != null) {
	            final Object object = resXml[0];
	            if (object instanceof org.w3c.dom.Document) {
	                final org.w3c.dom.Document res_dom = (org.w3c.dom.Document) resXml[0];
	                final org.w3c.dom.Element e = res_dom.getDocumentElement();
	                results = e.getTextContent();
	            } else {
	                results = (String) object;
	            }
		  	}else{
		  		resMap.put("Code", "012");
				resMap.put("Reason", "调用市局osb报错：返回为空！");
				return FwjrUtil.returnErrMsg(resMap, uuid);
		  	}
		} catch (Exception e) {
			resMap.put("Code", "012");
			resMap.put("Reason", "调用市局osb报错：Exception：" + e + ",请求url:" + sjOSBUrl);
			return FwjrUtil.returnErrMsg(resMap, uuid);
		}
		return results;
	}
	public static Object[]  cxfServiceSend(String xml,String url,String method) throws Exception{
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		Client client = factory.createClient(url);
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(10000);  //连接超时
		httpClientPolicy.setAllowChunking(false);    //取消块编码
		httpClientPolicy.setReceiveTimeout(120000); //响应超时
		conduit.setClient(httpClientPolicy);
		Object[] objs = client.invoke(method,xml);
		return objs;
	}
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	public static Map<String, String> getReqMap(String xmlStr, String uuid) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (xmlStr == null || "".equals(xmlStr)) {
			return null;
		}
		String tran_id = "";
		String channel_id = "";
		String tran_seq = "";
		String jsltjrcs0 = "";
		String jsltjrcs1 = "";
		String tran_date = "";
		String tran_time = "";
		String sendXml = "";

		if (xmlStr.indexOf("<tran_id>") != -1) {
			tran_id = xmlStr.substring(xmlStr.indexOf("<tran_id>") + 9,
					xmlStr.indexOf("</tran_id>"));
		}
		if (xmlStr.indexOf("channel_id") != -1) {
			channel_id = xmlStr.substring(xmlStr.indexOf("<channel_id>") + 12,
					xmlStr.indexOf("</channel_id>"));
		}
		if (xmlStr.indexOf("<tran_seq>") != -1) {
			tran_seq = xmlStr.substring(xmlStr.indexOf("<tran_seq>") + 10,
					xmlStr.indexOf("</tran_seq>"));
		}
		if (xmlStr.indexOf("<jsltjrcs0>") != -1) {
			jsltjrcs0 = xmlStr.substring(xmlStr.indexOf("<jsltjrcs0>") + 11,
					xmlStr.indexOf("</jsltjrcs0>"));
			sendXml = xmlStr.substring(0, xmlStr.indexOf("<jsltjrcs0>"));
		}
		if (xmlStr.indexOf("<jsltjrcs1>") != -1) {
			jsltjrcs1 = xmlStr.substring(xmlStr.indexOf("<jsltjrcs1>") + 11,
					xmlStr.indexOf("</jsltjrcs1>"));
		}

		if (xmlStr.indexOf("<tran_date>") != -1) {
			tran_date = xmlStr.substring(xmlStr.indexOf("<tran_date>") + 11,
					xmlStr.indexOf("</tran_date>"));
		}
		if (xmlStr.indexOf("<tran_time>") != -1) {
			tran_time = xmlStr.substring(xmlStr.indexOf("<tran_time>") + 11,
					xmlStr.indexOf("</tran_time>"));
		}

		if (tran_id == null || "".equals(tran_id) || channel_id == null
				|| channel_id.equals("") || tran_seq == null
				|| tran_seq.equals("") || tran_date == null
				|| tran_date.equals("") || tran_time == null
				|| tran_time.equals("") || jsltjrcs0 == null
				|| jsltjrcs0.equals("") || jsltjrcs1 == null
				|| jsltjrcs1.equals("")) {
			resMap.put("errXml", FwjrUtil.returnErrNotCheck(xmlStr, uuid)); // //如果不为空，则表示校验不通过
																			// 请求报文合规校验
			return resMap;
		}

		resMap.put("tran_id", tran_id);
		resMap.put("channel_id", channel_id);
		resMap.put("tran_seq", tran_seq);
		resMap.put("tran_date", tran_date);
		resMap.put("tran_time", tran_time);
		resMap.put("jsltjrcs0", jsltjrcs0);
		resMap.put("jsltjrcs1", jsltjrcs1);
		resMap.put("sendXml", sendXml);
		return resMap;
	}

	/**
	 * 返回错误报文和金三一致！ rtn_code 9 和 code 001-009重新定义
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static String returnErrMsg(Map<String, String> map, String uuid) {
		String re = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "   <soap:Header xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"/>\n"
				+ "   <soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "      <spec:service xmlns:spec=\"http://www.chinatax.gov.cn/spec/\"><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<service xmlns=\"http://www.chinatax.gov.cn/spec/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "  <head>\n" + "    <tran_id>"
				+ map.get("tran_id")
				+ "</tran_id>\n"
				+ "    <channel_id>"
				+ map.get("channel_id")
				+ "</channel_id>\n"
				+ "    <tran_seq>"
				+ map.get("tran_seq")
				+ "</tran_seq>\n"
				+ "    <tran_date>"
				+ map.get("tran_date")
				+ "</tran_date>\n"
				+ "    <tran_time>"
				+ map.get("tran_time")
				+ "</tran_time>\n"
				+ "    <rtn_code>9</rtn_code>\n"
				+ "    <rtn_msg>\n"
				+ "      <Code>"
				+ map.get("Code")
				+ "</Code>\n"
				+ "      <Message>接入校验出错</Message>\n"
				+ "      <Reason>"
				+ map.get("Reason")
				+ "</Reason>\n"
				+ "    </rtn_msg>\n"
				+ "  </head>\n"
				+ "  <body></body>\n"
				+ "</service>]]></spec:service>\n"
				+ "   </soap:Body>\n"
				+ "</soapenv:Envelope>";
		// dao.updateLsb(uuid, re);
		LogWritter.sysDebug("--------------本次请求结束-------------\n,会话uuid:" + uuid
				+ "，返回错误信息：" + re);
		return re;
	}

	/**
	 * 请求报文合规校验
	 * 
	 * @param xml
	 * @return
	 */
	public static String returnErrNotCheck(String xml, String uuid) {
		xml = xml.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		String re = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "   <soapenv:Header xmlns:spec=\"http://www.chinatax.gov.cn/spec/\"/>\n"
				+ "   <soapenv:Body xmlns:spec=\"http://www.chinatax.gov.cn/spec/\">\n"
				+ "      <spec:service><![CDATA[<spec:service xmlns:spec=\"http://www.chinatax.gov.cn/spec/\"><spec:head><spec:tran_id/><spec:channel_id/><spec:tran_seq/><spec:tran_date/><spec:tran_time/><spec:rtn_code/><spec:rtn_msg><spec:Code/><spec:Message/><spec:Reason>&lt;ctx:fault xmlns:ctx=\"http://www.bea.com/wli/sb/context\"&gt;&lt;ctx:errorCode&gt;BEA-382505&lt;/ctx:errorCode&gt;&lt;ctx:reason&gt;OSB Validate action failed validation&lt;/ctx:reason&gt;&lt;ctx:details&gt;&lt;ns0:ValidationFailureDetail xmlns:ns0=\"http://www.bea.com/wli/sb/stages/transform/config\"&gt;&lt;ns0:message&gt;string length (0) does not match length facet (8) for type of tran_date element in headType in namespace http://www.chinatax.gov.cn/spec/&lt;/ns0:message&gt;&lt;ns0:xmlLocation&gt;&lt;spec:tran_date xmlns:spec=\"http://www.chinatax.gov.cn/spec/\"/&gt;&lt;/ns0:xmlLocation&gt;&lt;/ns0:ValidationFailureDetail&gt;&lt;/ctx:details&gt;&lt;ctx:location&gt;&lt;ctx:node&gt;PipelinePairNode1&lt;/ctx:node&gt;&lt;ctx:pipeline&gt;PipelinePairNode1_request&lt;/ctx:pipeline&gt;&lt;ctx:stage&gt;请求报文合规校验&lt;/ctx:stage&gt;&lt;ctx:path&gt;request-pipeline&lt;/ctx:path&gt;&lt;/ctx:location&gt;&lt;/ctx:fault&gt;</spec:Reason></spec:rtn_msg></spec:head><spec:body>\n"
				+ xml
				+ "\n"
				+ "   </spec:body></spec:service>]]></spec:service>\n"
				+ "   </soapenv:Body>\n" + "</soapenv:Envelope>";
		// dao.updateLsb(uuid, re);
		LogWritter.sysDebug("---------本次请求结束---------\n,会话uuid:" + uuid
				+ "，返回报文合规校验错误：" + re);
		return re;
	}
}
