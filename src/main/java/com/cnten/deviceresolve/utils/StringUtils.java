package com.cnten.deviceresolve.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    
    private static final Logger log = LoggerFactory.getLogger(StringUtils.class);

    //根据位置截取对应的数据
    public static String getSubIndex(String data,Integer index){
        return getSubIndex(data, index, index);
    }

    //根据开始和结束截取对应的数据
    public static String getSubIndex(String data,Integer start,Integer end){
        return data.substring(2 * (start - 1), 2 * end);
    }

    //根据位置截取对应的数据并转换成整形
    public static Integer subIndexToInt(String data,Integer start,Integer end){
        return Integer.parseInt(getSubIndex(data, start, end), 16);
    }

    //根据开始和结束截取对应的数据并转换成整形
    public static Integer subIndexToInt(String data,Integer index){
        return subIndexToInt(data, index, index);
    }

    //将字符串转换为id
    public static String getDeviceId(String data){
        /*int length = data.length();
        if (length % 2 != 0) {
            throw new RuntimeException(data + "数据长度不是偶数位");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i += 2) {
            sb.append(data.substring(i + 1, i + 2));
        }
        return sb.toString();*/
        return new String(toBytes(data));
    }

    //字符串转换时间
    public static Date stringToDate(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
        Date date = null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            log.info(data+"转换日期出错");
        }
        return date;
    }

    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

    public static void main(String[] args) {
        String s = Integer.toHexString(887);
        System.out.println(s);
    }


    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param str
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }
}
