package com.java1234.generator;

import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.util.*;
import org.mybatis.generator.api.*;
import java.util.function.*;
import java.util.*;
import javax.persistence.*;
import java.util.stream.*;
import org.mybatis.generator.api.dom.java.*;
import com.google.common.collect.*;
import org.mybatis.generator.api.dom.xml.*;
import tk.mybatis.mapper.generator.MapperCommentGenerator;

public class MapperPlugin extends PluginAdapter
{
    private Set<String> mappers;
    private boolean caseSensitive;
    private boolean idByFunction;
    private Class<?> baseClass;
    
    public MapperPlugin() {
        this.mappers = new HashSet<String>();
        this.caseSensitive = false;
        this.idByFunction = false;
        this.baseClass = null;
    }

    @Override
    public void setContext(final Context context) {
        super.setContext(context);
        final CommentGeneratorConfiguration commentCfg = new CommentGeneratorConfiguration();
        commentCfg.setConfigurationType(MapperCommentGenerator.class.getCanonicalName());
        context.setCommentGeneratorConfiguration(commentCfg);
        final String rootClass = context.getJavaModelGeneratorConfiguration().getProperty("rootClass");
        if (StringUtility.stringHasValue(rootClass)) {
            try {
                this.baseClass = Class.forName(rootClass);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        final Properties properties = context.getProperties();
        this.idByFunction = new Boolean(properties.getProperty("idByFunction", "false"));
    }
    @Override
    public void setProperties(final Properties properties) {
        super.setProperties(properties);
        final String mappers = this.properties.getProperty("mappers");
        if (StringUtility.stringHasValue(mappers)) {
            for (final String mapper : mappers.split(",")) {
                this.mappers.add(mapper);
            }
            final String caseSensitive = this.properties.getProperty("caseSensitive");
            if (StringUtility.stringHasValue(caseSensitive)) {
                this.caseSensitive = "TRUE".equalsIgnoreCase(caseSensitive);
            }
            return;
        }
        throw new RuntimeException("Mapper插件缺少必要的mappers属性!");
    }
    @Override
    public boolean validate(final List<String> warnings) {
        return true;
    }
    @Override
    public boolean clientGenerated(final Interface interfaze, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        final FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        for (final String mapper : this.mappers) {
            interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
            interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
        }
        interfaze.addImportedType(entityType);
        GeneratorUtil.fillJavaDocLine((JavaElement)interfaze, introspectedTable.getTableConfiguration().getTableName(), ClassType.MAPPER);
        return true;
    }
    
    private void processEntityClass(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        GeneratorUtil.fillJavaDocLine((JavaElement)topLevelClass, introspectedTable.getTableConfiguration().getTableName(), ClassType.POJO);
        topLevelClass.addImportedType("javax.persistence.*");
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        if (StringUtility.stringContainsSpace(tableName)) {
            tableName = this.context.getBeginningDelimiter() + tableName + this.context.getEndingDelimiter();
        }
        if (this.caseSensitive && !topLevelClass.getType().getShortName().equals(tableName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + tableName + "\")");
        }
        else if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + tableName + "\")");
        }
        final List<Field> fieldList = (List<Field>)topLevelClass.getFields();
        if (this.idByFunction) {
            topLevelClass.addImportedType("com.java1234.dal.utils.SequenceRule");
            topLevelClass.addAnnotation("@SequenceRule");
        }
        final Map<String, Field> fieldMap = fieldList.stream().collect(Collectors.toMap(Field::getName,Function.identity()));
        if (this.baseClass != null) {
            final List<String> duplicateFieldNameList = Arrays.asList(this.baseClass.getDeclaredFields()).stream().filter(f -> f.getAnnotation(Transient.class) == null).filter(f -> fieldMap.get(f.getName()) != null).map(f -> f.getName()).collect(Collectors.toList());
            duplicateFieldNameList.stream().forEach(fieldName -> fieldList.remove(fieldMap.get(fieldName)));
            final List<Method> methodList = (List<Method>)topLevelClass.getMethods().stream().filter(m -> duplicateFieldNameList.contains(this.coverMethodToFieldName(m.getName()))).collect(Collectors.toList());
            methodList.stream().forEach(m -> topLevelClass.getMethods().remove(m));
            System.out.println();
        }
    }
    
    private String coverMethodToFieldName(String methodName) {
        methodName = methodName.replace("set", "").replace("get", "");
        return methodName.substring(0, 1).toLowerCase() + methodName.substring(1, methodName.length());
    }
    @Override
    public boolean modelBaseRecordClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        this.processEntityClass(topLevelClass, introspectedTable);
        return true;
    }
    @Override
    public boolean modelPrimaryKeyClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        this.processEntityClass(topLevelClass, introspectedTable);
        return true;
    }
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        this.processEntityClass(topLevelClass, introspectedTable);
        return false;
    }
    @Override
    public boolean sqlMapDocumentGenerated(final Document document, final IntrospectedTable introspectedTable) {
        final List<Element> elementList = (List<Element>)document.getRootElement().getElements();
        final Map<XmlElement, Attribute> jdbcTypeMap = Maps.newHashMap();
        final XmlElement[] xmlItem = new XmlElement[1];
        final Map<XmlElement, Attribute> map = null;
        final Attribute[] attribute = new Attribute[1];
        elementList.stream().filter(e -> e instanceof XmlElement).filter(e -> "resultMap".equalsIgnoreCase(((XmlElement) e).getName())).forEach(e -> ((XmlElement) e).getElements().stream().forEach(i -> {
            xmlItem[0] = (XmlElement) i;
            xmlItem[0].getAttributes().stream().filter(a -> "jdbcType".equalsIgnoreCase(a.getName())).forEach(a -> attribute[0] = map.put(xmlItem[0], a));
        }));
        jdbcTypeMap.forEach((k, v) -> k.getAttributes().remove(v));
        return true;
    }
    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientInsertMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientInsertSelectiveMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(final Method method, final Interface interfaze, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientInsertMethodGenerated(final Method method, final Interface interfaze, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientInsertSelectiveMethodGenerated(final Method method, final Interface interfaze, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientSelectAllMethodGenerated(final Method method, final Interface interfaze, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientSelectAllMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(final Method method, final Interface interfaze, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(final Method method, final Interface interfaze, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(final Method method, final Interface interfaze, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(final Method method, final Interface interfaze, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean sqlMapInsertElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean sqlMapSelectAllElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(final XmlElement element, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean providerGenerated(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean providerApplyWhereMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean providerInsertSelectiveMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
    @Override
    public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(final Method method, final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
        return false;
    }
}
