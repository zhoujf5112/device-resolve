package com.cnten.deviceresolve.utils;

public enum AdapterEnum {
    BDTXR("BDTXR","com.cnten.deviceresolve.adapter.impl.BdtxrResolveAdapter"),
    CCTXA("CCTXA","com.cnten.deviceresolve.adapter.impl.CctxaResolveAdapter");

    //防止字段值被修改，增加的字段也统一final表示常量
    private final String key;
    private final String value;

    private AdapterEnum(String key,String value){
        this.key = key;
        this.value = value;
    }

    //根据key获取枚举
    public static AdapterEnum getEnumByKey(String key){
        if(null == key){
            return null;
        }
        for(AdapterEnum temp:AdapterEnum.values()){
            if(temp.getKey().equals(key)){
                return temp;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
