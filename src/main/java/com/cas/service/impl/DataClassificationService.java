package com.cas.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cas.service.ClassificationStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DataClassificationService {
    private static final List<ClassificationStrategy> STRATEGIES = Arrays.asList(
            // 4级分类（高敏感）
            new IdentityDocumentStrategy(),
            new PrivateInfoStrategy(),
            new PasswordRelatedStrategy(),
            new SecretKeyStrategy(),
            new ContactInfoStrategy(),

            // 3级分类
            new NaturalPersonStrategy(),
            new NetworkIdentityStrategy(),
            new ServiceContentStrategy(),
            new ServiceLogStrategy(),
            new LocationDataStrategy()
            );

    /**
     * 获取字段分类结果
     *
     * @return Optional.empty()表示未匹配任何分类
     */
    public Optional<ClassificationResult> classify(String englishName, String chineseName) {
        if (StrUtil.isBlank(englishName)) {
            return Optional.empty();
        }

        return STRATEGIES.stream()
                .filter(strategy -> strategy.matches(englishName,
                        StrUtil.blankToDefault(chineseName, "")))
                .findFirst()
                .map(strategy -> new ClassificationResult(
                        strategy.getCategoryCode(),
                        strategy.getLevel(),
                        strategy.getDescription()
                ));
    }

    public static class ClassificationResult {
        private final String categoryCode;
        private final int level;
        private final String description;

        // constructor & getters...


        public ClassificationResult(String categoryCode, int level, String description) {
            this.categoryCode = categoryCode;
            this.level = level;
            this.description = description;
        }

        public String getCategoryCode() {
            return categoryCode;
        }

        public int getLevel() {
            return level;
        }

        public String getDescription() {
            return description;
        }
    }
}