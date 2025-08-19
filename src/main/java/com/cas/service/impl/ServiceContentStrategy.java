package com.cas.service.impl;

import com.cas.service.ClassificationStrategy;

import java.util.Arrays;
import java.util.List;

import static com.cas.service.impl.IdentityDocumentStrategy.containsAny;

public class ServiceContentStrategy implements ClassificationStrategy {
    private static final List<String> KEYWORDS = Arrays.asList(
        "content", "message", "sms", "mail", "call",
        "内容", "消息", "短信", "邮件", "通话"
    );

    @Override
    public boolean matches(String englishName, String chineseName) {
        return containsAny(englishName, chineseName, KEYWORDS) &&
               !englishName.contains("metadata"); // 排除元数据
    }

    @Override public String getCategoryCode() { return "B1-1"; }
    @Override public int getLevel() { return 3; }
    @Override public String getDescription() { return "服务内容数据"; }
}