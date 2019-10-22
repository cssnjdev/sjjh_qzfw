package com.cwks.bizcore.sjjh.core.vo;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component
@Configuration
@Scope("prototype")
public class JhDlTempletEvent implements Serializable {
    private static final long serialVersionUID = 2093331683336500554L;

    protected String reqBizMsg;
    protected String resourceId;
    protected ConcurrentHashMap dataSoureces;
    protected ConcurrentHashMap jhqzfw_resource_map;
    protected List tempList;
    protected ConcurrentHashMap tempMap = new ConcurrentHashMap();
    protected int returnCode = 0;
    protected String returnMsg = "";
    protected String transactionId = "";


    public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public ConcurrentHashMap getJhqzfw_resource_map() {
		return jhqzfw_resource_map;
	}

	public void setJhqzfw_resource_map(ConcurrentHashMap jhqzfw_resource_map) {
		this.jhqzfw_resource_map = jhqzfw_resource_map;
	}

	public JhDlTempletEvent() {

    }

    public ConcurrentHashMap getDataSoureces() {
        return dataSoureces;
    }

    public void setDataSoureces(ConcurrentHashMap dataSoureces) {
        this.dataSoureces = dataSoureces;
    }

    public List getTempList() {
        return tempList;
    }

    public void setTempList(List tempList) {
        this.tempList = tempList;
    }

    public ConcurrentHashMap getTempMap() {
        return tempMap;
    }

    public void setTempMap(ConcurrentHashMap tempMap) {
        this.tempMap = tempMap;
    }

    public String getReqBizMsg() {
        return reqBizMsg;
    }

    public void setReqBizMsg(String reqBizMsg) {
        this.reqBizMsg = reqBizMsg;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }


}
