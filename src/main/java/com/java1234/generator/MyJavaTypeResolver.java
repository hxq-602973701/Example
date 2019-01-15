package com.java1234.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.math.BigDecimal;

public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    @Override
    public FullyQualifiedJavaType calculateJavaType(final IntrospectedColumn introspectedColumn) {
        final JavaTypeResolverDefaultImpl.JdbcTypeInformation jdbcTypeInformation = this.typeMap.get(introspectedColumn.getJdbcType());
        if (introspectedColumn.getJdbcType() == -6 && introspectedColumn.getActualColumnName().equalsIgnoreCase("DEL_FLAG")) {
            return new FullyQualifiedJavaType(Boolean.class.getName());
        }
        FullyQualifiedJavaType answer = null;
        if (jdbcTypeInformation == null) {
            switch (introspectedColumn.getJdbcType()) {
                case 2:
                case 3: {
                    if (introspectedColumn.getScale() > 0 || introspectedColumn.getLength() > 24 || this.forceBigDecimals) {
                        answer = new FullyQualifiedJavaType(BigDecimal.class.getName());
                        break;
                    }
                    if (introspectedColumn.getLength() > 10) {
                        answer = new FullyQualifiedJavaType(Long.class.getName());
                        break;
                    }
                    if (introspectedColumn.getLength() > 4) {
                        answer = new FullyQualifiedJavaType(Integer.class.getName());
                        break;
                    }
                    if (introspectedColumn.getLength() == 1) {
                        answer = new FullyQualifiedJavaType(Boolean.class.getName());
                        break;
                    }
                    answer = new FullyQualifiedJavaType(Short.class.getName());
                    break;
                }
                default: {
                    answer = null;
                    break;
                }
            }
        } else {
            answer = jdbcTypeInformation.getFullyQualifiedJavaType();
        }
        return answer;
    }
}
