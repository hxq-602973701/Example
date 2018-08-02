package com.java1234.dal.constant.page.message;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.java1234.exception.message.BaseMessage;

public enum TaskMessage implements BaseMessage {
    TASK_CRON_ERROR("task.cron.error", "cron表达式不正确"),
    TASK_TARGET_CLASS_NOT_FOUND("task.target.class.not.found", "未找到目标类"),
    TASK_TARGET_METHOD_NOT_FOUND("task.target.method.not.found", "未找到目标方法"),
    TASK_NAME_OR_GROUP_REPEAT("task.name.or.group.repeat", "检查 name group 组合是否有重复"),
    TASK_STATUS_CHANGE_ERROR("task.status.change.error", "任务状态改变失败");

    private final String messageCode;
    private final String message;

    private TaskMessage(String messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    @Override
    public String getMessageCode() {
        return this.messageCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
