package com.cwks.bizcore.sjjh.core.job;

import com.cwks.bizcore.sjjh.core.config.log.JhLogEvent;
import com.cwks.bizcore.sjjh.core.service.JhQzFwService;
import com.cwks.bizcore.sjjh.core.vo.JhJobTempletEvent;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.sjjh.core.config.JhQzFwContext;
import com.cwks.bizcore.sjjh.core.config.log.JhLogWritter;
import com.cwks.bizcore.sjjh.core.vo.BeansUtils;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ConcurrentHashMap;

public class JhFwQuartzJob extends QuartzJobBean {

	@Autowired
	private JhLogWritter jhLogWritter;

	@Autowired
	private JhQzFwService jhQzFwService;

	@Autowired
	private JhLogEvent jhLogEvent;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void executeInternal(JobExecutionContext counter) throws JobExecutionException {
/*
		long ms = System.currentTimeMillis();
		System.out.println("\t\t" + new Date(ms));
		System.out.println(ms);
		System.out.println("(" + counter + ")");
*/
		String templete =null,method=null,resource_id=null,access_client_id=null;
		if(counter != null && counter.getMergedJobDataMap() != null){
			if(counter.getMergedJobDataMap().get("templete") != null){
				templete = (String) counter.getMergedJobDataMap().get("templete");
			}
			if(counter.getMergedJobDataMap().get("method") != null){
				method = (String) counter.getMergedJobDataMap().get("method");
			}
			if(counter.getMergedJobDataMap().get("resource_id") != null){
				resource_id = (String) counter.getMergedJobDataMap().get("resource_id");
			}
			if(counter.getMergedJobDataMap().get("access_client_id") != null){
				access_client_id = (String) counter.getMergedJobDataMap().get("access_client_id");
			}
		}
		JhJobTempletEvent jhJobTempletEvent = new JhJobTempletEvent();
		jhJobTempletEvent.setRequestClientIp(JhQzFwContext.singleton().getAppHost());
		if(templete == null){
			jhLogWritter.bizDebug(" ERROR: 定时任务请求-JhFwQuartzJob PRARMS[templete] IS NULL!");
			return;
		}
		if(method == null){
			jhLogWritter.bizDebug(" ERROR: 定时任务请求-JhFwQuartzJob PRARMS[method] IS NULL!");
			return;
		}
		if(resource_id == null){
			jhLogWritter.bizDebug(" ERROR: 定时任务请求-JhFwQuartzJob PRARMS[resource_id] IS NULL!");
			return;
		}else{
			jhJobTempletEvent.setResourceId(resource_id);
			jhLogEvent.setJh_resourceId(jhJobTempletEvent.getResourceId());
		}
		if(access_client_id == null){
			jhLogWritter.bizDebug(" ERROR: 定时任务请求-JhFwQuartzJob PRARMS[access_client_id] IS NULL!");
			return;
		}else{
			jhJobTempletEvent.setAccessClientId(access_client_id);
			jhLogEvent.setJh_resourceId(jhJobTempletEvent.getResourceId());
		}
		 
		jhLogWritter.bizDebug(" 0.0定时任务请求-准备请求模板.");
		try {
			Class c2 = Class.forName(templete);
			Object obj = BeansUtils.getBean(c2);
			JhJobTempletEvent templetEvent = (JhJobTempletEvent)obj.getClass().getDeclaredMethod(method,JhJobTempletEvent.class,JhLogWritter.class).invoke(obj,jhJobTempletEvent,jhLogWritter);
			if(templetEvent != null){
				long startTime=System.currentTimeMillis();
				ConcurrentHashMap reqMap = new ConcurrentHashMap();
				ConcurrentHashMap resMap = new ConcurrentHashMap();
				jhLogEvent.setJh_accessClientId(jhJobTempletEvent.getAccessClientId());
				jhLogEvent.setJh_accessClientIp(jhJobTempletEvent.getRequestClientIp());
				jhLogEvent.setJh_transactionId(jhJobTempletEvent.getTransactionId());
				jhLogEvent.setJh_host(JhQzFwContext.singleton().getAppHost());
				jhLogEvent.setJh_status("0");
				jhLogWritter.setSessionInfo(jhLogEvent);
				jhLogWritter.bizInfo("0.1定时任务请求-该请求通过一般校验，进入数据交换前置服务webservice外部服务接口！");
				String resStr="";
				try {
					reqMap.put("TRANSACTION_ID", jhJobTempletEvent.getTransactionId());
					reqMap.put("REQUSET-CLIENT-IP",jhJobTempletEvent.getRequestClientIp());
					reqMap.put("ACCESS-CLIENT-ID",jhJobTempletEvent.getAccessClientId());
					reqMap.put("RESOURCE_ID",jhJobTempletEvent.getResourceId());
					reqMap.put("REQUSET_MESSAGE",jhJobTempletEvent.getRequestMessage());
					ResponseEvent resEvent = null;
					RequestEvent req = new RequestEvent();
					req.setReqCurMap(reqMap);

					long endTime=System.currentTimeMillis();
					jhLogEvent.setJh_elapsed(endTime-startTime);
					jhLogWritter.setSessionInfo(jhLogEvent);
					jhLogWritter.bizInfo("0.2定时任务请求-调用数据交换前置内部服务开始！");
					resEvent = jhQzFwService.jhdl_qz_deal001(req);
					endTime=System.currentTimeMillis();
					jhLogEvent.setJh_elapsed(endTime-startTime);
					jhLogWritter.setSessionInfo(jhLogEvent);
					jhLogWritter.bizInfo("0.3定时任务请求-调用数据交换前置内部服务结束！");
					if(resEvent.getResCurMap() == null){
						jhLogWritter.bizDebug("ERROR: JhFwQuartzJob PRARMS[templete] IS NULL!");
					}
					resMap.put("RESOURCE_ID", resource_id);
					resMap.put("code", String.valueOf(resEvent.getResCode()));
					resMap.put("message", resEvent.getResMsg());
					resMap.put("resdata", resEvent.getResStr());
					resStr = JsonUtil.toJson(resMap);
					jhLogWritter.bizInfo("0.4定时任务请求-调用返回结果："+resStr);
				}catch (Exception e){
					jhLogWritter.bizError("error: 定时任务请求-该请求进入webservice jhdl_wbfw数据交换前置内部服务过程发生异常！",e);
				}
				jhLogWritter.bizDebug(this.getClass().getName()+".jhdl_wbfw 方法结束");
			}
		}catch (Exception e) {
			jhLogWritter.bizError(this.getClass().getName()+".executeInternal方法异常：",e);
			return;
		}finally {

		}
	}
}