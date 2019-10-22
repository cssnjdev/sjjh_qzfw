package com.cwks.bizcore.sjjh.core.ctrl;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cwks.bizcore.sjjh.core.config.JhQzFwContext;
import com.cwks.bizcore.sjjh.core.config.loger.JhLogEvent;
import com.cwks.bizcore.sjjh.core.config.loger.JhLogWritter;
import com.cwks.bizcore.sjjh.core.service.JhQzFwService;
import com.cwks.bizcore.sjjh.core.utils.CusAccessObjectUtil;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.systemConfig.BizErrorMsgContext;


/**
 * JhQzController
 * <p>Title: JhQzController.java</p>
 * <p>Description:数据交换前置服务的标准restful api</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cssnj</p>
 *
 * @author cssnj
 * @version 1.0
 */
@RestController
@RequestMapping("/jhfw-api")
public class JhFwController {

    @Autowired
    private JhLogWritter jhLogWritter;

    @Autowired
    private ObjectFactory<JhLogEvent> jhLogEventFactory;

    @Autowired
    private JhQzFwService jhQzFwService;

    /**
     * <p>url: /jhfw-api/jhdl-wbfw</p>
     * <p>Description:前置服务对外暴露的restful接口，用于处理外部请求前置服务的交换报文</p>
     *
     * @version 1.0
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/jhdl-wbfw", method = {RequestMethod.POST, RequestMethod.GET})
    public ConcurrentHashMap jhdl_wbfw(HttpServletRequest request, HttpServletResponse response, @RequestHeader("ACCESS-CLIENT-ID") String acc_client_id, @RequestHeader("TRANSACTION-ID") String transaction_id, @RequestHeader("RESOURCE-ID") String resource_id, @RequestHeader("REQUSET-CLIENT-IP") String client_ips, @RequestBody String reqStr) throws Exception {
        JhLogEvent jhLogEvent=jhLogEventFactory.getObject();
        jhLogWritter.sysDebug(this.getClass().getName()+".jhdl_wbfw 方法开始");
        long startTime=System.currentTimeMillis();
        ConcurrentHashMap resMap = new ConcurrentHashMap();
        ConcurrentHashMap reqMap = new ConcurrentHashMap();
        String tran_id = CusAccessObjectUtil.getUUID32();
        ConcurrentHashMap jhgl_resMap = null;
        if (resource_id == null || "".equals(resource_id)) {
            return getResMap(tran_id,"", "-1", "BIZ.SJJHPT.QZFW.WBFW.ERR_00_0001", "", jhgl_resMap);
        } else {
            reqMap.put("RESOURCE_ID", resource_id);
        }
        if (reqStr == null) {
            return getResMap(tran_id,resource_id, "-1", "BIZ.SJJHPT.QZFW.WBFW.ERR_00_0002", "", jhgl_resMap);
        } else {
            reqMap.put("REQUSET_MESSAGE", reqStr);
        }
        if (acc_client_id != null && !"".equals(acc_client_id)) {
            tran_id =acc_client_id+"_"+tran_id;
            reqMap.put("ACCESS-CLIENT-ID", acc_client_id);
        } else {
            reqMap.put("ACCESS-CLIENT-ID", "");
        }
        if (transaction_id != null && !"".equals(transaction_id)) {
            tran_id =acc_client_id+"_"+transaction_id;
        }

        String reqip = CusAccessObjectUtil.getIpAddress(request);
        if (reqip == null || "".equals(reqip)) {
            return getResMap(tran_id,resource_id, "-1", "BIZ.SJJHPT.QZFW.WBFW.ERR_00_0003", "", jhgl_resMap);
        }
        if (client_ips != null && !"".equals(client_ips)) {
            reqMap.put("REQUSET-CLIENT-IP", reqip + "," + client_ips);
        } else {
            reqMap.put("REQUSET-CLIENT-IP", reqip);
        }

        jhLogEvent.setJh_transactionId(tran_id);
        jhLogEvent.setJh_accessClientIp(reqip);
        jhLogEvent.setJh_accessClientId(acc_client_id);
        jhLogEvent.setJh_resourceId(resource_id);
        jhLogWritter.setSessionInfo(jhLogEvent);
        jhLogWritter.bizInfo("2.1该请求通过一般校验，进入数据交换前置服务restful外部服务接口！");
        try {
            reqMap.put("TRANSACTION_ID", tran_id);
            ResponseEvent resEvent = null;
            RequestEvent req = new RequestEvent();
            req.setBeanId("jhQzFwService");
            req.setHandleCode("jhdl_qz_deal001");
            req.setReqCurMap(reqMap);
            req.setCallMethodName("performTaskLongTx");
            long endTime=System.currentTimeMillis();
            jhLogEvent.setJh_elapsed(endTime-startTime);
            jhLogWritter.setSessionInfo(jhLogEvent);
            jhLogWritter.bizInfo("2.2调用数据交换前置内部服务开始！");
            resEvent = jhQzFwService.jhdl_qz_deal001(req);
            jhLogEvent.setJh_elapsed(endTime-startTime);
            jhLogWritter.setSessionInfo(jhLogEvent);
            jhLogWritter.bizInfo("2.3调用数据交换前置内部服务结束！");
            if (resEvent.getResCurMap() == null) {
                return getResMap(tran_id,resource_id, "-1", "BIZ.SJJHPT.QZFW.WBFW.ERR_00_0004", "", jhgl_resMap);
            }
            resMap.put("TRANSACTION_ID", tran_id);
            resMap.put("RESOURCE_ID", resource_id);
            resMap.put("code", String.valueOf(resEvent.getResCode()));
            resMap.put("message", resEvent.getResMsg());
            resMap.put("resdata", resEvent.getResStr());
        } catch (Exception e) {
            jhLogWritter.bizError("error: 该请求进入restful jhdl_wbfw数据交换前置内部服务过程发生异常！",e);
            return getResMap(tran_id,resource_id, "-1", "BIZ.SJJHPT.QZFW.WBFW.ERR_00_0005", e.getMessage(), jhgl_resMap);
        }
        jhLogWritter.sysDebug(this.getClass().getName()+".jhdl_wbfw 方法结束");
        return resMap;
    }


    /**
     * <p>url: /jhfw-api/jhdl-nbfw</p>
     * <p>Description:前置服务的restful接口，用于处理交换管理中心过来的请求</p>
     *
     * @version 1.0
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/jhdl-nbfw", method = {RequestMethod.POST, RequestMethod.GET})
    public ConcurrentHashMap jhdl_service(HttpServletRequest request, HttpServletResponse response, @RequestHeader("user-agent") String userAgent, @RequestBody ConcurrentHashMap reqMap) throws Exception {
        JhLogEvent jhLogEvent=jhLogEventFactory.getObject();
        jhLogWritter.sysDebug(this.getClass().getName()+".jhdl_service 方法开始");
        long startTime=System.currentTimeMillis();
        ConcurrentHashMap resMap = new ConcurrentHashMap();
        ConcurrentHashMap jhgl_resMap = null;
        if (reqMap == null) {
            return getResMap("","", "-1", "BIZ.SJJHPT.QZFW.NBFW.ERR_00_0001", "", jhgl_resMap);
        }
        String tran_id = (reqMap.get("TRANSACTION_ID")==null)?"":(String)reqMap.get("TRANSACTION_ID") ;
        if (reqMap.get("RESOURCE_ID") == null || "".equals(reqMap.get("RESOURCE_ID"))) {
            return getResMap(tran_id,"", "-1", "BIZ.SJJHPT.QZFW.NBFW.ERR_00_0003", "", jhgl_resMap);
        }
        String resource_id = (String) reqMap.get("RESOURCE_ID");
        String reqip = CusAccessObjectUtil.getIpAddress(request);
        if (reqip == null || "".equals(reqip)) {
            return getResMap(tran_id,resource_id, "-1", "BIZ.SJJHPT.QZFW.NBFW.ERR_00_0002", "", jhgl_resMap);
        }
        if (reqMap.get("REQUSET_MESSAGE") == null || "".equals(reqMap.get("REQUSET_MESSAGE"))) {
            return getResMap(tran_id,resource_id, "-1", "BIZ.SJJHPT.QZFW.NBFW.ERR_00_0004", "", jhgl_resMap);
        }
        reqMap.put("REQUSET-CLIENT-IP", reqip);
        jhLogEvent.setJh_transactionId(tran_id);
        jhLogEvent.setJh_accessClientIp(reqip);
        jhLogEvent.setJh_accessClientId((String)reqMap.get("ACCESS-CLIENT-ID"));
        jhLogEvent.setJh_resourceId(resource_id);
        jhLogWritter.setSessionInfo(jhLogEvent);
        jhLogWritter.bizInfo("3.1该请求通过一般校验，进入数据交换前置服务restful 内部服务接口！");
        try {
            ResponseEvent resEvent = null;
            RequestEvent req = new RequestEvent();
            req.setReqCurMap(reqMap);
            long endTime=System.currentTimeMillis();
            jhLogEvent.setJh_elapsed(endTime-startTime);
            jhLogWritter.setSessionInfo(jhLogEvent);
            jhLogWritter.bizInfo("3.2调用数据交换前置内部核心交换业务逻辑服务开始！");
            resEvent = jhQzFwService.jhdl_qz_deal002(req);
            jhLogEvent.setJh_elapsed(endTime-startTime);
            jhLogWritter.setSessionInfo(jhLogEvent);
            jhLogWritter.bizInfo("3.3调用数据交换前置内部核心交换业务逻辑服务结束！");
            if (resEvent.getResCurMap() == null) {
                return getResMap(tran_id,resource_id, "-1", "BIZ.SJJHPT.QZFW.NBFW.ERR_00_0005", "", jhgl_resMap);
            }
            resMap.put("TRANSACTION_ID", tran_id);
            resMap.put("RESOURCE_ID", resource_id);
            resMap.put("code", String.valueOf(resEvent.getResCode()));
            resMap.put("message", resEvent.getResMsg());
            resMap.put("resdata", resEvent.getResStr());
        } catch (Exception e) {
            jhLogWritter.bizError("error: 该请求进入restful jhdl_service数据交换前置内部核心交换业务逻辑服务过程发生异常！",e);
            return getResMap(tran_id,resource_id, "-1", "BIZ.SJJHPT.QZFW.NBFW.ERR_00_0006", e.getMessage(), jhgl_resMap);
        }
        jhLogWritter.sysDebug(this.getClass().getName()+".jhdl_service 方法结束");
        return resMap;
    }

    /**
     * <p>url: /jhfw-api/jhdl-nbfw-qz-config</p>
     * <p>Description:前置服务的restful接口，用于处理交换管理中心发送过来更新接入点资源和数据源配置信息的接口</p>
     * @version 1.0
     */
    @RequestMapping(value = "/jhdl-nbfw-qz-config", method = {RequestMethod.POST})
    public @ResponseBody
    String jhdl_qz_config_service(HttpServletRequest request, @RequestBody ConcurrentHashMap reqMap) throws Exception {
        jhLogWritter.sysDebug(this.getClass().getName()+".jhdl_qz_config_service 方法开始");
        ConcurrentHashMap resMap = new ConcurrentHashMap();
        if (reqMap == null) {
            return getResMapJsonStrByUpdateConfig( "-1", "ERROR:交换管理中心推送的配置信息为空。");
        }
        if ((reqMap.get("JHQZFW_RESOURCES_MAP") == null || ((ConcurrentHashMap)reqMap.get("JHQZFW_RESOURCES_MAP")).size()<=0)
                &&(reqMap.get("JHQZFW_DATASOURCE_MAP") == null || ((ConcurrentHashMap)reqMap.get("JHQZFW_DATASOURCE_MAP")).size()<=0)) {
            return getResMapJsonStrByUpdateConfig( "-1", "ERROR:交换管理中心推送的配置信息[接入点前置资源信息和数据源信息]为空。");
        }
        try {
            JhQzFwContext qzfw = JhQzFwContext.singleton();
            if (reqMap.get("JHQZFW_RESOURCES_MAP") != null && ((ConcurrentHashMap)reqMap.get("JHQZFW_RESOURCES_MAP")).size()>0) {
                qzfw.setResourceRulesMap((ConcurrentHashMap)reqMap.get("JHQZFW_RESOURCES_MAP"));
            }
            if (reqMap.get("JHQZFW_DATASOURCE_MAP") != null || ((ConcurrentHashMap)reqMap.get("JHQZFW_DATASOURCE_MAP")).size()>0) {
                qzfw.setDataSourceMap((ConcurrentHashMap)reqMap.get("JHQZFW_DATASOURCE_MAP"));
                qzfw.setDBPoolConnectionMap(new ConcurrentHashMap());
            }
            resMap.put("code","1");
            resMap.put("message", "UPDATE IS OK!");
        } catch (Exception e) {
            jhLogWritter.sysError("error: 交换管理中心推送的配置信息更新本地资源和数据源缓存配置的过程发生异常！",e);
            return getResMapJsonStrByUpdateConfig("-1", "error: 交换管理中心推送的配置信息更新本地资源和数据源缓存配置的过程发生异常:"+e.getMessage());
        }
        jhLogWritter.sysDebug(this.getClass().getName()+".jhdl_qz_config_service 方法结束");
        return JSONObject.toJSONString(resMap);
    }

    /**
     * <p>Description:包装异常情况的返回请求端的对象</p>
     *
     * @version 1.0
     */
    private ConcurrentHashMap getResMap(String transaction_id,String resource_id, String code, String error_code, String msg, ConcurrentHashMap jhgl_resMap) {
        ConcurrentHashMap resMap = new ConcurrentHashMap();
        String rtnMsg = "";
        Object obj = BizErrorMsgContext.singleton().getValueAsString(error_code);
        if (obj != null && !"".equals(obj)) {
            rtnMsg = (String) obj;
        }
        if (!"".equals(rtnMsg)) {
            msg = rtnMsg;
        }
        resMap.put("TRANSACTION_ID", (transaction_id==null)?"":transaction_id );
        resMap.put("RESOURCE_ID", (resource_id==null)?"":resource_id );
        resMap.put("code", code);
        resMap.put("message", (msg==null)?"":msg);
        resMap.put("resdata", (jhgl_resMap==null)?"":jhgl_resMap);
        return resMap;
    }

    /**
     * <p>Description:包装异常情况的返回请求端的对象</p>
     *
     * @version 1.0
     */
    private String getResMapJsonStr(String transaction_id,String resource_id, String code, String error_code, String msg, ConcurrentHashMap jhgl_resMap) {
        ConcurrentHashMap resMap = new ConcurrentHashMap();
        String rtnMsg = "";
        Object obj = BizErrorMsgContext.singleton().getValueAsString(error_code);
        if (obj != null && !"".equals(obj)) {
            rtnMsg = (String) obj;
        }
        if (!"".equals(rtnMsg)) {
            msg = rtnMsg;
        }
        resMap.put("TRANSACTION_ID", (transaction_id==null)?"":transaction_id );
        resMap.put("RESOURCE_ID", (resource_id==null)?"":resource_id );
        resMap.put("code", code);
        resMap.put("message", (msg==null)?"":msg);
        resMap.put("resdata", (jhgl_resMap==null)?"":jhgl_resMap);
        return JSONObject.toJSONString(resMap);
    }

    /**
     * <p>Description:包装异常情况的返回请求端的对象</p>
     *
     * @version 1.0
     */
    private String getResMapJsonStrByUpdateConfig(String code,String msg) {
        ConcurrentHashMap resMap = new ConcurrentHashMap();
        resMap.put("code", code);
        resMap.put("message", (msg==null)?"":msg);
        return JSONObject.toJSONString(resMap);
    }
}
