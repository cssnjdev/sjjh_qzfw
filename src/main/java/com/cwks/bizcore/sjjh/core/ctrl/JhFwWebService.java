package com.cwks.bizcore.sjjh.core.ctrl;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.sjjh.core.config.loger.JhLogEvent;
import com.cwks.bizcore.sjjh.core.config.loger.JhLogWritter;
import com.cwks.bizcore.sjjh.core.service.JhQzFwService;
import com.cwks.bizcore.sjjh.core.utils.CusAccessObjectUtil;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.systemConfig.BizErrorMsgContext;
import com.cwks.common.log.LogWritter;

/**
 * JhFwWebService
 * @功能：服务层代理实现类
 * @author cssnj
 * @version 1.0
 */
@Service("JhFwWebService")
@WebService(targetNamespace = "http://www.cssnj.com.cn/soa_service/", portName = "jhFwHttpServicePort",serviceName = "jhFwWebService")
public class JhFwWebService {

	@Resource
	private WebServiceContext wsContext;

	@Autowired
	private JhLogWritter jhLogWritter;

	@Autowired
	private ObjectFactory<JhLogEvent> jhLogEventFactory;

	@Autowired
	private JhQzFwService jhQzFwService;

	@WebMethod
	public String checkHeartBeat() {
		StringBuffer jsonStr = new StringBuffer("{code:\"1\",message:\"success\"}");
		return jsonStr.toString();
	}

	//@WebResult(name = "jhdlWbfwResponse", targetNamespace = "http://www.cssnj.com.cn/soa_service/", partName = "parameters")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@WebMethod
	public String jhdl_wbfw(String access_client_id,String resource_id,String transaction_id,String request_message) throws Exception {
		JhLogEvent jhLogEvent=jhLogEventFactory.getObject();
		jhLogWritter.bizDebug(this.getClass().getName()+".jhdl_wbfw 方法开始");
		long startTime=System.currentTimeMillis();
		ConcurrentHashMap reqMap = new ConcurrentHashMap();
		ConcurrentHashMap resMap = new ConcurrentHashMap();
		String tran_id = CusAccessObjectUtil.getUUID32();
		String msg = "";
		ConcurrentHashMap jhgl_resMap = null;
		if(resource_id == null || "".equals(resource_id)){
			return getResJsonMessage(tran_id,resource_id,"-1","BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0001","",jhgl_resMap);
		}
		if (access_client_id != null && !"".equals(access_client_id)) {
			tran_id =access_client_id+"_"+tran_id;
		}
		if (transaction_id != null && !"".equals(transaction_id)) {
			tran_id =access_client_id+"_"+transaction_id;
		}
		if(request_message == null){
			return getResJsonMessage(tran_id,resource_id,"-1","BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0002","",jhgl_resMap);
		}
		String reqip = getClientInfo();
		if(reqip == null || "".equals(reqip)){
			return getResJsonMessage(tran_id,resource_id,"-1","BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0003","",jhgl_resMap);
		}
		jhLogEvent.setJh_transactionId(tran_id);
		jhLogEvent.setJh_accessClientIp(reqip);
		jhLogEvent.setJh_accessClientId(access_client_id);
		jhLogEvent.setJh_resourceId(resource_id);
		jhLogWritter.setSessionInfo(jhLogEvent);
		jhLogWritter.bizInfo("1.1该请求通过一般校验，进入数据交换前置服务webservice外部服务接口！");
		String resStr="";
		try {
			reqMap.put("TRANSACTION_ID", tran_id);
			reqMap.put("REQUSET-CLIENT-IP",reqip);
			reqMap.put("ACCESS-CLIENT-ID",access_client_id);
			reqMap.put("RESOURCE_ID",resource_id);
			reqMap.put("REQUSET_MESSAGE",request_message);
			ResponseEvent resEvent = null;
			RequestEvent req = new RequestEvent();
			req.setBeanId("jhQzFwService");
			req.setHandleCode("jhdl_qz_deal001");
			req.setReqCurMap(reqMap);
			req.setCallMethodName("performTaskLongTx");

			long endTime=System.currentTimeMillis();
			jhLogEvent.setJh_elapsed(endTime-startTime);
			jhLogWritter.setSessionInfo(jhLogEvent);
			jhLogWritter.bizInfo("1.2调用数据交换前置内部服务开始！");
			//resEvent = ServiceDelegate.delegate(req);
			resEvent = jhQzFwService.jhdl_qz_deal001(req);
			endTime=System.currentTimeMillis();
			jhLogEvent.setJh_elapsed(endTime-startTime);
			jhLogWritter.setSessionInfo(jhLogEvent);
			jhLogWritter.bizInfo("1.3调用数据交换前置内部服务结束！");
			if(resEvent.getResCurMap() == null){
				return getResJsonMessage(tran_id,resource_id,"-1","BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0004","",jhgl_resMap);
			}
			resMap.put("RESOURCE_ID", resource_id);
			resMap.put("code", String.valueOf(resEvent.getResCode()));
			resMap.put("message", resEvent.getResMsg());
			resMap.put("resdata", resEvent.getResStr());
			resStr = JsonUtil.toJson(resMap);
		}catch (Exception e){
			jhLogWritter.bizError("error: 该请求进入webservice jhdl_wbfw数据交换前置内部服务过程发生异常！",e);
			return getResJsonMessage(tran_id,resource_id,"-1","BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0005",e.getMessage(),jhgl_resMap);
		}
		jhLogWritter.bizDebug(this.getClass().getName()+".jhdl_wbfw 方法结束");
		return resStr;
	}

	private String getClientInfo() {
		String clientIP = null;
		try {
			MessageContext mc = wsContext.getMessageContext();
			HttpServletRequest request = (HttpServletRequest) (mc.get(MessageContext.SERVLET_REQUEST));
			clientIP = request.getRemoteAddr();
		} catch (Exception e) {
			LogWritter.sysError("get webservice client ip error:", e);
		}
		return clientIP;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getResJsonMessage(String transaction_id,String resource_id,String code,String error_code,String msg,ConcurrentHashMap jhgl_resMap) {
		ConcurrentHashMap resMap = new ConcurrentHashMap();
		String rtnMsg = "";
		Object obj = BizErrorMsgContext.singleton().getValueAsString(error_code);
		if(obj != null && !"".equals(obj)){
			rtnMsg = (String)obj;
		}
		if(!"".equals(rtnMsg)){
			msg = rtnMsg;
		}
		resMap.put("TRANSACTION_ID", (transaction_id==null)?"":transaction_id );
		resMap.put("RESOURCE_ID", (resource_id==null)?"":resource_id );
		resMap.put("code", code);
		resMap.put("message", (msg==null)?"":msg);
		resMap.put("resdata", (jhgl_resMap==null)?"":jhgl_resMap);
		return JsonUtil.toJson(resMap);
	}

}