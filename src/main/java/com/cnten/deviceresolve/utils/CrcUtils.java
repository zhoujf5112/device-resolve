package com.cnten.deviceresolve.utils;

public class CrcUtils {
    /**
     * 计算CRC16校验码
     *
     * @param bytes 字节数组
     * @return {@link String} 校验码
     * @since 1.0
     */
    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC);
    }


    /**
     * 和校验
     * @param bs
     * @return
     */
    public static byte SumCheck(byte[] bs) {
        int num = 0;
        //所有字节累加
        for (int i = 0; i < bs.length; i++) {
            num = (num + bs[i]) % 0xFFFF;
        }
        byte ret = (byte)(num & 0xff);//只要最后一个字节
        return ret;
    }

    /**
     * 异或校验
     * @param datas
     * @param checkData
     * @return
     */
    public static  boolean getXOR(byte[] datas,byte checkData){
        int result=0;
        for (int i = 0; i <datas.length; i++) {
            result=  result^byteToInt(datas[i]);
        }
        int data= byteToInt(checkData);
        if (result==data){
            return true;
        }
        return false;
    }

    private static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }
}
