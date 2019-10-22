package com.cwks.bizcore.comm.controller;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cwks.bizcore.comm.utils.CusAccessObjectUtil;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.systemConfig.BizErrorMsgContext;
import com.cwks.common.log.LogWritter;
import com.cwks.common.service.ServiceDelegate;


/**
 * TykfptJhFwController
 * <p>Title: TykfptJhFwController.java</p>
 * <p>Description:通用开发平台中台交互的标准restful api</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cssnj</p>
 *
 * @author cssnj
 * @version 1.0
 */
@RestController
@RequestMapping("/tykfpt-jhfw-api")
public class TykfptJhFwController {


    /**
     * <p>url: /tykfpt-jhfw-api/jhdl-wbfw</p>
     * <p>Description:通用开发平台中台交互对外暴露的restful接口，用于处理外部请求前置服务的交换报文</p>
     *
     * @version 1.0
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/jhdl-wbfw", method = {RequestMethod.POST, RequestMethod.GET})
    public HashMap jhdl_wbfw(HttpServletRequest request, HttpServletResponse response, @RequestBody String reqStr) throws Exception {
        LogWritter.sysDebug(this.getClass().getName() + ".jhdl_wbfw 方法开始");
        long startTime = System.currentTimeMillis();
        HashMap resMap = new HashMap();
        HashMap reqMap = new HashMap();
        ConcurrentHashMap jhgl_resMap = null;
        if (reqStr == null) {
            return getResMap("-1", "BIZ.SJJHPT.QZFW.WBFW.ERR_00_0002", "");
        } else {
            reqMap = (HashMap) JsonUtil.toMap(reqStr);
            reqMap.put("REQUSET_MESSAGE", reqStr);
        }

        String reqip = CusAccessObjectUtil.getIpAddress(request);
        if (reqip == null || "".equals(reqip)) {
            return getResMap("-1", "BIZ.SJJHPT.QZFW.WBFW.ERR_00_0003", "");
        }
        reqMap.put("REQUSET-CLIENT-IP", reqip);
        try {
            ResponseEvent resEvent = null;
            RequestEvent req = new RequestEvent();
            if (reqMap.get("tld") != null && !"".equals(reqMap.get("tld"))) {
                String tld = (String) reqMap.get("tld");
                String beanid = tld.split("_")[0];
                String handlecode = tld.split("_")[1];
                req.setBeanId(beanid);
                req.setHandleCode(handlecode);
                req.setCallMethodName("performTask");
                req.setReqMap(reqMap);
                LogWritter.bizInfo("2.2调用数据交换前置内部服务开始！");
                resEvent = ServiceDelegate.delegate(req);
                LogWritter.bizInfo("2.3调用数据交换前置内部服务结束！");
            }

            if (resEvent.getResCurMap() == null) {
                return getResMap("-1", "BIZ.SJJHPT.QZFW.WBFW.ERR_00_0004", "");
            }
            resMap.put("code", String.valueOf(resEvent.getResCode()));
            resMap.put("message", resEvent.getResMsg());
            resMap.put("resdata", JsonUtil.toJson(resEvent.getResMap()));
        } catch (Exception e) {
            LogWritter.bizError("error: 该请求进入restful jhdl_wbfw数据交换前置内部服务过程发生异常！", e);
            return getResMap("-1", "BIZ.SJJHPT.QZFW.WBFW.ERR_00_0005", e.getMessage());
        }
        LogWritter.sysDebug(this.getClass().getName() + ".jhdl_wbfw 方法结束");
        return resMap;
    }

    /**
     * <p>Description:包装异常情况的返回请求端的对象</p>
     *
     * @version 1.0
     */
    private HashMap getResMap(String code, String error_code, String msg) {
        HashMap resMap = new HashMap();
        String rtnMsg = "";
        Object obj = BizErrorMsgContext.singleton().getValueAsString(error_code);
        if (obj != null && !"".equals(obj)) {
            rtnMsg = (String) obj;
        }
        if (!"".equals(rtnMsg)) {
            msg = rtnMsg;
        }
        resMap.put("code", code);
        resMap.put("message", (msg == null) ? "" : msg);
        resMap.put("resdata", "");
        return resMap;
    }

    /**
     * <p>Description:包装异常情况的返回请求端的对象</p>
     *
     * @version 1.0
     */
    private String getResMapJsonStr(String code, String error_code, String msg) {
        HashMap resMap = new HashMap();
        String rtnMsg = "";
        Object obj = BizErrorMsgContext.singleton().getValueAsString(error_code);
        if (obj != null && !"".equals(obj)) {
            rtnMsg = (String) obj;
        }
        if (!"".equals(rtnMsg)) {
            msg = rtnMsg;
        }
        resMap.put("code", code);
        resMap.put("message", (msg == null) ? "" : msg);
        resMap.put("resdata", "");
        return JSONObject.toJSONString(resMap);
    }

    /**
     * <p>Description:包装异常情况的返回请求端的对象</p>
     *
     * @version 1.0
     */
    private String getResMapJsonStrByUpdateConfig(String code, String msg) {
        HashMap resMap = new HashMap();
        resMap.put("code", code);
        resMap.put("message", (msg == null) ? "" : msg);
        return JSONObject.toJSONString(resMap);
    }
}
