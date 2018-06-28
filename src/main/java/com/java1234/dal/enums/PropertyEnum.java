package com.java1234.dal.enums;

/**
 * 业务参数枚举类型
 */
public enum PropertyEnum {

    /**
     * 情报线索报送平台
     */
    INTELLIGENCE_PLATFORM("3078"),

    /**
     * 情报来源细类
     */
    INTELLIGENCE_SOURCESUB1("3030"),

    /**
     * 情报来源细类
     */
    INTELLIGENCE_SOURCESUB2("3031"),

    /**
     * 情报采集来源父节点
     */
    INTELLIGENCE_SOURCE("4200"),


    /**
     * 情报采集来源：电脑上报
     */
    INTELLIGENCE_SOURCE_PC("4201"),

    /**
     * 情报采集来源：短信上报
     */
    INTELLIGENCE_SOURCE_MSG("4202"),

    /**
     * 情报采集来源：APP上报
     */
    INTELLIGENCE_SOURCE_APP("4203"),

    /**
     * 情报采集来源：微信上报
     */
    INTELLIGENCE_SOURCE_WX("4204"),

    /**
     * 情报研判操作名称：父节点
     */
    INTELLIGENCE_JUDGE_PARENT("3040"),

    /**
     * 情报研判操作名称：退回
     */
    INTELLIGENCE_JUDGE_BACK("3041"),

    /**
     * 情报研判操作名称：存档
     */
    INTELLIGENCE_JUDGE_STOCK("3042"),

    /**
     * 情报研判操作名称：采用
     */
    INTELLIGENCE_JUDGE_USE("3043"),

    /**
     * 情报研判操作名称：上报
     */
    INTELLIGENCE_JUDGE_REPORT("3044"),

    /**
     * 情报研判操作名称：流转
     */
    INTELLIGENCE_JUDGE_FLOW("3045"),

    /**
     * 情报研判操作名称：流转至其他分局
     */
    INTELLIGENCE_JUDGE_FLOW_OTHER("3025"),

    /**
     * 情报研判操作名称（市局）：父节点
     */
    INTELLIGENCE_SJ_JUDGE_PARENT("4050"),

    /**
     * 情报研判操作名称（市局）：录用
     */
    INTELLIGENCE_SJ_JUDGE_USE("4051"),

    /**
     * 情报研判操作名称（市局）：入库
     */
    INTELLIGENCE_SJ_JUDGE_STOCK("4052"),

    /**
     * 情报研判操作名称（市局）：退回
     */
    INTELLIGENCE_SJ_JUDGE_BACK("4053"),

    /**
     * 情报研判操作名称（市局）：流转
     */
    INTELLIGENCE_SJ_JUDGE_FLOW("4054"),

    /**
     * 情报研判采用等级（市局）：父节点
     */
    INTELLIGENCE_LEVEL_PARENT("4060"),

    /**
     * 情报研判采用等级（市局）：一级
     */
    INTELLIGENCE_LEVEL_1("4061"),

    /**
     * 情报研判采用等级（市局）：二级
     */
    INTELLIGENCE_LEVEL_2("4062"),

    /**
     * 情报研判采用等级（市局）：三级
     */
    INTELLIGENCE_LEVEL_3("4063"),

    /**
     * 情报来源：人力情报
     */
    INTELLIGENCE_ORIGIN("3030"),

    /**
     * 情报来源：303 耳目
     */
    INTELLIGENCE_ORIGIN_303("3032"),

    /**
     * 情报来源：321 特勤
     */
    INTELLIGENCE_ORIGIN_321("3033"),

    /**
     * 情报来源：XXY 信息员
     */
    INTELLIGENCE_ORIGIN_XXY("3034"),

    /**
     * 情报采集紧急程度父节点
     */
    EMERGENCY_LEVEL("4300"),
    /**
     * 情报采集紧急程度平急
     */
    EMERGENCY_LEVEL_NORMAL("4301"),
    /**
     * 情报采集紧急程度紧急
     */
    EMERGENCY_LEVEL_MEDIUM("4302"),
    /**
     * 情报采集紧急程度特急
     */
    EMERGENCY_LEVEL_EXTRA("4303"),

    /**
     * 信息员类别
     */
    INFORMER_TYPE("910"),

    /**
     * 情报来源
     */
    INTELLIGENCE_RESOURCE("3029"),


    /**
     * 情报奖励父节点
     */
    INTELLIGENCE_BONUS("4205"),

    /**
     * 情报来源父节点
     */
    INTELLIGENCE_TYPE("3026"),

    /**
     * 情报来源-其他
     */
    INTELLIGENCE_TYPE_OTHER("10001"),

    /**
     * 单位管理-单位类型
     */
    DEPT_CONTROL("3700"),

    /**
     * 短信管理-短信提醒方式
     */
    SMS_REMIND_WAY("3810"),

    /**
     * 短信管理-短信提醒内容
     */
    SMS_REMIND_CONTENT("3850");


    private final String code;

    PropertyEnum(String code) {
        this.code = code;
    }

    public static PropertyEnum getType(final String code) {
        for (PropertyEnum type : PropertyEnum.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("wrong " + PropertyEnum.class.getName());
    }

    public String getValue() {
        return this.code;
    }
}
