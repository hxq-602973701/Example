package com.java1234.dal.utils;


import com.java1234.dal.dao.common.CommonDAO;
import com.java1234.dal.entity.base.BaseEntity;
import com.java1234.util.SpringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * 主键生成策略
 *
 * @author lt on 2018/05/29.
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class MySequenceGenerator implements Interceptor {

    /**
     * 拦截插入的方法
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        final Object[] objects = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) objects[0];
        final Object entity = objects[1];

        final SqlCommandType commandType = ms.getSqlCommandType();

        //只对插入的操作进行拦截
        if (commandType == SqlCommandType.INSERT && entity instanceof BaseEntity) {
            //填充序列值
            SpringUtils.getBean(CommonDAO.class).fillSequence((BaseEntity) entity);
        }

        return invocation.proceed();
    }

    /**
     * 是否包装成插件代理对象
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * 属性配置
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        // Nothing
    }

}
