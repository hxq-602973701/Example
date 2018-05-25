package com.java1234.service.task.impl;


import com.google.common.collect.Maps;
import com.java1234.dal.dao.common.CommonDAO;
import com.java1234.service.task.BaseTaskService;
import com.java1234.util.DataSourceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时获取内网天气数据
 */
public class CommonTestTaskServiceImpl extends BaseTaskService {


    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(CommonTestTaskServiceImpl.class);

    /**
     * 通用dao方法
     */
    @Resource
    private CommonDAO commonDAO;

    /**
     * @throws Throwable
     */
    @Override
    protected void execute() throws Throwable {

        logger.info("执行common多数据源...");
        Map<String,Object> conditionMap = Maps.newHashMap();
        conditionMap.put("post_id",23);
        String tableName = "post";
        List<HashMap> mapList = commonDAO.selectByCondition(DataSourceEnum.SD,tableName,conditionMap,1);

        mapList.parallelStream().forEach(map->{
           logger.info(map.get("DEL_FLAG").toString());
        });

        logger.info("common多数据源配置成功...");

    }


    @Override
    protected Class<?> getSubClass() {
        return CommonTestTaskServiceImpl.class;
    }
}
