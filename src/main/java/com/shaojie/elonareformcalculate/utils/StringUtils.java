package com.shaojie.elonareformcalculate.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author liqi
 * 
 */
public final class StringUtils extends org.apache.commons.lang.StringUtils {

    private StringUtils() {
    }


    /**
     * 判断集合是否为null，或者空集合
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断集合是否为null，或者空集合
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断字符串是否为纯大写字母
     *
     * @param str 要匹配的字符串
     * @return
     */
    public static boolean isUpperCase(String str) {
        return match("^[A-Z]+$", str);
    }

    /**
     * 正则表达式匹配
     *
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 获取UUID
     *
     * @return str
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取本机的ip地址
     *
     * @return str
     * @throws UnknownHostException
     */
    public static String getLocalIP() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        byte[] ipAddr = addr.getAddress();
        StringBuilder ipAddrStr = new StringBuilder();
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr.append(".");
            }
            ipAddrStr.append(ipAddr[i] & 0xFF);
        }
        return ipAddrStr.toString();
    }


}