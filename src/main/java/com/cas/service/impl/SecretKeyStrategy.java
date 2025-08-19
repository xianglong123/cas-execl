package com.cas.service.impl;

import com.cas.service.ClassificationStrategy;

import java.util.Arrays;
import java.util.List;

import static com.cas.service.impl.IdentityDocumentStrategy.containsAny;

public class SecretKeyStrategy implements ClassificationStrategy {
    private static final List<String> KEYWORDS = Arrays.asList(
        "log", "record", "history", "audit", "trace",
        "日志", "记录", "历史", "审计", "跟踪"
    );

    @Override
    public boolean matches(String englishName, String chineseName) {
        return containsAny(englishName, chineseName, KEYWORDS) ||
               englishName.endsWith("_log") || 
               chineseName.endsWith("日志");
    }

    @Override public String getCategoryCode() { return "A2-2"; }
    @Override public int getLevel() { return 4; }
    @Override public String getDescription() { return "服务记录和日志"; }
}