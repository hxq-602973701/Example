//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.java1234.dal.utils;

import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;

import com.java1234.exception.message.BaseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public class DataPipe {
    public static final String CONTEXT_KEY_META = "meta";
    public static final String CONTEXT_KEY_RESPONSE = "response";
    public static final String CONTEXT_KEY_COOKIES = "cookies";
    private final Logger logger = LoggerFactory.getLogger(DataPipe.class);
    private Map<String, Object> model;

    private DataPipe(Map<String, Object> map) {
        this.model = map;
        Object meta = map.get("meta");
        Object response = map.get("response");
        if (meta != null) {
            this.model.put("meta", meta);
        }

        if (response != null) {
            this.model.put("response", response);
        }

    }

    public static DataPipe in(Model model) {
        Map<String, Object> map = model.asMap();
        return new DataPipe(map);
    }

    public static DataPipe in(ModelAndView model) {
        return new DataPipe(model.getModel());
    }

    public static DataPipe in(Map<String, Object> map) {
        return new DataPipe(map);
    }

    public DataPipe response(Object response) {
        this.model.put("response", response);
        return this;
    }

    public DataPipe meta(int code) {
        DataPipe.Meta meta = (DataPipe.Meta) this.model.get("meta");
        if (meta == null) {
            this.model.put("meta", new DataPipe.Meta(code));
        } else {
            meta.code = code;
        }

        return this;
    }

    public DataPipe meta(long timestamp) {
        DataPipe.Meta meta = (DataPipe.Meta) this.model.get("meta");
        if (meta == null) {
            this.model.put("meta", new DataPipe.Meta(timestamp));
        } else {
            meta.timestamp = timestamp;
        }

        return this;
    }

    public DataPipe meta(int code, BaseMessage messageCode, String message) {
        DataPipe.Meta meta = (DataPipe.Meta) this.model.get("meta");
        if (meta == null) {
            this.model.put("meta", new DataPipe.Meta(code, messageCode, message));
        } else {
            meta.code = code;
            if (messageCode != null) {
                meta.messageCode = messageCode.getMessageCode();
            }

            meta.message = message;
        }

        return this;
    }

    public DataPipe meta(int code, String message, long timestamp) {
        DataPipe.Meta meta = (DataPipe.Meta) this.model.get("meta");
        if (meta == null) {
            this.model.put("meta", new DataPipe.Meta(code, message));
        } else {
            meta.code = code;
            meta.message = message;
            meta.timestamp = timestamp;
        }

        return this;
    }

    public DataPipe addCookie(Cookie cookie) {
        this.initCookieList().add(cookie);
        return this;
    }

    public DataPipe addCookie(String cookieName, String cookieValue, int maxAge) {
        this.addCookie(cookieName, cookieValue, maxAge, false);
        return this;
    }

    public DataPipe addCookie(String cookieName, String cookieValue, int maxAge, boolean httpOnly) {
        try {
            Cookie cookie = new Cookie(cookieName, URLEncoder.encode(cookieValue, "utf-8"));
            cookie.setMaxAge(maxAge);
            cookie.setPath("/");
            //这个地方不设置也可以,但是设置了可以有效防止通过JavaScript脚本将无法读取到Cookie信息，这样能有效的防止XSS攻击，让网站应用更加安全。
            //JAVAEE6.0的容器 TOMCAT7.0可以直接设置下边的
//            cookie.setHttpOnly(httpOnly);
            this.initCookieList().add(cookie);
            return this;
        } catch (UnsupportedEncodingException var6) {
            this.logger.error("cookie encode error:", var6);
            throw new RuntimeException(var6);
        }
    }

    public DataPipe deleteCookie(Cookie cookie) {
        cookie.setMaxAge(0);
        cookie.setPath("/");
        this.initCookieList().add(cookie);
        return this;
    }

    public DataPipe deleteCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        this.initCookieList().add(cookie);
        return this;
    }

    public List<Cookie> cookies() {
        return (List) this.model.get("cookies");
    }

    private List<Cookie> initCookieList() {
        List<Cookie> cookies = (List) this.model.get("cookies");
        if (cookies == null) {
            cookies = Lists.newLinkedList();
            this.model.put("cookies", cookies);
        }

        return (List) cookies;
    }

    class Meta {
        private int code;
        private String messageCode;
        private String message;
        private Long timestamp;

        public Meta(int code) {
            this.code = code;
        }

        public Meta(long timestamp) {
            this.timestamp = timestamp;
        }

        public Meta(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public Meta(int code, BaseMessage messageCode, String message) {
            this.code = code;
            if (messageCode != null) {
                this.messageCode = messageCode.getMessageCode();
            }

            this.message = message;
        }

        public Meta(int code, String message, long timestamp) {
            this.code = code;
            this.message = message;
            this.timestamp = timestamp;
        }

        public int getCode() {
            return this.code == 0 ? 200 : this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessageCode() {
            return this.messageCode;
        }

        public void setMessageCode(String messageCode) {
            this.messageCode = messageCode;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Long getTimestamp() {
            return this.timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
