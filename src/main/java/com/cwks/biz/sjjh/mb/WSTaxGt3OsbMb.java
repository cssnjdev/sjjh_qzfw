package com.cwks.biz.sjjh.mb;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import com.cwks.biz.sjjh.util.HttpClientUtil;
import com.cwks.bizcore.sjjh.core.config.loger.JhLogWritter;
import com.cwks.bizcore.sjjh.core.vo.JhDlTempletEvent;


/**
 * WebServicesTaxMb
 *
 * @author CSSNJ
 * @version 1.0
 */
@Component
public class WSTaxGt3OsbMb {
	private final static String DATA_SOURCE_ID="WEBSERVICE_TAX_GT3_OSB";
   /**
	 * 
	 * 请求外部接口获取接口返回数据
	 * @param jhDlTempletEvent
	 * @param jhLogWritter
	 * @return
	 * @throws BeansException
	 */
	@SuppressWarnings("rawtypes")
	public JhDlTempletEvent sendWebservice(JhDlTempletEvent jhDlTempletEvent, JhLogWritter jhLogWritter)throws Exception {
		jhLogWritter.bizDebug("*******1.获取报文信息*********");
		String jsons=jhDlTempletEvent.getReqBizMsg();
		ConcurrentHashMap dataSourceMap=jhDlTempletEvent.getDataSoureces();
		ConcurrentHashMap dataWsMap=(ConcurrentHashMap)dataSourceMap.get(DATA_SOURCE_ID);
		HttpClientUtil httpClientUtil = (HttpClientUtil) dataWsMap.get(DATA_SOURCE_ID);
		String url=(String)dataWsMap.get("url");
		String soapHead=(String)dataWsMap.get("soapHead");
		String soapEnd=(String)dataWsMap.get("soapEnd");
		String soapFilterHead=(String)dataWsMap.get("soapFilterHead");
		String soapFilterEnd=(String)dataWsMap.get("soapFilterEnd");
		String resXml=httpClientUtil.sendPostRequest(DATA_SOURCE_ID,url,jsons,soapHead, soapEnd,soapFilterHead,soapFilterEnd);
		jhDlTempletEvent.setReturnMsg(resXml);
		return jhDlTempletEvent;
	}
	/**
	 * 处理、清洗报文数据
	 * 
	 * @param requestEvent
	 * @return
	 * @throws BeansException
	 */
	public JhDlTempletEvent processingData(JhDlTempletEvent jhDlTempletEvent, JhLogWritter jhLogWritter) throws  Exception {
		
		return jhDlTempletEvent;
	}
	/**
	 * 组装返回报文
	 * 
	 * @param requestEvent
	 * @return
	 * @throws BeansException
	 */
	public JhDlTempletEvent returnData(JhDlTempletEvent jhDlTempletEvent, JhLogWritter jhLogWritter)throws Exception {
			jhDlTempletEvent.setReturnCode(1);
		return jhDlTempletEvent;
	}
}
