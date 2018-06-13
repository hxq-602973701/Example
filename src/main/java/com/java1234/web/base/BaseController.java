package com.java1234.web.base;

import com.java1234.dal.enums.DeviceTypeEnum;
import com.java1234.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Controller基类
 *
 * @author lt 2018-06-13
 * @version 1.0.0
 */
@Controller
public class BaseController {

    /**
     * 日志记录器
     */
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * yyyy-MM-dd HH:mm:ss 正则
     */
    private static final Pattern YYYY_MM_DD_HH_MM_SS = Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
    /**
     * yyyy-MM-dd HH:mm 正则
     */
    private static final Pattern YYYY_MM_DD_HH_MM = Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}");

    /**
     * 注册绑定转换器
     *
     * @param binder
     * @param request
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        // 绑定设备平台类型枚举转换器
        binder.registerCustomEditor(DeviceTypeEnum.class, new EnumPropertyEditor<>(DeviceTypeEnum.class));
    }

    /**
     * 跳转到指定URL
     *
     * @param url
     * @return
     */
    protected String redirectTo(String url) {
        return "redirect:" + url;
    }

    /**
     * 绑定到枚举类型<br>
     *
     * @author xinfeng.hu 2013-3-18下午3:49:24
     * @version 1.0.0
     */
    static class EnumPropertyEditor<E extends Enum<E>> extends PropertyEditorSupport {

        private Class<E> enumClass;

        public EnumPropertyEditor(Class<E> enumClass) {
            this.enumClass = enumClass;
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            try {
                setValue(Enum.valueOf(enumClass, text.toUpperCase()));
            } catch (Exception ex) {
                logger.error("InitBinder text[{}]to[{}] error:{}", text, enumClass.getName(), ex.getMessage());
                setValue(null);
            }
        }
    }

    /**
     * 绑定到枚举类型<br>
     *
     * @author xinfeng.hu 2013-3-18下午3:49:24
     * @version 1.0.0
     */
    static class DatePropertyEditor extends PropertyEditorSupport {

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            try {
                text = text.replaceAll("/", "-").replaceAll("T", " ");
                if (!"".equals(text)) {
                    setValue(DateUtil.toDate(text, formatAnalysis(text)));
                }
            } catch (Exception ex) {
                logger.warn("InitBinder text[{}]to[Date] error:{}", text, ex.getMessage());
                setValue(null);
            }
        }

        /**
         * 格式分析
         *
         * @param text
         * @return
         */
        private String formatAnalysis(String text) {

            Matcher match = YYYY_MM_DD_HH_MM_SS.matcher(text);
            if (match.find()) {
                return DateUtil.YYYY_MM_DD_HH_MM_SS;
            }

            match = YYYY_MM_DD_HH_MM.matcher(text);
            if (match.find()) {
                return DateUtil.YYYY_MM_DD_HH_MM;
            }

            return DateUtil.YYYY_MM_DD;
        }
    }

}
