package com.java1234.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public abstract class BasePluginAdapter
        extends PluginAdapter {
    protected String targetProject = null;
    protected String targetPackage = null;
    protected String baseMapper = null;
    protected String baseDao = null;
    protected String baseDaoImpl = null;
    protected String baseService = null;
    protected String baseServiceImpl = null;
    protected String mapperPackage = null;
    protected String entityPackage = null;
    protected String daoPackage = null;
    protected String servicePackage = null;

    public boolean validate(List<String> warnings) {
        this.targetProject = this.properties.getProperty("targetProject");
        if (!StringUtility.stringHasValue(this.targetProject)) {
            warnings.add("DaoPlugin缺少targetProject属性!");
            return false;
        }
        this.targetPackage = this.properties.getProperty("targetPackage");
        if (!StringUtility.stringHasValue(this.targetPackage)) {
            warnings.add("DaoPlugin缺少targetPackage属性!");
            return false;
        }
        this.targetPackage = this.targetPackage.replaceAll(".main", "");

        Properties properties = this.context.getProperties();
        this.baseMapper = properties.getProperty("baseMapper");
        if (!StringUtility.stringHasValue(this.baseMapper)) {
            warnings.add("DaoPlugin缺少baseMapper属性!");
            return false;
        }
        this.baseDao = properties.getProperty("baseDao");
        if (!StringUtility.stringHasValue(this.baseDao)) {
            warnings.add("DaoPlugin缺少baseDao属性!");
            return false;
        }
        this.baseDaoImpl = properties.getProperty("baseDaoImpl");
        if (!StringUtility.stringHasValue(this.baseDaoImpl)) {
            warnings.add("DaoPlugin缺少baseDaoImpl属性!");
            return false;
        }
        this.baseService = properties.getProperty("baseService");
        if (!StringUtility.stringHasValue(this.baseService)) {
            warnings.add("ServicePlugin缺少baseService属性!");
            return false;
        }
        this.baseServiceImpl = properties.getProperty("baseServiceImpl");
        if (!StringUtility.stringHasValue(this.baseServiceImpl)) {
            warnings.add("ServicePlugin缺少baseServiceImpl属性!");
            return false;
        }
        this.mapperPackage = properties.getProperty("mapperPackage");
        if (!StringUtility.stringHasValue(this.mapperPackage)) {
            warnings.add("DaoPlugin缺少mapperPackage属性!");
            return false;
        }
        this.entityPackage = properties.getProperty("entityPackage");
        if (!StringUtility.stringHasValue(this.entityPackage)) {
            warnings.add("DaoPlugin缺少entityPackage属性!");
            return false;
        }
        this.daoPackage = properties.getProperty("daoPackage");
        if (!StringUtility.stringHasValue(this.daoPackage)) {
            warnings.add("ServicePlugin缺少daoPackage属性!");
            return false;
        }
        this.daoPackage = this.daoPackage.replaceAll(".main", "");
        this.servicePackage = properties.getProperty("servicePackage");
        if (!StringUtility.stringHasValue(this.servicePackage)) {
            warnings.add("ServicePlugin缺少servicePackage属性!");
            return false;
        }
        this.servicePackage = this.servicePackage.replaceAll(".main", "");

        return true;
    }

    public GeneratedJavaFile buildGeneratedJavaFile(CompilationUnit compilationUnit) {
        return new GeneratedJavaFile(compilationUnit, this.targetProject, "utf-8", getContext().getJavaFormatter());
    }

    public FullyQualifiedJavaType buildFullyQualifiedJavaType(String className, String typeArgument) {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(className);
        if ((typeArgument != null) && (typeArgument.length() != 0)) {
            type.addTypeArgument(new FullyQualifiedJavaType(typeArgument));
        }
        return type;
    }

    public String getDateByYYYYMD() {
        return new SimpleDateFormat("yyyy-M-d").format(new Date());
    }

    public String toLowerFirstChar(String value) {
        return value.substring(0, 1).toLowerCase() + value.substring(1, value.length());
    }

    public String toUpperFirstChar(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1, value.length());
    }
}
