package com.cn.peasantry.utils.utils;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 工具类
 * @version 1.0
 */
public class AceUtils {

    /**
     * 获取当月天数
     * @return
     */
    public static int getDaysInMonth() {
        // 获取当前月份
        YearMonth currentYearMonth = YearMonth.now();

        // 获取当月天数并输出
        int daysInMonth = currentYearMonth.lengthOfMonth();

        return daysInMonth;
    }


    /**
     * 根据 年月日获取当前号获取月份
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static int getDateOfDay(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }


    public static String uuid32(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }



    public static String getGodate() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMdd");
        Date date = new Date();
        return f.format(date);
    }


}
