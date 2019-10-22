package com.cwks.biz.sjjh.job;

import com.cwks.bizcore.sjjh.core.config.log.JhLogWritter;
import com.cwks.bizcore.sjjh.core.utils.CusAccessObjectUtil;
import com.cwks.bizcore.sjjh.core.vo.JhJobTempletEvent;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;


/**
 * ExcelTaxMb
 *
 * @author CSSNJ
 * @version 1.0
 */
@Component
public class ExcelTaxMbJob {


    /**
     * 初始化
     *
     * @param jhJobTempletEvent
     * @return
     * @throws BeansException
     */
    public JhJobTempletEvent init(JhJobTempletEvent jhJobTempletEvent,JhLogWritter jhLogWritter) throws BeansException {
        if (jhJobTempletEvent != null) {
            //String resource_id = jhJobTempletEvent.getResourceId();
            String tran_id = CusAccessObjectUtil.getUUID32();
            jhJobTempletEvent.setTransactionId(tran_id);
            StringBuffer reqxml = new StringBuffer("");
            reqxml.append("{\"path\":\"/20190704\",\"filename\":\"tmp001.xlsx\"}");
            jhJobTempletEvent.setRequestMessage(reqxml.toString());
        }
        return jhJobTempletEvent;
    }
}
