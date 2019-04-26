package com.cnten.deviceresolve.platform.resolve.model;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="bd_cj_resolve")
public class CjResolve{

    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Integer resolveId;
    private Integer messageId;
    private String devSerial;
    private String devType;
    private String dataTime;
    private String inputPressure;
    private String outputPressure;
    private String instantFlow;
    private String totalFlow;
    private String voltage;
    private String checkWord;
    private String devCode;
    private String bdCard;

    public String getBdCard() {
        return bdCard;
    }

    public void setBdCard(String bdCard) {
        this.bdCard = bdCard;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public Integer getResolveId() {
        return resolveId;
    }

    public void setResolveId(Integer resolveId) {
        this.resolveId = resolveId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getDevSerial() {
        return devSerial;
    }

    public void setDevSerial(String devSerial) {
        this.devSerial = devSerial;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getInputPressure() {
        return inputPressure;
    }

    public void setInputPressure(String inputPressure) {
        this.inputPressure = inputPressure;
    }

    public String getOutputPressure() {
        return outputPressure;
    }

    public void setOutputPressure(String outputPressure) {
        this.outputPressure = outputPressure;
    }

    public String getInstantFlow() {
        return instantFlow;
    }

    public void setInstantFlow(String instantFlow) {
        this.instantFlow = instantFlow;
    }

    public String getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(String totalFlow) {
        this.totalFlow = totalFlow;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getCheckWord() {
        return checkWord;
    }

    public void setCheckWord(String checkWord) {
        this.checkWord = checkWord;
    }
}
