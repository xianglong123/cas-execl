package com.cas.utils;

import cn.hutool.core.map.MapUtil;
import com.alibaba.excel.util.StringUtils;
import org.apache.poi.util.StringUtil;

import java.util.*;

public class DataClassificationRules {
    // 4级数据分类映射
    private static final Map<String, String> LEVEL4_CATEGORIES = new HashMap<String, String>() {{
        put("A1-4", "实体身份证明");
        put("A1-5", "用户私密信息");
        put("A2-1", "用户密码及关联信息");
        put("A2-2", "密钥信息");
        put("B1-2", "联系人信息");
    }};



    // 3级数据分类映射
    private static final Map<String, String> LEVEL3_CATEGORIES = createLevel3Categories();

    private static Map<String, String> createLevel3Categories() {
        Map<String, String> map = new HashMap<>();
        map.put("A1-1", "自然人身份标识");
        map.put("A1-2", "网络身份标识");
        map.put("B1-1", "服务内容数据");
        map.put("C1-2", "服务记录和日志");
        map.put("C1-4", "位置数据");
        map.put("D1-1", "用户使用习惯和行为分析数据");
        map.put("D1-2", "用户上网行为相关统计分析数据");
        map.put("E1", "网络规划建设类数据");
        map.put("E2", "网络与系统资源类数据");
        map.put("E3", "网络与系统运维类数据");
        map.put("E4", "网络安全管理类数据");
        map.put("G1", "发展战略与重大决策");
        map.put("G2", "业务发展类数据");
        map.put("G5", "生产经营类数据");
        map.put("G6-3", "监督管理信息");
        return Collections.unmodifiableMap(map);
    }

    // 自然人身份直接标识字段（必须包含的关键词）
    private static final List<String> PERSON_DIRECT_IDENTIFIERS = Arrays.asList(
            "姓名", "名字", "名称", "人名", "用户", "客户",
            "person", "name", "user", "guest", "host",
            "legal_person", "legal_name", "apply_name", "approval_name"
    );

    // 自然人身份关联属性（需结合上下文判断）
    private static final List<String> PERSON_RELATED_ATTRIBUTES = Arrays.asList(
            "身份证", "证件号", "手机号", "电话", "地址",
            "id_no", "id_card", "mobile", "phone", "address"
    );

    // 明确排除的非自然人字段关键词
    private static final List<String> NON_PERSON_KEYWORDS = Arrays.asList(
            "公司", "企业", "商户", "接口", "应用", "产品", "业务",
            "company", "merchant", "interface", "app", "product", "business",
            "服务", "规则", "活动", "奖品", "文件", "资源",
            "service", "rule", "activity", "prize", "file", "resource"
    );

    // 4级敏感字段关键词（英文）
    private static final List<String> LEVEL4_EN_KEYWORDS = Arrays.asList(
        "token", "key", "secret", "password", "private", 
        "photo", "image", "face", "biometric", "signature",
        "cipher", "encrypt", "cert", "hsm", "rsa"
    );

    // 4级敏感字段关键词（中文）
    private static final List<String> LEVEL4_CN_KEYWORDS = Arrays.asList(
        "令牌", "秘钥", "密码", "私钥", "照片",
        "人像", "生物特征", "签名", "密文", "加密",
        "证书", "身份证", "护照", "驾照", "营业执照"
    );

    // 3级敏感字段关键词（英文）
    private static final List<String> LEVEL3_EN_KEYWORDS = Arrays.asList(
        "id", "name", "mobile", "phone", "email",
        "address", "location", "gps", "log", "record",
        "content", "message", "sms", "call", "contact"
    );

    // 3级敏感字段关键词（中文）
    private static final List<String> LEVEL3_CN_KEYWORDS = Arrays.asList(
        "身份证", "姓名", "手机", "电话", "邮箱",
        "地址", "位置", "坐标", "日志", "记录",
        "内容", "消息", "短信", "通话", "联系人"
    );

    // 特殊字段直接映射
    private static final Map<String, String> SPECIAL_FIELD_MAPPINGS = createSpecialFieldMappings();

    private static Map<String, String> createSpecialFieldMappings() {
        Map<String, String> map = new HashMap<>();
        map.put("photo_url", "A1-4");
        map.put("new_photo_url", "A1-4");
        map.put("identity_card_positive_url", "A1-4");
        map.put("identity_card_reverse_url", "A1-4");
        map.put("legal_person_name", "A1-1");
        map.put("legal_person_id_no", "A1-1");
        map.put("mobile_no", "A1-2");
        map.put("msisdn", "A1-2");
        map.put("email", "A1-2");
        map.put("user_name", "A1-1");
        map.put("login_name", "A1-2");
        map.put("login_ip", "A1-2");
        map.put("mobile", "A1-2");
        map.put("access_token", "A2-1");
        map.put("refresh_token", "A2-1");
        map.put("validate_code", "A2-1");
        return Collections.unmodifiableMap(map);
    }

    // 更新A1-1类检查方法
    private static String checkA11NaturalPerson(String enName, String cnName) {
        enName = enName.toLowerCase();
        cnName = cnName.toLowerCase();

        // 1. 先检查明确排除的非自然人字段
        if (containsAny(enName, cnName, NON_PERSON_KEYWORDS)) {
            return null;
        }

        // 2. 检查直接标识字段
        boolean isDirectIdentifier =
                containsAny(enName, PERSON_DIRECT_IDENTIFIERS) ||
                        containsAny(cnName, PERSON_DIRECT_IDENTIFIERS);

        // 3. 检查关联属性字段（需与直接标识组合）
        boolean hasRelatedAttribute =
                containsAny(enName, PERSON_RELATED_ATTRIBUTES) ||
                        containsAny(cnName, PERSON_RELATED_ATTRIBUTES);

        // 4. 特殊字段处理（如法人信息）
        boolean isLegalPersonInfo =
                enName.contains("legal_") || cnName.contains("法人");

        // 判断逻辑：
        // - 直接标识字段 或
        // - (关联属性+上下文) 或
        // - 特殊法人字段
        if (isDirectIdentifier ||
                (hasRelatedAttribute && isPersonContext(enName, cnName)) ||
                isLegalPersonInfo) {
            return "A1-1";
        }

        return null;
    }

    // 判断是否在自然人上下文中
    private static boolean isPersonContext(String enName, String cnName) {
        // 排除组织/公司上下文
        if (enName.matches(".*(company|org|dept|merchant).*") ||
                cnName.matches(".*(公司|组织|部门|商户).*")) {
            return false;
        }

        // 包含个人操作上下文
        return enName.matches(".*(personal|person|individual).*") ||
                cnName.matches(".*(个人|自然人).*");
    }

    // 辅助方法：检查字符串是否包含列表中的任意关键词
    private static boolean containsAny(String str, List<String> keywords) {
        return keywords.stream().anyMatch(str::contains);
    }

    private static boolean containsAny(String enStr, String cnStr, List<String> keywords) {
        return keywords.stream().anyMatch(k ->
                enStr.contains(k) || cnStr.contains(k));
    }

    // 获取字段的分类和级别
    public static ClassificationResult classifyField(String fieldEnglishName, String fieldChineseName) {
        // 1. 先检查特殊字段映射
        String specialCategory = SPECIAL_FIELD_MAPPINGS.get(fieldEnglishName);
        if (specialCategory != null) {
            return new ClassificationResult(
                specialCategory, 
                LEVEL4_CATEGORIES.containsKey(specialCategory) ? 4 : 3,
                LEVEL4_CATEGORIES.getOrDefault(specialCategory, 
                    LEVEL3_CATEGORIES.get(specialCategory))
            );
        }

        // 2. 检查4级分类
        String level4Category = checkLevel4(fieldEnglishName, fieldChineseName);
        if (level4Category != null) {
            return new ClassificationResult(
                level4Category,
                4,
                LEVEL4_CATEGORIES.get(level4Category)
            );
        }

        // 3. 检查3级分类
        String level3Category = checkLevel3(fieldEnglishName, fieldChineseName);
        if (level3Category != null) {
            return new ClassificationResult(
                level3Category,
                3,
                LEVEL3_CATEGORIES.get(level3Category)
            );
        }

        // 4. 默认返回空
        return new ClassificationResult(null, null, null);
    }

    private static boolean isA12NetworkIdentity(String enName, String cnName) {
        enName = enName.toLowerCase();
        cnName = cnName.toLowerCase();

        boolean noMatch = !enName.matches(".*(journal_no|delete_flag|msisdn|email|user|login|ip).*");

        // 英文关键词匹配
        boolean enMatch = enName.matches(".*(mobile|phone|msisdn|email|user|login|ip).*");

        // 中文关键词匹配
        boolean cnMatch = cnName.matches(".*(手机|电话|邮箱|用户|登录|IP).*");

        // 特殊格式匹配（如手机号格式）
        boolean numberFormatMatch = enName.matches(".*(no|num|number).*")
                && cnName.contains("号");

        return noMatch || enMatch || cnMatch || numberFormatMatch;
    }

    private static String checkLevel4(String enName, String cnName) {
        if (StringUtils.isBlank(enName) || StringUtils.isBlank(cnName)) {
            return null;
        }
        enName = enName.toLowerCase();
        cnName = cnName.toLowerCase();

        // 检查A1-4类（实体身份证明）
        if (enName.contains("card") || enName.contains("id") || 
            cnName.contains("身份证") || cnName.contains("护照")) {
            return "A1-4";
        }

        // 检查A1-5类（用户私密信息）
        if (enName.contains("private") ||
            cnName.contains("私密") || cnName.contains("健康")) {
            return "A1-5";
        }

        // 检查A2-1类（用户密码及关联信息）
        if (enName.contains("password") || enName.contains("token") ||
            cnName.contains("密码") || cnName.contains("令牌")) {
            return "A2-1";
        }

        // 检查A2-2类（密钥信息）
        if (enName.contains("key") || enName.contains("secret") ||
            cnName.contains("密钥") || cnName.contains("秘钥")) {
            return "A2-2";
        }

        // 检查B1-2类（联系人信息）
        if (enName.contains("contact") || enName.contains("address") ||
            cnName.contains("联系人") || cnName.contains("地址")) {
            return "B1-2";
        }

        return null;
    }

    private static String checkLevel3(String enName, String cnName) {
        if (StringUtils.isBlank(enName) || StringUtils.isBlank(cnName)) {
            return null;
        }
        enName = enName.toLowerCase();
        cnName = cnName.toLowerCase();

        // 检查A1-1类（自然人身份标识）
        String A11 = checkA11NaturalPerson(enName, cnName);
        if (A11 != null) {
            return A11;
        }


        // 检查A1-2类（网络身份标识）
        if (isA12NetworkIdentity(enName, cnName)) {
            return "A1-2";
        }

        // 检查B1-1类（服务内容数据）
        if (enName.contains("content") || enName.contains("message") ||
            cnName.contains("内容") || cnName.contains("消息")) {
            return "B1-1";
        }

        // 检查C1-2类（服务记录和日志）
        if (enName.contains("log") || enName.contains("record") ||
            cnName.contains("日志") || cnName.contains("记录")) {
            return "C1-2";
        }

        // 检查C1-4类（位置数据）
        if (enName.contains("gps") || enName.contains("location") ||
            cnName.contains("位置") || cnName.contains("坐标")) {
            return "C1-4";
        }

        // 检查D1-1/D1-2类（用户行为数据）
        if (enName.contains("behavior") || enName.contains("analysis") ||
            cnName.contains("行为") || cnName.contains("分析")) {
            return "D1-1";
        }

        // 检查E类（网络相关数据）
        if (enName.contains("network") || enName.contains("system") ||
            cnName.contains("网络") || cnName.contains("系统")) {
            return "E2";
        }

        // 检查G类（业务数据）
        if (enName.contains("business") || enName.contains("order") ||
            cnName.contains("业务") || cnName.contains("订单")) {
            return "G2";
        }

        return null;
    }

    public static class ClassificationResult {
        private String categoryCode;
        private Integer level;
        private String categoryDescription;

        public ClassificationResult(String categoryCode, Integer level, String categoryDescription) {
            this.categoryCode = categoryCode;
            this.level = level;
            this.categoryDescription = categoryDescription;
        }

        public String getCategoryCode() {
            return categoryCode;
        }

        public Integer getLevel() {
            return level;
        }

        public String getCategoryDescription() {
            return categoryDescription;
        }

        // getters...
    }
}