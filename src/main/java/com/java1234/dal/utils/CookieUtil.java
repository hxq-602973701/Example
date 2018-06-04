//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.java1234.dal.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static final String TOKEN = "token";
    public static final String SESSION_ID = "_GVkX18tPhiBX3_";
    public static final int SESSION_TIME_OUT = 14400;

    public CookieUtil() {
    }

    public static Cookie getSessionCookie(String sessionId) {
        Cookie cookie = new Cookie("_GVkX18tPhiBX3_", sessionId);
        cookie.setMaxAge(14400);
        cookie.setPath("/");
//        cookie.setHttpOnly(true);
        return cookie;
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookieName != null && cookieName.length() != 0) {
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Cookie cookie = var3[var5];
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        return cookie != null ? cookie.getValue() : null;
    }
}
