package com.cwks.biz.sjjh.mq.listener;

import com.cwks.common.log.LogWritter;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.stereotype.Service;

@Service("confirmCallBackListener")
public class ConfirmCallBackListener implements ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    	LogWritter.bizDebug("confirm--:correlationData:" + correlationData + ",ack:" + ack + ",cause:" + cause);
    }
}
