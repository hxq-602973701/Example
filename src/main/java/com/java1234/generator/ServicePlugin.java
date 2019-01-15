package com.java1234.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

public class ServicePlugin extends BasePluginAdapter
{
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(final IntrospectedTable introspectedTable) {
        if (!"true".equalsIgnoreCase(this.getProperties().getProperty("isCreate", "false"))) {
            return null;
        }
        final List<GeneratedJavaFile> serviceList = new ArrayList<GeneratedJavaFile>();
        final String tableComment;
        final String entityName = tableComment = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        final String interfaceName = this.targetPackage + "." + entityName + "Service";
        final Interface interfaceClass = new Interface(interfaceName);
        interfaceClass.setVisibility(JavaVisibility.PUBLIC);
        interfaceClass.addImportedType(new FullyQualifiedJavaType(this.baseService));
        interfaceClass.addImportedType(new FullyQualifiedJavaType(this.entityPackage + "." + entityName));
        interfaceClass.addSuperInterface(super.buildFullyQualifiedJavaType(this.baseService, entityName));
        GeneratorUtil.fillJavaDocLine((JavaElement)interfaceClass, tableComment, ClassType.SERVICE);
        serviceList.add(super.buildGeneratedJavaFile((CompilationUnit)interfaceClass));
        final String implementsName = this.targetPackage + ".impl." + entityName + "ServiceImpl";
        final TopLevelClass implementsClass = new TopLevelClass(implementsName);
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
        GeneratorUtil.fillJavaDocLine((JavaElement)implementsClass, tableComment, ClassType.SERVICE);
        implementsClass.addAnnotation("@Service");
        final String filedName = this.toLowerFirstChar(entityName) + "DAO";
        final Field mapperField = new Field(filedName, new FullyQualifiedJavaType(this.daoPackage + "." + entityName + "DAO"));
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        mapperField.addAnnotation("@Resource");
        mapperField.addJavaDocLine("");
        mapperField.addJavaDocLine("/**");
        mapperField.addJavaDocLine(" * " + tableComment + "DAO");
        mapperField.addJavaDocLine(" */");
        implementsClass.addField(mapperField);
        final Method getDAOMethod = new Method("getDAO");
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
        serviceList.add(super.buildGeneratedJavaFile((CompilationUnit)implementsClass));
        return serviceList;
    }
}
