package com.cnten.deviceresolve.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class ResolveAdapter {
    private static final Logger log = LoggerFactory.getLogger(ResolveAdapter.class);

    /**
     * 采集原始报文并入库
     * @param data
     */
    private Integer excuteCjMessage(String data){
        boolean checkLength = isCheckLength(data);
        return  saveCjMessage(data,checkLength);
    }

    /**
     * 解析原始报文并入库，必须满足长度校验
     * @param data
     */
    private void excuteResolve(String data,Integer integer){
       if (isCheckLength(data)){
           messageResolve(data,integer);
       } else {
           log.info("长度检验未通过："+data);
       }
    }

    public void excute(String data){
        init();
        Integer integer = excuteCjMessage(data);
        excuteResolve(data,integer);
    }
    public abstract Integer saveCjMessage(String data,Boolean checkLength);
    public abstract void messageResolve(String data,Integer integer);

    /**
     * 检验数据长度是否合格
     * @param data
     * @return
     */
    public abstract boolean isCheckLength(String data);

    public abstract void init();
}
