package com.java1234.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

public class ServicePlugin
        extends BasePluginAdapter
{
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable)
    {
        if (!"true".equalsIgnoreCase(getProperties().getProperty("isCreate", "false"))) {
            return null;
        }
        List<GeneratedJavaFile> serviceList = new ArrayList();

        String entityName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        String tableComment = entityName;

        String interfaceName = this.targetPackage + "." + entityName + "Service";
        Interface interfaceClass = new Interface(interfaceName);
        interfaceClass.setVisibility(JavaVisibility.PUBLIC);
        interfaceClass.addImportedType(new FullyQualifiedJavaType(this.baseService));
        interfaceClass.addImportedType(new FullyQualifiedJavaType(this.entityPackage + "." + entityName));
        interfaceClass.addSuperInterface(super.buildFullyQualifiedJavaType(this.baseService, entityName));
        interfaceClass.addJavaDocLine("/**");
        interfaceClass.addJavaDocLine(" * " + tableComment + "Service");
        interfaceClass.addJavaDocLine(" *");
        interfaceClass.addJavaDocLine(" * @author lt " + getDateByYYYYMD());
        interfaceClass.addJavaDocLine(" * @version 1.0.0");
        interfaceClass.addJavaDocLine(" * @category 南阳理工学院");
        interfaceClass.addJavaDocLine(" */");
        serviceList.add(super.buildGeneratedJavaFile(interfaceClass));

        String implementsName = this.targetPackage + ".impl." + entityName + "ServiceImpl";
        TopLevelClass implementsClass = new TopLevelClass(implementsName);
        implementsClass.setVisibility(JavaVisibility.PUBLIC);
        implementsClass.addImportedType(this.baseServiceImpl);
        implementsClass.addImportedType(this.entityPackage + "." + entityName);
        implementsClass.addImportedType(this.daoPackage + "." + entityName + "DAO");
        implementsClass.addImportedType(this.baseDao);
        implementsClass.addImportedType("javax.annotation.Resource");
        implementsClass.addImportedType("org.springframework.stereotype.Service");
        implementsClass.addImportedType(interfaceName);
        implementsClass.addSuperInterface(new FullyQualifiedJavaType(interfaceName));
        implementsClass.setSuperClass(super.buildFullyQualifiedJavaType(this.baseServiceImpl, entityName));
        implementsClass.addJavaDocLine("/**");
        implementsClass.addJavaDocLine(" * " + tableComment + "Service");
        implementsClass.addJavaDocLine(" *");
        implementsClass.addJavaDocLine(" * @author lt " + getDateByYYYYMD());
        implementsClass.addJavaDocLine(" * @version 1.0.0");
        implementsClass.addJavaDocLine(" * @category 南阳理工学院");
        implementsClass.addJavaDocLine(" */");
        implementsClass.addAnnotation("@Service");

        String filedName = toLowerFirstChar(entityName) + "DAO";
        Field mapperField = new Field(filedName, new FullyQualifiedJavaType(this.daoPackage + "." + entityName + "DAO"));
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        mapperField.addAnnotation("@Resource");
        mapperField.addJavaDocLine("");
        mapperField.addJavaDocLine("/**");
        mapperField.addJavaDocLine(" * " + tableComment + "DAO");
        mapperField.addJavaDocLine(" */");
        implementsClass.addField(mapperField);

        Method getDAOMethod = new Method("getDAO");
        getDAOMethod.setVisibility(JavaVisibility.PROTECTED);
        getDAOMethod.setReturnType(super.buildFullyQualifiedJavaType(this.baseDao, entityName));
        getDAOMethod.addBodyLine("return " + filedName + ";");
        getDAOMethod.addAnnotation("@Override");
        getDAOMethod.addJavaDocLine("/**");
        getDAOMethod.addJavaDocLine(" * Mapper初始化");
        getDAOMethod.addJavaDocLine(" *");
        getDAOMethod.addJavaDocLine(" * @return");
        getDAOMethod.addJavaDocLine(" */");
        implementsClass.addMethod(getDAOMethod);

        serviceList.add(super.buildGeneratedJavaFile(implementsClass));

        return serviceList;
    }
}
