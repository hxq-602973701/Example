package com.java1234.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 设置返回数据结构
 *
 * @author xinfeng.hu 2013-3-13下午9:47:58
 * @version 1.0.0
 * @category 杭州尤拉夫科技有限公司
 */
public class DataPipe {

    /**
     * 日志记录器
     */
    private final Logger logger = LoggerFactory.getLogger(DataPipe.class);

    /**
     * 返回结构meta key
     */
    public static final String CONTEXT_KEY_META = "meta";

    /**
     * 返回结构response key
     */
    public static final String CONTEXT_KEY_RESPONSE = "response";

    /**
     * 直接返回结构(不会被嵌入在meta和response中) key
     */
    public static final String CONTEXT_KEY_BODY = "__response__body__";

    /**
     * 返回结构cookies key
     */
    public static final String CONTEXT_KEY_COOKIES = "cookies";

    private Map<String, Object> model;

    /**
     * 清除model里多余的数据只保留 meta 和 response
     *
     * @param map
     */
    private DataPipe(Map<String, Object> map) {
        this.model = map;
        Object meta = map.get(CONTEXT_KEY_META);
        Object response = map.get(CONTEXT_KEY_RESPONSE);
        if (meta != null) {
            model.put(CONTEXT_KEY_META, meta);
        }
        if (response != null) {
            model.put(CONTEXT_KEY_RESPONSE, response);
        }
    }

    /**
     * @param model
     * @return
     */
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

    /**
     * 设置返回结构中的 Response结构
     * 将传递进来的数据封装到Model中
     *
     * @param response
     * @return
     */
    public DataPipe response(Object response) {
        model.put(CONTEXT_KEY_RESPONSE, response);
        return this;
    }

    /**
     * 直接返回内容不会被包裹在meta 和 response 中
     *
     * @param responseBody
     * @return
     */
    public DataPipe responseBody(Object responseBody) {
        if (responseBody != null) {
            model.put(CONTEXT_KEY_BODY, responseBody);
        }
        return this;
    }

    /**
     * 设置返回结构中的 Meta结构
     *
     * @param code
     * @return
     */
    public DataPipe meta(int code) {
        Meta meta = (Meta) model.get(CONTEXT_KEY_META);
        if (meta == null) {
            model.put(CONTEXT_KEY_META, new Meta(code));
        } else {
            meta.code = code;
        }
        return this;
    }

    /**
     * 设置返回结构中的 Meta结构
     *
     * @param timestamp
     * @return
     */
    public DataPipe meta(long timestamp) {
        Meta meta = (Meta) model.get(CONTEXT_KEY_META);
        if (meta == null) {
            model.put(CONTEXT_KEY_META, new Meta(timestamp));
        } else {
            meta.timestamp = timestamp;
        }
        return this;
    }

    /**
     * 设置返回结构中的 Meta结构
     *
     * @param code
     * @param messageCode
     * @param message
     * @return
     */
    public DataPipe meta(int code, BaseMessage messageCode, String message) {
        Meta meta = (Meta) model.get(CONTEXT_KEY_META);
        if (meta == null) {
            model.put(CONTEXT_KEY_META, new Meta(code, messageCode, message));
        } else {
            meta.code = code;
            if (messageCode != null) {
                meta.messageCode = messageCode.getMessageCode();
            }
            meta.message = message;
        }
        return this;
    }

    /**
     * 设置返回结构中的 Meta结构
     *
     * @param code      返回码
     * @param message   返回信息
     * @param timestamp 时间戳
     * @return
     */
    public DataPipe meta(int code, String message, long timestamp) {
        Meta meta = (Meta) model.get(CONTEXT_KEY_META);
        if (meta == null) {
            model.put(CONTEXT_KEY_META, new Meta(code, message));
        } else {
            meta.code = code;
            meta.message = message;
            meta.timestamp = timestamp;
        }
        return this;
    }

    /**
     * 添加Cookie
     *
     * @param cookie
     * @return
     */
    public DataPipe addCookie(Cookie cookie) {
        initCookieList().add(cookie);
        return this;
    }

    /**
     * 添加Cookie
     *
     * @param cookieName
     * @param cookieValue
     * @param maxAge
     * @return
     */
  /*  public DataPipe addCookie(String cookieName, String cookieValue, int maxAge) {
        addCookie(cookieName, cookieValue, maxAge, false);
        return this;
    }*/

    /**
     * 添加Cookie
     *
     * @param cookieName
     * @param cookieValue
     * @param maxAge
     * @param httpOnly
     * @return
     */
   /* public DataPipe addCookie(String cookieName, String cookieValue, int maxAge, boolean httpOnly) {

        try {
            Cookie cookie = new Cookie(cookieName, URLEncoder.encode(cookieValue, "utf-8"));
            cookie.setMaxAge(maxAge);
            cookie.setPath("/");
            cookie.setHttpOnly(httpOnly);
            initCookieList().add(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error("cookie encode error:", e);
            throw new RuntimeException(e);
        }
        return this;
    }*/

    /**
     * 删除一个Cookie
     *
     * @param cookie
     * @return
     */
    public DataPipe deleteCookie(Cookie cookie) {

        cookie.setMaxAge(0);
        cookie.setPath("/");

        initCookieList().add(cookie);

        return this;
    }

    /**
     * 删除一个Cookie
     *
     * @param cookieName
     * @return
     */
    public DataPipe deleteCookie(String cookieName) {

        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        initCookieList().add(cookie);

        return this;
    }

    /**
     * 获取Cookie列表
     *
     * @return
     */
    public List<Cookie> cookies() {
        return (List<Cookie>) model.get(CONTEXT_KEY_COOKIES);
    }

    /**
     * 初始化Cookie列表
     *
     * @return
     */
    private List<Cookie> initCookieList() {
        List<Cookie> cookies = (List<Cookie>) model.get(CONTEXT_KEY_COOKIES);
        if (cookies == null) {
            cookies = Lists.newLinkedList();
            model.put(CONTEXT_KEY_COOKIES, cookies);
        }
        return cookies;
    }

    /**
     * 元数据
     *
     * @author xinfeng.hu 2013-3-13下午9:50:58
     * @version 1.0.0
     * @category 杭州尤拉夫科技有限公司
     */
    class Meta {

        /**
         * 请求处理结果状态码
         */
        private int code;

        /**
         * 如果code!=200的情况下，返回具体的错误信息Code
         */
        private String messageCode;

        /**
         * 如果code!=200的情况下，返回具体的错误信息
         */
        private String message;

        /**
         * 时间戳
         */
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
            return (code == 0 ? ResponseCode.SC_OK : code);
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessageCode() {
            return messageCode;
        }

        public void setMessageCode(String messageCode) {
            this.messageCode = messageCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }

}
