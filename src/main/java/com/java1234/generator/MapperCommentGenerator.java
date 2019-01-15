package com.java1234.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

public class MapperCommentGenerator implements CommentGenerator
{
    @Override
    public void addJavaFileComment(final CompilationUnit compilationUnit) {
    }
    @Override
    public void addComment(final XmlElement xmlElement) {
    }
    @Override
    public void addRootComment(final XmlElement rootElement) {
        rootElement.addElement((Element)new TextElement("<!-- 命名空间必须与接口类保持一致 -->"));
    }
    @Override
    public void addConfigurationProperties(final Properties properties) {
    }
    
    protected void addJavadocTag(final JavaElement javaElement, final boolean markAsDoNotDelete) {
        final StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append("@mbg.generated");
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        javaElement.addJavaDocLine(sb.toString());
    }
    @Override
    public void addClassComment(final InnerClass innerClass, final IntrospectedTable introspectedTable) {
        this.addClassComment(innerClass, introspectedTable, true);
    }
    @Override
    public void addClassComment(final InnerClass innerClass, final IntrospectedTable introspectedTable, final boolean markAsDoNotDelete) {
        GeneratorUtil.fillJavaDocLine((JavaElement)innerClass, introspectedTable.getTableConfiguration().getTableName(), ClassType.MAPPER);
    }
    @Override
    public void addEnumComment(final InnerEnum innerEnum, final IntrospectedTable introspectedTable) {
    }
    @Override
    public void addFieldComment(final Field field, final IntrospectedTable introspectedTable, final IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            final StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }
        if (field.isTransient()) {
            field.addAnnotation("@Transient");
        }
        for (final IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
            if (introspectedColumn == column) {
                field.addAnnotation("@Id");
                break;
            }
        }
        String column2 = introspectedColumn.getActualColumnName();
        if (StringUtility.stringContainsSpace(column2) || introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
            column2 = introspectedColumn.getContext().getBeginningDelimiter() + column2 + introspectedColumn.getContext().getEndingDelimiter();
        }
        if (!column2.equals(introspectedColumn.getJavaProperty())) {
            field.addAnnotation("@Column(name = \"" + column2 + "\")");
        }
        if (introspectedColumn.isIdentity()) {
            if ("JDBC".equals(introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement())) {
                field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
            }
            else {
                field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
            }
        }
        else if (introspectedColumn.isSequenceColumn()) {
            field.addAnnotation("@SequenceGenerator(name=\"\",sequenceName=\"" + introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement() + "\")");
        }
    }
    @Override
    public void addFieldComment(final Field field, final IntrospectedTable introspectedTable) {
    }
    @Override
    public void addGeneralMethodComment(final Method method, final IntrospectedTable introspectedTable) {
    }
    @Override
    public void addGetterComment(final Method method, final IntrospectedTable introspectedTable, final IntrospectedColumn introspectedColumn) {
        final StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" * \u83b7\u53d6");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString());
            method.addJavaDocLine(" *");
        }
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" - ");
            sb.append(introspectedColumn.getRemarks());
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
    }
    @Override
    public void addSetterComment(final Method method, final IntrospectedTable introspectedTable, final IntrospectedColumn introspectedColumn) {
        final StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" * 设置");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString());
            method.addJavaDocLine(" *");
        }
        final Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" ");
            sb.append(introspectedColumn.getRemarks());
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
    }
    
    public void addModelClassComment(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
    }
}
