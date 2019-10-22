package com.cwks.biz.sjjh.mq.listener;

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
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml","classpath*:spring/applicationContext_db.xml","classpath*:spring/_applicationContext_rabbitMQ.xml"})
public class TestConfirm {
    //@Resource
    private AmqpTemplate amqpTemplate;

    private static String exChange = "remoting.exchange";

    @Test
    public void test1() throws InterruptedException{
        if(amqpTemplate == null){
            amqpTemplate = (AmqpTemplate)SpringContextUtil.getBean("amqpTemplate");
        }
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("test1---message:"+message);
        //exchange,queue 都正确,confirm被回调, ack=true
        amqpTemplate.convertAndSend(exChange, "remoting.queue.test1Key", message);
        Thread.sleep(1000);
    }

    @Test
    public void test2() throws InterruptedException{
        if(amqpTemplate == null){
            amqpTemplate = (AmqpTemplate)SpringContextUtil.getBean("amqpTemplate");
        }
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("test2---message:"+message);
        //exchange 错误,queue 正确,confirm被回调, ack=false
        amqpTemplate.convertAndSend(exChange+"NO", "remoting.queue.test1Key", message);
        Thread.sleep(1000);
    }

    @Test
    public void test3() throws InterruptedException{
        if(amqpTemplate == null){
            amqpTemplate = (AmqpTemplate)SpringContextUtil.getBean("amqpTemplate");
        }
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("test3---message:"+message);
        //exchange 正确,queue 错误 ,confirm被回调, ack=true; return被回调 replyText:NO_ROUTE
        amqpTemplate.convertAndSend(exChange, "", message);
        //Thread.sleep(1000);
    }

    @Test
    public void test4() throws InterruptedException{
        if(amqpTemplate == null){
            amqpTemplate = (AmqpTemplate)SpringContextUtil.getBean("amqpTemplate");
        }
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("test4---message:"+message);
        //exchange 错误,queue 错误,confirm被回调, ack=false
        amqpTemplate.convertAndSend(exChange+"NO", "123", message);
        Thread.sleep(1000);
    }
}
