package com.cwks.bizcore.sjjh.core.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cwks.bizcore.comm.utils.AESCoder;
import com.cwks.bizcore.sjjh.core.config.JhQzFwContext;
import com.cwks.bizcore.sjjh.core.config.loger.JhLogEvent;
import com.cwks.bizcore.sjjh.core.config.loger.JhLogWritter;
import com.cwks.bizcore.sjjh.core.utils.ComProConf;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.service.ServiceDelegate;

/**
 * <p>File: JhQzFwService.java</p>
 * <p>Title: JhQzFwService</p>
 * <p>Description: 数据交换前置服务service</p>
 * @author cssnj
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Component
@Service
@Transactional(timeout=599)
public class JhQzFwService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JhDlBizDealService jhDlBizDealService;

    @Autowired
    private JhLogWritter jhLogWritter;

    @Autowired
    private ObjectFactory<JhLogEvent> jhLogEventFactory;
    
    private static final String asynchronous="asynchronous";
    /**
     * <p>方法名：jhdl_qz_deal001</p>
     * <p>Description:处理外部请求前置服务的第一层处理</p>
     * @version 1.0
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEvent jhdl_qz_deal001(RequestEvent requestEvent) throws Exception {
        JhLogEvent jhLogEvent=jhLogEventFactory.getObject();
        jhLogWritter.bizDebug(this.getClass().getName()+".jhdl_qz_deal001 方法开始");
        ResponseEvent resEvent = new ResponseEvent();
        if(requestEvent.getReqCurMap() == null){
            resEvent.setResCode(-1);
            resEvent.setResMsg("请求的对象为空，处理失败!");
            resEvent.setResStr("");
        }
        ConcurrentHashMap reqMap = requestEvent.getReqCurMap();
        if(reqMap.get("TRANSACTION_ID") == null || "".equals(reqMap.get("TRANSACTION_ID"))){
            resEvent.setResCode(-1);
            resEvent.setResMsg("交易事物ID为空!");
            resEvent.setResStr("");
        }
        String tran_id = (String)reqMap.get("TRANSACTION_ID");
        String accessClentID = (String)reqMap.get("ACCESS-CLIENT-ID");
        jhLogEvent.setJh_transactionId(tran_id);
        if(reqMap.get("REQUSET-CLIENT-IP") == null || "".equals(reqMap.get("REQUSET-CLIENT-IP"))){
            resEvent.setResCode(-1);
            resEvent.setResMsg("tran_id:"+tran_id+",请求端IP为空，处理失败!");
            resEvent.setResStr("");
        }
        jhLogEvent.setJh_accessClientIp((String) reqMap.get("REQUSET-CLIENT-IP"));
        jhLogEvent.setJh_accessClientId((String) reqMap.get("ACCESS-CLIENT-ID"));
        if(reqMap.get("RESOURCE_ID") == null || "".equals(reqMap.get("RESOURCE_ID"))){
            resEvent.setResCode(-1);
            resEvent.setResMsg("tran_id:"+tran_id+",请求的资源ID为空，处理失败!");
            resEvent.setResStr("");
        }
        String resource_id = (String)reqMap.get("RESOURCE_ID");
        jhLogEvent.setJh_resourceId((String)reqMap.get("RESOURCE_ID"));
        jhLogWritter.setSessionInfo(jhLogEvent);
        if(reqMap.get("REQUSET_MESSAGE") == null || "".equals(reqMap.get("REQUSET_MESSAGE"))){
            resEvent.setResCode(-1);
            resEvent.setResMsg("tran_id:"+tran_id+",请求的报文为空，处理失败!");
            resEvent.setResStr("");
        }
        ConcurrentHashMap resourceRuleMap = JhQzFwContext.singleton().getResourceRuleMapById(resource_id);

        if(resourceRuleMap == null){
            resEvent.setResCode(-1);
            resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]未找到匹配的前置处理逻辑规则!");
            resEvent.setResStr("");
            return resEvent;
        }

        String client_ips,allow_req_ip = null;
        client_ips = (String)reqMap.get("REQUSET-CLIENT-IP");
        /*client_id = (resourceRuleMap.get("ACCESS-CLIENT-ID")==null) ? "" :(String)resourceRuleMap.get("ACCESS-CLIENT-ID");
        id = (resourceRuleMap.get("resourceId")==null) ? "" :(String)resourceRuleMap.get("resourceId");
        des = (resourceRuleMap.get("des")==null) ? "" :(String)resourceRuleMap.get("des");*/
        allow_req_ip = (resourceRuleMap.get("allow_req_ip")==null) ? "" :(String)resourceRuleMap.get("allow_req_ip");
        StringBuffer req_ips = null;
        if(allow_req_ip != null && !"".equals(allow_req_ip)){
            String[] arr_allow_ips = allow_req_ip.split(",");
            String[] arr_client_ips = client_ips.split(",");
            req_ips = new StringBuffer();
            boolean isIpPass = false;
            if(arr_allow_ips.length >= arr_client_ips.length){
                for(int i=0;i<arr_allow_ips.length;i++) {
                    if(!isIpPass){
                        for(int j=0;j<arr_client_ips.length;j++) {
                            req_ips.append(arr_client_ips[j]+"|");
                            if(arr_client_ips[j].equals(arr_allow_ips[i])){
                                isIpPass = true;
                                break;
                            }
                        }
                    }
                }
            }else{
                for(int i=0;i<arr_client_ips.length;i++) {
                    if(!isIpPass){
                        for(int j=0;j<arr_allow_ips.length;j++) {
                            req_ips.append(arr_client_ips[j]+"|");
                            if(arr_allow_ips[j].equals(arr_client_ips[i])){
                                isIpPass = true;
                                break;
                            }
                        }
                    }
                }
            }
            if(!isIpPass){
                jhLogWritter.sysError("error: 交换数据处理失败，前置处理规则请求IP策略验证未通过!",null);
                resEvent.setResCode(-1);
                if(arr_client_ips == null || arr_client_ips.length <=0){
                    resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]，client_ip[空]前置处理规则请求IP策略验证未通过!");
                }else{
                    resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]，client_ip["+req_ips.toString()+"]前置处理规则请求IP策略验证未通过!");
                }
                resEvent.setResStr("");
                return resEvent;
            }
        }
        ConcurrentHashMap resourceClientMap = JhQzFwContext.singleton().getResourceClientMapById(accessClentID);
        if(resourceClientMap==null) {
        	 resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]，client_ip["+req_ips.toString()+"]前置处理规则：请求接入点订阅策略不存在!");
        	 return resEvent;
        }
        ConcurrentHashMap reqResouceMap=(ConcurrentHashMap)((Map) resourceClientMap.get("reqResources")).get(reqMap.get("RESOURCE_ID"));
        if(reqResouceMap==null) {
       	 resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]，client_ip["+req_ips.toString()+"]前置处理规则：请求接入点订阅资源策略验证未通过!");
       	 return resEvent;
       }
        reqMap.putAll(reqResouceMap);
        String reqModel=(String) reqResouceMap.get("reqModel");
        //restful 调用数据交换管理中心
        String islocal = ComProConf.JHDL_RESTAPI_NBFW_URL_ISLOCAL;
        ConcurrentHashMap resMap = null;
        ResponseEvent deal002_resEvent = null;
        RequestEvent deal002_req = new RequestEvent();
        //走交换管理中心模式
        if(islocal != null && "false".equals(islocal)){
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.add("X-Auth-Token", UUID.randomUUID().toString());
//            MultiValueMap<String, ConcurrentHashMap> postParameters = new LinkedMultiValueMap<String, ConcurrentHashMap>();
//            postParameters.add("QZFW_REQUEST_CUR_MAP", reqMap);
//            HttpEntity<MultiValueMap<String, ConcurrentHashMap>> requestEntity = new HttpEntity<MultiValueMap<String, ConcurrentHashMap>>(
//                    postParameters, headers);
            reqMap.put("REQUSET_MESSAGE", AESCoder.encrypt((String)reqMap.get("REQUSET_MESSAGE"), JhQzFwContext.singleton().getJhdlCoreWbfwSecretPwd()));
            //走MQ异步路由消息模式
            if(JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigIsopen() != null 
            		&& "true".equals(JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigIsopen())
            			&&asynchronous.equals(reqModel)){
                resEvent = sentMqMessage(reqMap,resEvent);
            }else{//走restful接口调用模式
                resMap = restTemplate.postForObject(ComProConf.JHDL_RESTAPI_NBFW_URL, reqMap,ConcurrentHashMap.class);
                if(resMap == null){
                    resEvent.setResCode(-1);
                    resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则处理异常，返回空!");
                    resEvent.setResStr("");
                    return resEvent;
                }
                if(resMap.get("code") == null || "".equals(resMap.get("code"))){
                    resEvent.setResCode(resEvent.getResCode());
                    resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则返回执行状态码为空!");
                    resEvent.setResStr("");
                    return resEvent;
                }
                if(resMap.get("message") == null || "".equals(resMap.get("message"))){
                    resEvent.setResCode(resEvent.getResCode());
                    resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则返回执行状态消息为空!");
                    resEvent.setResStr("");
                    return resEvent;
                }
                if(resMap.get("resdata") == null || "".equals(resMap.get("resdata"))){
                    resEvent.setResCode(resEvent.getResCode());
                    resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则返回执行结果信息报文为空! 失败原因："+(String)resMap.get("message"));
                    resEvent.setResStr("");
                    return resEvent;
                }
                resEvent.setResCode(Integer.parseInt((String)resMap.get("code")));
                resEvent.setResMsg((String)resMap.get("message"));
                resEvent.setResStr((String)resMap.get("resdata"));
            }
        }else{//走前置服务本地路由模式
            reqMap.put("REQUSET-CLIENT-IP",JhQzFwContext.singleton().getAppHost());
            //走本地模式
            reqMap.put("IS-LOCAL-MODEL","true");
            JhQzFwContext qzfw = JhQzFwContext.singleton();
            Map resource_map= qzfw.getResourceRuleMapById((String)reqMap.get("RESOURCE_ID"));
            reqMap.putAll(resource_map);
            //本地单实例 调用端前置和资源端前置为一个服务的情况
            if(resource_map.get("localRestFulApiPath") == null || "".equals(resource_map.get("localRestFulApiPath"))){
                reqMap.put("LOCAL-MODEL","01");
                //走MQ异步路由消息模式
                if(JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigIsopen() != null && 
                		"true".equals(JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigIsopen()) &&
                		JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigTld() != null && 
                		!"".equals(JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigTld())
                		&& asynchronous.equals(reqModel)){
                    resEvent = sentMqMessage(reqMap,resEvent);
                }else{//走内部调用
                    deal002_req.setBeanId("jhQzFwService");
                    deal002_req.setHandleCode("jhdl_qz_deal002");
                    deal002_req.setReqCurMap(reqMap);
                    deal002_req.setCallMethodName("performTask");
                    deal002_resEvent = jhdl_qz_deal002(deal002_req);

                    resEvent.setResCode(deal002_resEvent.getResCode());
                    resEvent.setResMsg(deal002_resEvent.getResMsg());
                    resEvent.setResStr(deal002_resEvent.getResStr());
                }
            }else{
                reqMap.put("LOCAL-MODEL","02");
                reqMap.put("REQUSET_MESSAGE", AESCoder.encrypt((String)reqMap.get("REQUSET_MESSAGE"),JhQzFwContext.singleton().getJhdlCoreWbfwSecretPwd()));
                //走MQ异步路由消息模式
                if(JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigIsopen() != null 
                		&& "true".equals(JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigIsopen()) 
                		&& JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigTld() != null 
                		&& !"".equals(JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigTld())
                		&& asynchronous.equals(reqModel)){
                    resEvent = sentMqMessage(reqMap,resEvent);
                }else{ //走前置调用前置模式
                    String JHDL_LOCAL_RESTAPI_NBFW_URL = (String)resource_map.get("localRestFulApiPath")+ComProConf.JHDL_RESTAPI_NBFW_URL_HZPATH;
                    String json_resMap = restTemplate.postForObject(JHDL_LOCAL_RESTAPI_NBFW_URL,reqMap,String.class);
                    resMap = (ConcurrentHashMap) JSONObject.parse(json_resMap);
                    if(resMap == null){
                        resEvent.setResCode(-1);
                        resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则处理异常，返回空!");
                        resEvent.setResStr("");
                        return resEvent;
                    }
                    if(resMap.get("code") == null || "".equals(resMap.get("code"))){
                        resEvent.setResCode(resEvent.getResCode());
                        resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则返回执行状态码为空!");
                        resEvent.setResStr("");
                        return resEvent;
                    }
                    if(resMap.get("message") == null || "".equals(resMap.get("message"))){
                        resEvent.setResCode(resEvent.getResCode());
                        resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则返回执行状态消息为空!");
                        resEvent.setResStr("");
                        return resEvent;
                    }
                    if(resMap.get("resdata") == null || "".equals(resMap.get("resdata"))){
                        resEvent.setResCode(resEvent.getResCode());
                        resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]前置逻辑规则返回执行结果信息报文为空!");
                        resEvent.setResStr("");
                        return resEvent;
                    }
                    resEvent.setResCode(Integer.parseInt((String)resMap.get("code")));
                    resEvent.setResMsg((String)resMap.get("message"));
                    resEvent.setResStr((String)resMap.get("resdata"));
                }
            }
        }
        jhLogWritter.bizDebug(this.getClass().getName()+".jhdl_qz_deal001 方法结束");
        return resEvent;
    }



    /**
     * <p>方法名：jhdl_qz_deal002</p>
     * <p>Description:处理内部数据交换管理中心过来的请求处理</p>
     * @version 1.0
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEvent jhdl_qz_deal002(RequestEvent requestEvent) throws Exception {
        JhLogEvent jhLogEvent=jhLogEventFactory.getObject();
        jhLogWritter.bizDebug(this.getClass().getName()+".jhdl_qz_deal002 方法开始");
        ResponseEvent resEvent = new ResponseEvent();
        if(requestEvent.getReqCurMap() == null){
            resEvent.setResCode(-1);
            resEvent.setResMsg("交换管理中心请求的对象为空，处理失败!");
            resEvent.setResStr("");
            return resEvent;
        }
        ConcurrentHashMap reqMap = requestEvent.getReqCurMap();
        if(reqMap.get("TRANSACTION_ID") == null || "".equals(reqMap.get("TRANSACTION_ID"))){
            resEvent.setResCode(-1);
            resEvent.setResMsg("交易事物ID为空!");
            resEvent.setResStr("");
        }
        String tran_id = (String)reqMap.get("TRANSACTION_ID");
        if(reqMap.get("REQUSET-CLIENT-IP") == null || "".equals(reqMap.get("REQUSET-CLIENT-IP"))){
            resEvent.setResCode(-1);
            resEvent.setResMsg("tran_id:"+tran_id+",交换管理中心端IP为空，处理失败!");
            resEvent.setResStr("");
            return resEvent;
        }
        if(reqMap.get("RESOURCE_ID") == null || "".equals(reqMap.get("RESOURCE_ID"))){
            resEvent.setResCode(-1);
            resEvent.setResMsg("tran_id:"+tran_id+",交换管理中心请求的资源ID为空，处理失败!");
            resEvent.setResStr("");
            return resEvent;
        }
        if(reqMap.get("REQUSET_MESSAGE") == null || "".equals(reqMap.get("REQUSET_MESSAGE"))){
            resEvent.setResCode(-1);
            resEvent.setResMsg("tran_id:"+tran_id+",交换管理中心请求的报文为空，处理失败!");
            resEvent.setResStr("");
            return resEvent;
        }
        //String islocal = ComProConf.JHDL_RESTAPI_NBFW_URL_ISLOCAL;

        jhLogEvent.setJh_transactionId(tran_id);
        jhLogEvent.setJh_accessClientIp((String) reqMap.get("REQUSET-CLIENT-IP"));
        jhLogEvent.setJh_accessClientId((String) reqMap.get("ACCESS-CLIENT-ID"));
        jhLogEvent.setJh_resourceId((String)reqMap.get("RESOURCE_ID"));
        jhLogWritter.setSessionInfo(jhLogEvent);

        //开始构建资源信息、和数据源信息
        JhQzFwContext qzfw = JhQzFwContext.singleton();
        Map resource_map= qzfw.getResourceRuleMapById((String)reqMap.get("RESOURCE_ID"));
        reqMap.put("JHQZFW_RESOURCES_MAP",resource_map);
        reqMap.put("JHQZFW_DATASOURCE_MAP",qzfw.getDataSourceMap());
        //走本地模式
        if(reqMap.get("IS-LOCAL-MODEL") != null && "true".equals(reqMap.get("IS-LOCAL-MODEL"))){
            if(reqMap.get("LOCAL-MODEL")!=null && "02".equals((String)reqMap.get("LOCAL-MODEL"))){
                reqMap.put("REQUSET_MESSAGE", AESCoder.decrypt((String)reqMap.get("REQUSET_MESSAGE"), JhQzFwContext.singleton().getJhdlCoreWbfwSecretPwd()));
            }
        }else{//走交换管理中心模式
            if(reqMap.get("IS-LOCAL-MODEL")==null || "false".equals((String)reqMap.get("IS-LOCAL-MODEL"))){
                reqMap.put("REQUSET_MESSAGE", AESCoder.decrypt((String)reqMap.get("REQUSET_MESSAGE"), JhQzFwContext.singleton().getJhdlCoreWbfwSecretPwd()));
            }
        }

        String resource_id = (String)reqMap.get("RESOURCE_ID");
        ConcurrentHashMap resourceRuleMap = JhQzFwContext.singleton().getResourceRuleMapById(resource_id);
        if(resourceRuleMap == null){
            resEvent.setResCode(-1);
            resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]未找到匹配的前置处理逻辑规则!");
            resEvent.setResStr("");
            return resEvent;
        }

        String client_ip,allow_res_ip = null;
        //client_id = (resourceRuleMap.get("ACCESS-CLIENT-ID")==null) ? "" :(String)resourceRuleMap.get("ACCESS-CLIENT-ID");
        client_ip = (String)reqMap.get("REQUSET-CLIENT-IP");
        //id = (resourceRuleMap.get("resourceId")==null) ? "" :(String)resourceRuleMap.get("resourceId");
        //des = (resourceRuleMap.get("des")==null) ? "" :(String)resourceRuleMap.get("des");
        allow_res_ip = (resourceRuleMap.get("allow_res_ip")==null) ? "" :(String)resourceRuleMap.get("allow_res_ip");

        if(allow_res_ip != null && !"".equals(allow_res_ip)){
            if(!allow_res_ip.equals(client_ip)){
                jhLogWritter.sysError("交换数据处理失败，前置处理规则请求IP策略验证未通过!",null);
                resEvent.setResCode(-1);
                resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]，client_ip["+client_ip+"]调用第三方资源处理规则请求IP策略验证未通过!");
                resEvent.setResStr("");
                return resEvent;
            }
        }

        RequestEvent req = new RequestEvent();
        req.setReqCurMap(reqMap);
        req.setReqWsXml((String)reqMap.get("REQUSET_MESSAGE"));
        ResponseEvent ruleReqEvent = null;
        try{
            ruleReqEvent = jhDlBizDealService.dealBiz(req);
        }catch(Exception e){
            jhLogWritter.sysError("error: 交换数据处理失败，前置处理业务接口异常!",e);
        }

        if(ruleReqEvent == null){
            resEvent.setResCode(-1);
            resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]调用第三方资源逻辑规则处理异常，返回空!");
            resEvent.setResStr("");
            return resEvent;
        }
        if(ruleReqEvent.getResStr() == null || "".equals(ruleReqEvent.getResStr())){
            resEvent.setResCode(ruleReqEvent.getResCode());
            resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理失败，资源["+resource_id+"]调用第三方资源逻辑规则返回执行情况消息为空!");
            resEvent.setResStr("");
            return resEvent;
        }
        resEvent.setResCode(ruleReqEvent.getResCode());
        resEvent.setResMsg("tran_id:"+tran_id+",交换数据处理成功，资源["+resource_id+"]消息："+ruleReqEvent.getResMsg());
        resEvent.setResStr(ruleReqEvent.getResStr());
        jhLogWritter.bizDebug(this.getClass().getName()+".jhdl_qz_deal002 方法结束");
        return resEvent;
    }


    @SuppressWarnings("rawtypes")
	private ResponseEvent sentMqMessage(ConcurrentHashMap reqMap, ResponseEvent resEvent) throws Exception{
        ResponseEvent mqResEvent = resEvent;
        RequestEvent deal002_req = new RequestEvent();
        ResponseEvent deal002_resEvent = null;
        String tld = JhQzFwContext.singleton().getJhdlCoreNbfwMqConfigTld();
        String BeanID = tld.substring(0, tld.indexOf("_"));
        deal002_req.setHandleCode(tld.substring(tld.indexOf("_") + 1));
        deal002_req.setBeanId(BeanID);
        deal002_req.setReqCurMap(reqMap);
        deal002_req.setCallMethodName("performTask");
        deal002_resEvent = ServiceDelegate.delegate(deal002_req);
        mqResEvent.setResCode(deal002_resEvent.getResCode());
        mqResEvent.setResMsg(deal002_resEvent.getResMsg());
        mqResEvent.setResStr(deal002_resEvent.getResStr());
        return mqResEvent;
    }
}
