package com.cnten.deviceresolve.adapter.impl;

import com.cnten.deviceresolve.adapter.ResolveAdapter;
import com.cnten.deviceresolve.platform.darchives.dao.HtDarchivesMapper;
import com.cnten.deviceresolve.platform.darchives.model.HtDarchives;
import com.cnten.deviceresolve.platform.message.dao.CjMessageMapper;
import com.cnten.deviceresolve.platform.message.model.CjMessage;
import com.cnten.deviceresolve.platform.resolve.dao.CjResolveMapper;
import com.cnten.deviceresolve.platform.resolve.model.CjResolve;
import com.cnten.deviceresolve.utils.CrcUtils;
import com.cnten.deviceresolve.utils.HexUtil;
import com.cnten.deviceresolve.utils.SpringContextUtils;
import com.cnten.deviceresolve.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CctxaResolveAdapter extends ResolveAdapter {

    private static final Logger log = LoggerFactory.getLogger(CctxaResolveAdapter.class);
    private CjMessageMapper cjMessageMapper;
    private CjResolveMapper cjResolveMapper;
    private HtDarchivesMapper htDarchivesMapper;

    @Override
    public Integer saveCjMessage(String data, Boolean checkLength) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        CjMessage cjMessage = new CjMessage();
        HtDarchives htDarchives = new HtDarchives();
        String[] split = data.split(",");
        cjMessage.setDataTime(currentDate);//数据日期
        cjMessage.setMessageTop(split[0]);//报文头
        cjMessage.setMessage(data);//原始报文
        cjMessage.setBdCard(split[1].substring(1,split[1].length()));//北斗卡号去除前面的零
        htDarchives.setcNumber(cjMessage.getBdCard());
        HtDarchives htDarchives1 = htDarchivesMapper.selectOne(htDarchives);
        if (htDarchives1 != null){
            cjMessage.setDevCode(htDarchives1.getdCode());
        }
        int i = split[4].indexOf("*");
        String messageData = null;
        if (i != -1) {
            messageData = split[4].substring(0,i);//数据部分
        } else {
            messageData = split[4].substring(0,split[4].length()-3);//数据部分
        }
        cjMessage.setData(messageData);
        if (checkLength) {//长度检验是否合格
            //异或校验
            String xorMessage = StringUtils.subString(data, "$", "*");
            String xorCode = data.substring(data.length() - 2, data.length());
            boolean xor = CrcUtils.getXOR(xorMessage.getBytes(), StringUtils.toBytes(xorCode)[0]);
            if (xor){
                if (messageData.length() > 70) {//$BDTXR报文头对应两种报文，两种模块发的
                    cjMessage.setMessageType(split[0].substring(1,split[0].length())+"_long");
                    messageData = messageData.substring(0,69);
                    //和校验
                    String messageDataUse = messageData.substring(2, messageData.length() - 2);
                    byte b = CrcUtils.SumCheck(StringUtils.toBytes(messageDataUse));
                    String checkCode = messageData.substring(messageData.length() - 2, messageData.length());
                    byte b1 = StringUtils.toBytes(checkCode)[0];
                    if (b == b1){
                        cjMessage.setIsCheckTrue("1");
                    } else {
                        log.info("CjMessageJiaoYHUnPass----数据和校验未通过："+data);
                        cjMessage.setIsCheckTrue("0");
                    }
                } else {
                    cjMessage.setIsCheckTrue("1");
                    cjMessage.setMessageType(split[0].substring(1,split[0].length())+"_short");
                }
            } else {
                log.info("CjMessageYHUnPass----数据异或校验未通过："+data);
                if (messageData.length() > 70) {//$BDTXR报文头对应两种报文，两种模块发的
                    cjMessage.setMessageType(split[0].substring(1,split[0].length())+"_long");
                } else {
                    cjMessage.setMessageType(split[0].substring(1,split[0].length())+"_short");
                }
                cjMessage.setIsCheckTrue("0");
            }
        } else {
            cjMessage.setIsCheckTrue("0");
        }
        cjMessageMapper.insert(cjMessage);
        return cjMessage.getMessageId();
    }

    @Override
    public void messageResolve(String data, Integer integer) {
        String[] split = data.split(",");
        //异或校验
        String xorMessage = StringUtils.subString(data, "$", "*");
        String xorCode = data.substring(data.length() - 2, data.length());
        boolean xor = CrcUtils.getXOR(xorMessage.getBytes(), StringUtils.toBytes(xorCode)[0]);
        if (xor){
            int i = split[4].indexOf("*");
            String messageData = null;
            if (i != -1) {
                messageData = split[4].substring(0,i);//数据部分
            } else {
                messageData = split[4].substring(0,split[4].length()-3);//数据部分
            }
            if (messageData.length() > 70) {//$BDTXR报文头对应两种报文，两种模块发的
                messageData = messageData.substring(0, 69);  //长报文截取有效位
                //和校验
                String messageDataUse = messageData.substring(2, messageData.length() - 2);
                byte b = CrcUtils.SumCheck(StringUtils.toBytes(messageDataUse));
                String checkCode = messageData.substring(messageData.length() - 2, messageData.length());
                byte b1 = StringUtils.toBytes(checkCode)[0];
                if (b == b1){//校验和合格
                    CjResolve cjResolve = resolveMessageData(messageData);
                    if (cjResolve != null){
                        HtDarchives htDarchives = new HtDarchives();
                        cjResolve.setBdCard(split[1].substring(1,split[1].length()));//北斗卡号去除前面的零
                        htDarchives.setcNumber(cjResolve.getBdCard());
                        HtDarchives htDarchives1 = htDarchivesMapper.selectOne(htDarchives);
                        if (htDarchives1 != null){
                            cjResolve.setDevCode(htDarchives1.getdCode());
                        }
                        cjResolve.setMessageId(integer);
                        cjResolveMapper.insert(cjResolve);
                    }
                } else {
                    log.info("messageResolveJiaoYHUnPass----数据和校验未通过："+data);
                }
            } else {
                CjResolve cjResolve = resolveMessageData(messageData);
                if (cjResolve != null){
                    HtDarchives htDarchives = new HtDarchives();
                    cjResolve.setBdCard(split[1].substring(1,split[1].length()));//北斗卡号去除前面的零
                    htDarchives.setcNumber(cjResolve.getBdCard());
                    HtDarchives htDarchives1 = htDarchivesMapper.selectOne(htDarchives);
                    if (htDarchives1 != null){
                        cjResolve.setDevCode(htDarchives1.getdCode());
                    }
                    cjResolve.setMessageId(integer);
                    cjResolveMapper.insert(cjResolve);
                }
            }
        } else {
            log.info("messageResolveYiHuoUnpass----数据异或校验未通过："+data);
        }
    }

    /**
     * 数据部分解析
     * @param messageData
     */
    private CjResolve resolveMessageData(String messageData){
        try {
            CjResolve cjResolve = new CjResolve();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String devSerial = messageData.substring(2, 9);//设备序号
            cjResolve.setDevSerial(devSerial);
            String devTypeStr = messageData.substring(9, 11);
            String devType = String.valueOf(Integer.parseInt(devTypeStr, 16));//设备类型
            cjResolve.setDevType(devType);
            String dataTimeStr = "20"+messageData.substring(11, 23);//日期时间
            Date date1 = new SimpleDateFormat("yyyyMMddHHmmss").parse(dataTimeStr);
            String dataTime = sf.format(date1);
            cjResolve.setDataTime(dataTime);
            String inputPressureStr = messageData.substring(23, 31);//进口压力
            Float hex2Float = HexUtil.Hex2Float(inputPressureStr);
            String inputPressure = String.valueOf(hex2Float);
            cjResolve.setInputPressure(inputPressure);
            String outputPressureStr = messageData.substring(31, 39);//出口压力
            Float outputPressureF = HexUtil.Hex2Float(outputPressureStr);
            String outputPressure = String.valueOf(outputPressureF);
            cjResolve.setOutputPressure(outputPressure);
            String instantFlowStr = messageData.substring(39, 47);//瞬时流量
            Float instantFlowF = HexUtil.Hex2Float(instantFlowStr);
            String instantFlow = String.valueOf(instantFlowF);
            cjResolve.setInstantFlow(instantFlow);
            String totalFlowStr = messageData.substring(47, 63);//累计流量
            double totalFlowD = HexUtil.Hex2Double(totalFlowStr);
            String totalFlow = String.valueOf(totalFlowD);
            cjResolve.setTotalFlow(totalFlow);
            String voltageStr = messageData.substring(63, 67);//电压
            String voltage = String.valueOf(Integer.parseInt(voltageStr,16)/10);
            cjResolve.setVoltage(voltage);
            String checkWord = messageData.substring(67, 69);//检验和
            cjResolve.setCheckWord(checkWord);
            return cjResolve;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

        }

    }


    @Override
    public boolean isCheckLength(String data) {
        if (data.length() == 129 || data.length() == 91){//129为$CCTXA长报文长度，91为$CCTXA短报文长度
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        cjMessageMapper = SpringContextUtils.getBean(CjMessageMapper.class);
        cjResolveMapper = SpringContextUtils.getBean(CjResolveMapper.class);
        htDarchivesMapper = SpringContextUtils.getBean(HtDarchivesMapper.class);
    }
}
