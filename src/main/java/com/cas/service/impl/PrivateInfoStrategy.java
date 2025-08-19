package com.cas.service.impl;

import com.cas.service.ClassificationStrategy;

import java.util.Arrays;
import java.util.List;

import static com.cas.service.impl.IdentityDocumentStrategy.containsAny;

public class PrivateInfoStrategy implements ClassificationStrategy {
    private static final List<String> KEYWORDS = Arrays.asList(
        "private", "secret", "health", "gene", "religion",
        "私密", "健康", "基因", "宗教信仰", "住址"
    );

    @Override
    public boolean matches(String englishName, String chineseName) {
        return containsAny(englishName, chineseName, KEYWORDS) ||
               englishName.matches(".*(private|secret).*");
    }

    @Override public String getCategoryCode() { return "A1-5"; }
    @Override public int getLevel() { return 4; }
    @Override public String getDescription() { return "用户私密信息"; }
}