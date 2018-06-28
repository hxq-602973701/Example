//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.java1234.dal.security.login;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public MD5() {
    }

    public static final String md5(String s) {
        byte[] h = DigestUtils.md5(s);
        StringBuilder resultSb = new StringBuilder();

        for (int i = 0; i < h.length; ++i) {
            int t = h[i] & 255;
            resultSb.append(hexDigits[t >> 4 & 15]);
            resultSb.append(hexDigits[t & 15]);
        }

        return resultSb.toString();
    }

    public static final String md5(byte[] s) {
        byte[] h = DigestUtils.md5(s);
        StringBuilder resultSb = new StringBuilder();

        for (int i = 0; i < h.length; ++i) {
            int t = h[i] & 255;
            resultSb.append(hexDigits[t >> 4 & 15]);
            resultSb.append(hexDigits[t & 15]);
        }

        return resultSb.toString();
    }

    public static void main(String[] args) {
        String data = "171cntaobao风格cntaobao经验YTkxUik6P0tFaEBsIVU/ZGUzQVdhQT";
        System.out.println(md5(data.getBytes()));
        System.out.println(md5("123456"));
    }
}
