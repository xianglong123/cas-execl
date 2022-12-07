package com.cas.enums;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/12/7 11:26 上午
 * @desc 模版类型
 */
public enum ModelTypeEnum {

    /**
     *
     */
    SIMPLE("excelmodel/simple.xlsx", "简单模版"),
    FILL("excelmodel/fill.xlsx", "需要填充模版"),
    FILL_LIST("excelmodel/list.xlsx", "需要填充模版"),
    COMPLEX("excelmodel/complex.xlsx", "复杂模版-多sheet"),
    ;

    private final String name;
    private final String msg;

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    ModelTypeEnum(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }
}
