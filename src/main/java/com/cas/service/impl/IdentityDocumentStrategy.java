package com.cas.service.impl;

import com.cas.service.ClassificationStrategy;

import java.util.Arrays;
import java.util.List;



public class IdentityDocumentStrategy implements ClassificationStrategy {
    private static final List<String> KEYWORDS = Arrays.asList(
        "id_card", "passport", "license", "certificate", 
        "身份证", "护照", "驾照", "营业执照"
    );

    @Override
    public boolean matches(String englishName, String chineseName) {
        return containsAny(englishName, chineseName, KEYWORDS) || 
               englishName.matches(".*(id|card|cert).*");
    }

    public static boolean containsAny(String enStr, String cnStr, List<String> keywords) {
        return keywords.stream().anyMatch(k ->
                enStr.contains(k) || cnStr.contains(k));
    }

    @Override public String getCategoryCode() { return "A1-4"; }
    @Override public int getLevel() { return 4; }
    @Override public String getDescription() { return "实体身份证明"; }
}