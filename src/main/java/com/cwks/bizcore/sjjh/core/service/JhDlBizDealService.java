package com.cwks.bizcore.sjjh.core.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cwks.bizcore.sjjh.core.config.JhQzFwContext;
import com.cwks.bizcore.sjjh.core.config.loger.JhLogEvent;
import com.cwks.bizcore.sjjh.core.config.loger.JhLogWritter;
import com.cwks.bizcore.sjjh.core.utils.JhdlBizCoreUtil;
import com.cwks.bizcore.sjjh.core.utils.JhdlBizTemplete;
import com.cwks.bizcore.sjjh.core.vo.JhDlTempletEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.systemConfig.BizErrorMsgContext;


/**
 * <p>File: JhDlBizDealService.java</p>
 * <p>Title: JhDlBizDealService</p>
 * <p>Description: 核心调用服务</p>
 *
 * @author admin
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Component
@Service("jhdlBizDealService")
public class JhDlBizDealService {

	@Autowired
	private JhLogWritter jhLogWritter;

	@Autowired
	private ObjectFactory<JhLogEvent> jhLogEventFactory;
	
	@Autowired
	private JhdlBizCoreUtil jhdlBizCoreUtil;
	
	@Autowired
	private ObjectFactory<JhDlTempletEvent> templetEventFactory;
	
    /**
     * @param requestEvent
     * @return ResponseEvent
     * @throws Exception
     */
	@SuppressWarnings({ "rawtypes"})
	public ResponseEvent dealBiz(RequestEvent requestEvent) throws Exception {
		JhLogEvent jhLogEvent=jhLogEventFactory.getObject();
		jhLogWritter.bizDebug("5.1 数据交换前置内部核心交换业务逻辑服务开始 ");
		long startTime=System.currentTimeMillis();
		if (requestEvent == null) {
			throw new Exception("RequestEvent:null param requestEvent!");
		}
		JhDlTempletEvent templetEvent=templetEventFactory.getObject();
        ResponseEvent resEvent = new ResponseEvent();
        ConcurrentHashMap<?, ?> reqmap =requestEvent.getReqCurMap();
		String resourceId = (String) reqmap.get("RESOURCE_ID");
		String transactionId = (String) reqmap.get("TRANSACTION_ID");

		jhLogEvent.setJh_resourceId(resourceId);
		jhLogEvent.setJh_accessClientId((String) reqmap.get("ACCESS-CLIENT-ID"));
		if(reqmap.get("REQUSET-CLIENT-IP") != null){
			jhLogEvent.setJh_accessClientIp((String) reqmap.get("REQUSET-CLIENT-IP"));
		}
		jhLogEvent.setJh_transactionId((String) reqmap.get("TRANSACTION_ID"));
		jhLogEvent.setJh_host(JhQzFwContext.singleton().getAppHost());
		jhLogEvent.setJh_status("0");
		jhLogWritter.setSessionInfo(jhLogEvent);
        //本资源的配置信息
        ConcurrentHashMap jhqzfwResourceMap =(ConcurrentHashMap)reqmap.get("JHQZFW_RESOURCES_MAP");
		//本资源要调用的数据源配置信息
        ConcurrentHashMap jhqzfwDatasourceMap =(ConcurrentHashMap)reqmap.get("JHQZFW_DATASOURCE_MAP");

		templetEvent.setResourceId(resourceId);
		templetEvent.setTransactionId(transactionId);
		templetEvent.setReqBizMsg( requestEvent.getReqWsXml());
		templetEvent.setJhqzfw_resource_map(jhqzfwResourceMap);
		try {
			ConcurrentHashMap dataSource=jhdlBizCoreUtil.getSourcesConfigById((String)jhqzfwResourceMap.get("templeteDataSources"),jhqzfwDatasourceMap,jhqzfwResourceMap);
			templetEvent.setDataSoureces(dataSource);
		} catch (Exception e) {
			resEvent.setResStr(BizErrorMsgContext.singleton().getValueAsString("BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0006")+","+e.toString());
			resEvent.setResCode(-1);
		}
		long endTime=System.currentTimeMillis();
		try {
			jhLogWritter.bizDebug("5.2 进入"+this.getClass().getName()+".dealBiz方法==模板 reqTemplete 开始");
			jhLogEvent.setJh_elapsed(endTime-startTime);
			jhLogWritter.setSessionInfo(jhLogEvent);
			templetEvent = JhdlBizTemplete.reqTemplete(templetEvent,jhLogWritter);
			endTime=System.currentTimeMillis();
			jhLogEvent.setJh_elapsed(endTime-startTime);
			jhLogWritter.setSessionInfo(jhLogEvent);
			jhLogWritter.bizDebug("5.2 进入"+this.getClass().getName()+".dealBiz方法==模板 reqTemplete 结束");
		}catch (Exception e) {
			jhdlBizCoreUtil.rollback(templetEvent.getDataSoureces(),(String) jhqzfwResourceMap.get("isRollback"));
			jhLogWritter.bizError(this.getClass().getName()+".dealBiz方法异常：",e);
			resEvent.setResStr(BizErrorMsgContext.singleton().getValueAsString("BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0007")+","+e.toString());
			resEvent.setResCode(-1);
			jhdlBizCoreUtil.closePools(templetEvent.getDataSoureces());
			return resEvent;
		}finally {
		}
		try {
			jhLogWritter.bizDebug("5.3 进入"+this.getClass().getName()+".dealBiz方法==模板 processTemplete 开始");
			endTime=System.currentTimeMillis();
			jhLogEvent.setJh_elapsed(endTime-startTime);
			jhLogWritter.setSessionInfo(jhLogEvent);
			templetEvent= JhdlBizTemplete.processTemplete(templetEvent,jhLogWritter);
			endTime=System.currentTimeMillis();
			jhLogEvent.setJh_elapsed(endTime-startTime);
			jhLogWritter.setSessionInfo(jhLogEvent);
			jhLogWritter.bizDebug("5.3 进入"+this.getClass().getName()+".dealBiz方法==模板 processTemplete 结束");
		}catch (Exception e) {
			jhdlBizCoreUtil.rollback(templetEvent.getDataSoureces(),(String) jhqzfwResourceMap.get("isRollback"));
			jhLogWritter.bizError(this.getClass().getName()+".dealBiz方法异常：",e);
			resEvent.setResStr(BizErrorMsgContext.singleton().getValueAsString("BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0008")+","+e.toString());
			resEvent.setResCode(-1);
			jhdlBizCoreUtil.closePools(templetEvent.getDataSoureces());
			return resEvent;
		}finally {
		}
		
		try {
			jhLogWritter.bizDebug("5.4 进入"+this.getClass().getName()+".dealBiz方法==模板 resTemplete 开始");
			endTime=System.currentTimeMillis();
			jhLogEvent.setJh_elapsed(endTime-startTime);
			jhLogWritter.setSessionInfo(jhLogEvent);
			templetEvent = JhdlBizTemplete.resTemplete(templetEvent,jhLogWritter);
			endTime=System.currentTimeMillis();
			jhLogEvent.setJh_elapsed(endTime-startTime);
			jhLogWritter.setSessionInfo(jhLogEvent);
			jhLogWritter.bizDebug("5.4 进入"+this.getClass().getName()+".dealBiz方法==模板 resTemplete 结束");
			resEvent.setResCode(templetEvent.getReturnCode());
		}catch (Exception e) {
			jhdlBizCoreUtil.rollback(templetEvent.getDataSoureces(),(String) jhqzfwResourceMap.get("isRollback"));
			jhLogWritter.bizError("error: "+this.getClass().getName()+".dealBiz方法异常：",e);
			resEvent.setResStr(BizErrorMsgContext.singleton().getValueAsString("BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0009")+","+e.toString());
			resEvent.setResCode(-1);
			return resEvent;
		}finally {
			jhdlBizCoreUtil.closePools(templetEvent.getDataSoureces());
		}
        resEvent.setResStr(templetEvent.getReturnMsg());
		jhLogWritter.bizDebug("5.5 数据交换前置内部核心交换业务逻辑服务结束 ");
        return resEvent;
    }

}
