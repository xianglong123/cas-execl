package com.cas.service.impl;

import com.cas.service.ClassificationStrategy;

import java.util.Arrays;
import java.util.List;

public class NaturalPersonStrategy implements ClassificationStrategy {
    private static final List<String> PERSON_KEYWORDS = Arrays.asList(
        "姓名", "名字", "人名", "用户", "客户", "person", "name", "user"
    );
    private static final List<String> EXCLUDE_KEYWORDS = Arrays.asList(
        "公司", "企业", "商户", "product", "business"
    );

    @Override
    public boolean matches(String englishName, String chineseName) {
        englishName = englishName.toLowerCase();
        chineseName = chineseName.toLowerCase();
        
        // 排除非自然人字段
        String finalEnglishName = englishName;
        String finalChineseName = chineseName;
        if (EXCLUDE_KEYWORDS.stream().anyMatch(k ->
            finalEnglishName.contains(k) || finalChineseName.contains(k))) {
            return false;
        }
        
        // 检查自然人关键词
        return PERSON_KEYWORDS.stream().anyMatch(k ->
                finalEnglishName.contains(k) || finalChineseName.contains(k));
    }

    @Override public String getCategoryCode() { return "A1-1"; }
    @Override public int getLevel() { return 3; }
    @Override public String getDescription() { return "自然人身份标识"; }
}