package com.cwks.bizcore.sjjh.core.ctrl;

import com.cwks.bizcore.sjjh.core.utils.RestFulJhQzFwClient;
import com.cwks.common.log.LogWritter;
import com.cwks.common.core.systemConfig.BizErrorMsgContext;
import com.cwks.bizcore.comm.utils.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JhFwWebService
 * @功能：服务层代理实现类
 * @author 胡锐
 * @version 1.0
 */
@Service("ExtTestWebService")
@WebService(targetNamespace = "http://www.cssnj.com.cn/soa_service/", portName = "testHttpServicePort",serviceName = "testWebService")
public class ExtTestWebService {

	@Resource
	private WebServiceContext wsContext;

	@WebMethod
	public String jhdl_test(String request_message) throws Exception {
		if(request_message == null){
			return getResJsonMessage("-1","","没有传入报文",null);
		}
		String reqip = getClientInfo();
		if(reqip == null || "".equals(reqip)){
			return getResJsonMessage("-1","","未获取到客户端IP",null);
		}
		String resStr="";
		try {
			resStr = RestFulJhQzFwClient.singleton().postForObject("","CSSNJ.TEST.JSDS.TEST01","COM.CSSNJ.SJJH.CORE.TEST01",reqip,request_message);
		}catch (Exception e){
			LogWritter.bizError("JhFwWebService ERROR",e);
			return getResJsonMessage("-1","",e.getMessage(),null);
		}
		System.out.println(resStr);
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

	private String getResJsonMessage(String code,String error_code,String msg,ConcurrentHashMap jhgl_resMap) {
		ConcurrentHashMap resMap = new ConcurrentHashMap();
		String rtnMsg = msg;
		Object obj = BizErrorMsgContext.singleton().getValueAsString(error_code);
		if(obj != null && !"".equals(obj)){
			rtnMsg = (String)obj;
		}
		if(!"".equals(rtnMsg)){
			msg = rtnMsg;
		}
		resMap.put("code", code);
		resMap.put("message", (msg==null)?"":msg);
		resMap.put("resdata", (jhgl_resMap==null)?"":jhgl_resMap);
		return JsonUtil.toJson(resMap);
	}

}