package com.java1234.dal.enums;

/**
 * 系统参数枚举类型
 */
public enum ConfigEnum {

    /**
     * 操作时限获取方式
     */
    EMERGENCY_TYPE("EMERGENCY_TYPE"),

    /**
     * 是否需要民警审核
     */
    NORMAL_AUDIT("NORMAL_AUDIT"),

    /**
     * 是否需要所领导审核
     */
    NORMAL_LEADER_AUDIT("NORMAL_LEADER_AUDIT"),

    /**
     * 所领导是否审核本人物建信息员上报的情报
     */
    NORMAL_LEADER_AUDIT_NOT_MY("NORMAL_LEADER_AUDIT_NOT_MY"),

    /**
     * 派出所是否支持入库
     */
    NORMAL_LEADER_AUDIT_STOCK("NORMAL_LEADER_AUDIT_STOCK"),

    /**
     * 研判操作单位
     */
    JUDGE_TYPE("JUDGE_TYPE"),

    /**
     * 研判采用、上报是否必须先存档
     */
    REPORT_PRE("REPORT_PRE"),

    /**
     * 申报奖励条件
     */
    REWARD_TYPE("REWARD_TYPE"),

    /**
     * 信息员代号手动或者自动生成
     */
    INFORMER_CLERK_SYMBOL("INFORMER_CLERK_SYMBOL"),

    /**
     * 耳目代号手动或者自动生成
     */
    INFORMER_EYE_SYMBOL("INFORMER_EYE_SYMBOL"),

    /**
     * 特勤代号手动或者自动生成
     */
    INFORMER_AGENT_SYMBOL("INFORMER_AGENT_SYMBOL"),

    /**
     * 信息员代号自动生成前缀
     */
    INFORMER_CLERK_SYMBOLPRE("INFORMER_CLERK_SYMBOLPRE"),

    /**
     * 耳目代号自动生成前缀
     */
    INFORMER_EYE_SYMBOLPRE("INFORMER_EYE_SYMBOLPRE"),

    /**
     * 特勤代号自动生成前缀
     */
    INFORMER_AGENT_SYMBOLPRE("INFORMER_AGENT_SYMBOLPRE"),

    /**
     * XXY、303、321，初始密码
     */
    INFORMER_NORMOL_PWD("INFORMER_NORMOL_PWD"),

    /**
     * 专代号自动生成：默认的单位信息
     */
    INFORMER_SYMBOL_DEFAULT_DEPT("INFORMER_SYMBOL_DEFAULT_DEPT"),

    /**
     * 价值数据文件导入时，显示预览行数
     */
    VALUE_DATA_SHOW_ROWS("VALUE_DATA_SHOW_ROWS"),

    /**
     * 智能分析solr搜索引擎地址
     */
    SOLR_URL("SOLR_URL"),

    /**
     * 智能分析solr搜索引擎实体标识字段
     */
    SOLR_ENTITY_FIELD("SOLR_ENTITY_FIELD"),

    /**
     * 智能分析solr搜索引擎代理字段
     */
    SOLR_PROXY("SOLR_PROXY"),

    /**
     * 短信平台账号
     */
    SMS_ACCOUNT("SMS_ACCOUNT"),

    /**
     * 短信平台密码
     */
    SMS_PASSWORD("SMS_PASSWORD"),

    /**
     * 短信平台处理方式
     */
    SMS_HANDLE("SMS_HANDLE"),

    /**
     * 短信平台每次获取条数
     */
    SMS_COUNT("SMS_COUNT"),

    /**
     * 短信平台地址
     */
    SMS_URL("SMS_URL");


    private final String code;

    ConfigEnum(String code) {
        this.code = code;
    }

    public static ConfigEnum getType(final String code) {
        for (ConfigEnum type : ConfigEnum.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("wrong " + ConfigEnum.class.getName());
    }

    public String getValue() {
        return this.code;
    }
}
