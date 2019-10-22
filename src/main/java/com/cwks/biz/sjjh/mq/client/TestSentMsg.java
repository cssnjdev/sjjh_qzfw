package com.cwks.biz.sjjh.mq.client;

import com.cwks.common.core.systemConfig.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml","classpath*:spring/applicationContext_db.xml","classpath*:spring/applicationContext_rabbitMQ_Client.xml"})
public class TestSentMsg {

    //@Resource
    private AmqpTemplate amqpTemplate;

    @Test
    //@Transactional   //标明此方法需使用事务
    //@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void sendDataToQueue() {
    	if(amqpTemplate == null){
			amqpTemplate = (AmqpTemplate)SpringContextUtil.getBean("amqpTemplate");
		}
        try {
            for(int i=0;i<10;i++){
                //amqpTemplate.convertAndSend("remoting.queue.test1Key", "test spring async=>"+i);
                //Thread.sleep(100);
            	amqpTemplate.convertAndSend( "test spring async=>"+i);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
