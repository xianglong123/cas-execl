package com.cas.utils;

import com.cas.po.DatabaseMetadataBO;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DataLevelClassifier {

    // 定义敏感关键词列表（英文）
    private static final List<String> SENSITIVE_ENGLISH_KEYWORDS = Arrays.asList(
            "id_no", "id_card", "identity_card", "legal_person", 
            "name", "user_name", "photo", "card", "identity"
    );

    // 定义敏感关键词列表（中文）
    private static final List<String> SENSITIVE_CHINESE_KEYWORDS = Arrays.asList(
            "身份证", "身份证号", "姓名", "人像", "法人", 
            "照", "认证", "客户", "邀请人", "受邀人"
    );

    // 定义3级数据的正则模式
    private static final Pattern LEVEL3_PATTERN = Pattern.compile(
            "(id|identity|card|no|name|person|法人|身份证|姓名)"
    );

    // 定义4级数据的正则模式（更敏感的信息）
    private static final Pattern LEVEL4_PATTERN = Pattern.compile(
            "(photo|image|人像|照片|照|存储)"
    );

    public static String classifyDataLevel(DatabaseMetadataBO metadata) {
        if (metadata == null || 
            StringUtils.isBlank(metadata.getFieldEnglishName()) || 
            StringUtils.isBlank(metadata.getFieldChineseName())) {
            return "未知";
        }

        String englishName = metadata.getFieldEnglishName().toLowerCase();
        String chineseName = metadata.getFieldChineseName().toLowerCase();

        // 先检查4级数据（最高敏感度）
        if (containsLevel4Keyword(englishName, chineseName)) {
            return "4";
        }

        // 检查3级数据
        if (containsLevel3Keyword(englishName, chineseName)) {
            return "3";
        }

        // 默认返回低级
        return "1";
    }

    private static boolean containsLevel4Keyword(String englishName, String chineseName) {
        // 检查英文名是否包含4级关键词
        boolean englishMatch = LEVEL4_PATTERN.matcher(englishName).find() || 
                SENSITIVE_CHINESE_KEYWORDS.stream().anyMatch(keyword -> 
                        englishName.contains(keyword.toLowerCase()));

        // 检查中文名是否包含4级关键词
        boolean chineseMatch = LEVEL4_PATTERN.matcher(chineseName).find() || 
                SENSITIVE_CHINESE_KEYWORDS.stream().anyMatch(keyword -> 
                        chineseName.contains(keyword.toLowerCase()));

        // 特殊字段直接匹配
        return englishMatch || chineseMatch || 
               englishName.contains("photo_url") || 
               englishName.contains("new_photo_url") || 
               chineseName.contains("人像信息") || 
               chineseName.contains("照片");
    }

    private static boolean containsLevel3Keyword(String englishName, String chineseName) {
        // 检查英文名是否包含3级关键词
        boolean englishMatch = LEVEL3_PATTERN.matcher(englishName).find() || 
                SENSITIVE_ENGLISH_KEYWORDS.stream().anyMatch(keyword -> 
                        englishName.contains(keyword.toLowerCase()));

        // 检查中文名是否包含3级关键词
        boolean chineseMatch = LEVEL3_PATTERN.matcher(chineseName).find() || 
                SENSITIVE_CHINESE_KEYWORDS.stream().anyMatch(keyword -> 
                        chineseName.contains(keyword.toLowerCase()));

        // 特殊字段直接匹配
        return englishMatch || chineseMatch || 
               englishName.contains("id_no") || 
               englishName.contains("legal_person") || 
               chineseName.contains("身份证") || 
               chineseName.contains("姓名");
    }

    // 示例使用方法
    public static void main(String[] args) {
        DatabaseMetadataBO sample1 = new DatabaseMetadataBO();
        sample1.setFieldEnglishName("id_no_encrypt");
        sample1.setFieldChineseName("身份证号密文");
        System.out.println("数据级别: " + classifyDataLevel(sample1)); // 应输出3

        DatabaseMetadataBO sample2 = new DatabaseMetadataBO();
        sample2.setFieldEnglishName("photo_url");
        sample2.setFieldChineseName("人像信息");
        System.out.println("数据级别: " + classifyDataLevel(sample2)); // 应输出4
    }
}