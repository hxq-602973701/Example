package com.java1234.web.controller.open;

import com.java1234.dal.entity.main.sys.open.OpenThirdIn;
import com.java1234.dal.utils.LoginContext;
import com.java1234.web.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2018/6/14.
 */
@Controller
public class OpenController extends BaseController {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(OpenController.class);

    /**
     * 第三方信息采集接口
     *
     * @param intelligenceJson
     */
    @RequestMapping(value = {"/api/intelligence/third/intelligence1", "/api/intelligence/third/intelligence-save"}, method = RequestMethod.POST)
    public void thirdPartyIntelligenceApi(String intelligenceJson) {

        //验证秘钥
        OpenThirdIn third = LoginContext.getOpenThirdIn();
        String deptCode = third.getDeptCode();
        String deptName = third.getDeptName();
        logger.debug("获取到了deptCode为【{}】,单位名称为【{}】,推送上来的情报json数据为:{}", deptCode, deptName, intelligenceJson);

        //保存数据到本地库...
        //操作
    }
}
