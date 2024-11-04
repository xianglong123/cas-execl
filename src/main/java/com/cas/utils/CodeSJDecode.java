package com.cas.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * created by yangfan on 2023-10-09
 *
 * @author yangfan
 */
public class CodeSJDecode {
    private static final String baseDigits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+/";
    private static final int BASE = baseDigits.length();
    private static final char[] digitsChar = baseDigits.toCharArray();
    private static final int FAST_SIZE = 'z';
    private static final int[] digitsIndex = new int[FAST_SIZE + 1];
    static {
        for (int i = 0; i < FAST_SIZE; i++) {
            digitsIndex[i] = -1;
        }
        //
        for (int i = 0; i < BASE; i++) {
            digitsIndex[digitsChar[i]] = i;
        }
    }

    /**
     * 把64进制的字符串，解码成10进制的长整型
     *
     * @param s 64进制的字符串
     * @return 10进制的长整型
     */
    public static long decode(final String s) {
        long result = 0L;
        long multiplier = 1;
        for (int pos = s.length() - 1; pos >= 0; pos--) {
            int index = getIndex(s, pos);
            result += index * multiplier;
            multiplier *= BASE;
        }
        return result;
    }
    /**
     * 获取指定位置字符，在64进制中对应的数据
     *
     * @param s   字符串
     * @param pos 位置
     * @return 64进制中对应的数据
     */
    private static int getIndex(final String s, final int pos) {
        final char c = s.charAt(pos);
        if (c > FAST_SIZE) {
            throw new IllegalArgumentException("Unknow character for Base64:" + s);
        }

        int index = digitsIndex[c];
        if (index == -1) {
            throw new IllegalArgumentException("Unknow character for Base64:" + s);
        }
        return index;
    }

    public static void main(String[] args) {
        String sj = "1B7BE6";
        //decode(sj);
        long decode = decode(sj);

        System.out.println(decode);

        // 将时间戳转换为 LocalDateTime 对象
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(decode), ZoneId.systemDefault());
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }


}
