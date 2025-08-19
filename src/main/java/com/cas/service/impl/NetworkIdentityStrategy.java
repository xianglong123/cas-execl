package com.cas.service.impl;

import com.cas.service.ClassificationStrategy;

import java.util.Arrays;
import java.util.List;

public class NetworkIdentityStrategy implements ClassificationStrategy {
    private static final List<String> IDENTITY_KEYWORDS = Arrays.asList(
        "mobile", "phone", "email", "user", "login", "ip", 
        "手机", "电话", "邮箱", "登录", "账号"
    );

    @Override
    public boolean matches(String englishName, String chineseName) {
        englishName = englishName.toLowerCase();
        chineseName = chineseName.toLowerCase();

        String finalEnglishName = englishName;
        String finalChineseName = chineseName;
        return IDENTITY_KEYWORDS.stream().anyMatch(k ->
            finalEnglishName.contains(k) || finalChineseName.contains(k));
    }

    @Override public String getCategoryCode() { return "A1-2"; }
    @Override public int getLevel() { return 3; }
    @Override public String getDescription() { return "网络身份标识"; }
}