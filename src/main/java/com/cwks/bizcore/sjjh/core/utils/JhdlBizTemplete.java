package com.cwks.bizcore.sjjh.core.utils;

import java.util.concurrent.ConcurrentHashMap;

import com.cwks.bizcore.sjjh.core.config.log.JhLogWritter;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cwks.bizcore.sjjh.core.vo.BeansUtils;
import com.cwks.bizcore.sjjh.core.vo.JhDlTempletEvent;

/**
 * JhdlBizTemplete
 * @author cssnj
 * @version 1.0
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml","classpath*:config/webservice/webservice.xml"})
public class JhdlBizTemplete {

	/**
	 *
	 * @param jhDlTempletEvent
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes","unchecked" })
	public static JhDlTempletEvent reqTemplete(JhDlTempletEvent jhDlTempletEvent, JhLogWritter jhLogWritter) throws Exception {
		jhLogWritter.sysDebug("JhdlBizTemplete.reqTemplete");
		ConcurrentHashMap configMap=jhDlTempletEvent.getJhqzfw_resource_map();
		String reqTemplete=(String) configMap.get("reqTemplete");
		String[] reqTempleteAry=reqTemplete.split("_");
		Class c2 = Class.forName(reqTempleteAry[0]);
		Object obj = BeansUtils.getBean(c2);
		return (JhDlTempletEvent)obj.getClass().getDeclaredMethod(reqTempleteAry[1],JhDlTempletEvent.class,JhLogWritter.class).invoke(obj,jhDlTempletEvent,jhLogWritter);
	}
	/**
	 * 
	 * @param jhDlTempletEvent
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	public static JhDlTempletEvent processTemplete(JhDlTempletEvent jhDlTempletEvent, JhLogWritter jhLogWritter) throws Exception {
		jhLogWritter.sysDebug("JhdlBizTemplete.processTemplete");
		ConcurrentHashMap configMap=jhDlTempletEvent.getJhqzfw_resource_map();
		String processTemplete=(String) configMap.get("processTemplete");
		String[] processTempleteAry=processTemplete.split("_");
		Class c2 = Class.forName(processTempleteAry[0]);
		Object obj = BeansUtils.getBean(c2);
		return (JhDlTempletEvent)obj.getClass().getDeclaredMethod(processTempleteAry[1],JhDlTempletEvent.class,JhLogWritter.class).invoke(obj,jhDlTempletEvent,jhLogWritter);
	}
	/**
	 * 
	 * @param jhDlTempletEvent
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	public static JhDlTempletEvent resTemplete(JhDlTempletEvent jhDlTempletEvent, JhLogWritter jhLogWritter) throws Exception {
		jhLogWritter.sysDebug("JhdlBizTemplete.resTemplete");
		ConcurrentHashMap configMap=jhDlTempletEvent.getJhqzfw_resource_map();
		String resTemplete=(String) configMap.get("resTemplete");
		String[] resTempleteAry=resTemplete.split("_");
		Class c2 = Class.forName(resTempleteAry[0]);
		Object obj = BeansUtils.getBean(c2);
		return (JhDlTempletEvent)obj.getClass().getDeclaredMethod(resTempleteAry[1],JhDlTempletEvent.class,JhLogWritter.class).invoke(obj,jhDlTempletEvent,jhLogWritter);
	}
}
