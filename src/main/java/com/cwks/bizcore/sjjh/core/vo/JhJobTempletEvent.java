package com.cwks.bizcore.sjjh.core.vo;

import java.io.Serializable;

public class JhJobTempletEvent implements Serializable {

    protected String transactionId;
    protected String resourceId;
    protected String accessClientId;
    protected String requestClientIp;
    protected String requestMessage;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAccessClientId() {
        return accessClientId;
    }

    public void setAccessClientId(String accessClientId) {
        this.accessClientId = accessClientId;
    }

    public String getRequestClientIp() {
        return requestClientIp;
    }

    public void setRequestClientIp(String requestClientIp) {
        this.requestClientIp = requestClientIp;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }


}
