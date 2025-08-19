package com.cas.service.impl;

import com.cas.service.ClassificationStrategy;

import java.util.Arrays;
import java.util.List;

import static com.cas.service.impl.IdentityDocumentStrategy.containsAny;

public class PasswordRelatedStrategy implements ClassificationStrategy {
    private static final List<String> KEYWORDS = Arrays.asList(
        "password", "credential", "token", "auth",
        "密码", "凭证", "令牌", "验证"
    );

    @Override
    public boolean matches(String englishName, String chineseName) {
        return containsAny(englishName, chineseName, KEYWORDS) ||
               englishName.matches(".*(pwd|pass|token).*");
    }

    @Override public String getCategoryCode() { return "A2-1"; }
    @Override public int getLevel() { return 4; }
    @Override public String getDescription() { return "用户密码及关联信息"; }
}