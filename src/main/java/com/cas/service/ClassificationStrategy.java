package com.cas.service;

public interface ClassificationStrategy {
    /**
     * 判断字段是否属于当前分类
     * @param englishName 字段英文名
     * @param chineseName 字段中文名
     * @return 是否属于当前分类
     */
    boolean matches(String englishName, String chineseName);

    /**
     * 获取分类编码
     */
    String getCategoryCode();

    /**
     * 获取分类级别
     */
    int getLevel();

    /**
     * 获取分类描述
     */
    String getDescription();
}