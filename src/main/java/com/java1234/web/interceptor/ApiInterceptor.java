package com.java1234.web.interceptor;

import com.java1234.dal.entity.main.sys.open.OpenThirdIn;
import com.java1234.dal.utils.LoginContext;
import com.java1234.exception.DataErrorException;
import com.java1234.service.sys.open.OpenThirdInService;
import com.java1234.util.CommonMessage;
import com.java1234.util.ResponseCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 第三方调用接口
 *
 * @author lt 2018-06-14
 * @version 1.0.0
 */
public class ApiInterceptor extends BaseInterceptor {

    /**
     * 第三方信息Service
     */
    @Resource
    private OpenThirdInService openThirdInService;

    @Override
    public boolean preHandleBase(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod == false) {
            return true;
        }

        //应用KEY
        String appKey = request.getHeader("App-Key");
        if (StringUtils.isBlank(appKey)) {
            throw new DataErrorException(ResponseCode.SC_UNAUTHORIZED, CommonMessage.COMMON_MESSAGE, "请求头中不存在【 App-Key 】");
        }

        //应用Secrety
        String appSecrety = request.getHeader("App-Secrety");
        if (StringUtils.isBlank(appKey)) {
            throw new DataErrorException(ResponseCode.SC_UNAUTHORIZED, CommonMessage.COMMON_MESSAGE, "请求头中不存在【 App-Secrety 】");
        }

        final OpenThirdIn openThirdIn = new OpenThirdIn();
        openThirdIn.setAppKey(appKey);
        openThirdIn.setAppSecrety(appSecrety);
        openThirdIn.setDelFlag(false);
        final OpenThirdIn third = openThirdInService.selectOne(openThirdIn);

        if (third == null) {
            throw new DataErrorException(ResponseCode.SC_UNAUTHORIZED, CommonMessage.COMMON_MESSAGE, "App-Key或App-Secrety错误");
        }
        LoginContext.setOpenThirdIn(third);

        return true;
    }

}
