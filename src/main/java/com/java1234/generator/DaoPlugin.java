package com.java1234.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

public class DaoPlugin
        extends BasePluginAdapter
{
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable)
    {
        if (!"true".equalsIgnoreCase(getProperties().getProperty("isCreate", "false"))) {
            return null;
        }
        List<GeneratedJavaFile> daoList = new ArrayList();

        String entityName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        String tableComment = entityName;

        String interfaceName = this.targetPackage + "." + entityName + "DAO";
        Interface interfaceClass = new Interface(interfaceName);
        interfaceClass.setVisibility(JavaVisibility.PUBLIC);
        interfaceClass.addImportedType(new FullyQualifiedJavaType(this.baseDao));
        interfaceClass.addImportedType(new FullyQualifiedJavaType(this.entityPackage + "." + entityName));
        interfaceClass.addSuperInterface(super.buildFullyQualifiedJavaType(this.baseDao, entityName));
        interfaceClass.addJavaDocLine("/**");
        interfaceClass.addJavaDocLine(" * " + tableComment + "DAO");
        interfaceClass.addJavaDocLine(" *");
        interfaceClass.addJavaDocLine(" * @author lt " + getDateByYYYYMD());
        interfaceClass.addJavaDocLine(" * @version 1.0.0");
        interfaceClass.addJavaDocLine(" * @category 南阳理工学院");
        interfaceClass.addJavaDocLine(" */");
        daoList.add(super.buildGeneratedJavaFile(interfaceClass));

        String implementsName = this.targetPackage + ".impl." + entityName + "DAOImpl";
        TopLevelClass implementsClass = new TopLevelClass(implementsName);
        implementsClass.setVisibility(JavaVisibility.PUBLIC);
        implementsClass.addImportedType(this.baseDaoImpl);
        implementsClass.addImportedType(this.entityPackage + "." + entityName);
        implementsClass.addImportedType("javax.annotation.Resource");
        implementsClass.addImportedType("org.springframework.stereotype.Service");
        implementsClass.addImportedType(this.baseMapper);
        implementsClass.addImportedType(this.mapperPackage + "." + entityName + "Mapper");
        implementsClass.addImportedType(interfaceName);
        implementsClass.addSuperInterface(new FullyQualifiedJavaType(interfaceName));
        implementsClass.setSuperClass(super.buildFullyQualifiedJavaType(this.baseDaoImpl, entityName));
        implementsClass.addJavaDocLine("/**");
        implementsClass.addJavaDocLine(" * " + tableComment + "DAO");
        implementsClass.addJavaDocLine(" *");
        implementsClass.addJavaDocLine(" * @author lt " + getDateByYYYYMD());
        implementsClass.addJavaDocLine(" * @version 1.0.0");
        implementsClass.addJavaDocLine(" * @category 南阳理工学院");
        implementsClass.addJavaDocLine(" */");
        implementsClass.addAnnotation("@Service");

        String filedName = toLowerFirstChar(entityName) + "Mapper";
        Field mapperField = new Field(filedName, new FullyQualifiedJavaType(this.mapperPackage + "." + entityName + "Mapper"));
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        mapperField.addAnnotation("@Resource");
        mapperField.addJavaDocLine("");
        mapperField.addJavaDocLine("/**");
        mapperField.addJavaDocLine(" * " + tableComment + "Mapper");
        mapperField.addJavaDocLine(" */");
        implementsClass.addField(mapperField);

        Method getMapperMethod = new Method("getMapper");
        getMapperMethod.setVisibility(JavaVisibility.PROTECTED);
        getMapperMethod.setReturnType(super.buildFullyQualifiedJavaType(this.baseMapper, entityName));
        getMapperMethod.addBodyLine("return " + filedName + ";");
        getMapperMethod.addAnnotation("@Override");
        getMapperMethod.addJavaDocLine("/**");
        getMapperMethod.addJavaDocLine(" * Mapper初始化");
        getMapperMethod.addJavaDocLine(" *");
        getMapperMethod.addJavaDocLine(" * @return");
        getMapperMethod.addJavaDocLine(" */");
        implementsClass.addMethod(getMapperMethod);

        daoList.add(super.buildGeneratedJavaFile(implementsClass));

        return daoList;
    }
}
