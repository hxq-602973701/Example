package com.java1234.generator;

public enum ClassType
{
    CONTROLLER("控制器"),
    SERVICE("Service"), 
    DAO("DAO"), 
    MAPPER("Mapper"), 
    POJO("实体");
    
    private final String comment;
    
    private ClassType(final String comment) {
        this.comment = comment;
    }
    
    public String getComment() {
        return this.comment;
    }
}