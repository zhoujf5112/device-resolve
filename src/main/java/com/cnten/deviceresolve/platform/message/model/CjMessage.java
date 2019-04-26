package com.cnten.deviceresolve.platform.message.model;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bd_cj_message")
public class CjMessage{

    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Integer messageId;
    private String message;
    private String messageTop;
    private String devCode;
    private String data;
    private String messageType;
    private String isCheckTrue;
    private String dataTime;
    private String bdCard;

    public String getBdCard() {
        return bdCard;
    }

    public void setBdCard(String bdCard) {
        this.bdCard = bdCard;
    }

    public String getIsCheckTrue() {
        return isCheckTrue;
    }

    public void setIsCheckTrue(String isCheckTrue) {
        this.isCheckTrue = isCheckTrue;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageTop(String messageTop) {
        this.messageTop = messageTop;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageTop() {
        return messageTop;
    }

    public String getDevCode() {
        return devCode;
    }

    public String getData() {
        return data;
    }

    public String getMessageType() {
        return messageType;
    }
}
