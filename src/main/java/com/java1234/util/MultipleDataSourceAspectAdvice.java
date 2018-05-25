package com.java1234.util;

import com.java1234.dal.annotation.DataSourceType;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Mapper切面
 *
 * @author lt on 2017/09/16.
 * @version 1.0.0
 */
public class MultipleDataSourceAspectAdvice {

    /**
     * 拦截切面，获取目标数据源(切面表达式请到biz-database.xml里面修改)
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    public Object doAround(final ProceedingJoinPoint jp) throws Throwable {

        DataSourceType ds = null;

        Class<?>[] interfaceList = jp.getTarget().getClass().getInterfaces();
        if (interfaceList != null && interfaceList.length > 0) {
            ds = interfaceList[0].getAnnotation(DataSourceType.class);
        }

        if (MultipleDataSource.getDataSourceKey() == null) {
            if (ds == null) {
                // 默认主库
                MultipleDataSource.setDataSourceKey(DataSourceEnum.MAIN);
            } else {
                MultipleDataSource.setDataSourceKey(ds.target());
            }
        }

        try {
            //调用被拦截方法
            return jp.proceed();
        } finally {
            MultipleDataSource.setDataSourceKey(null);
        }
    }
}
