package com.cas.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/5/23 11:57 上午
 * @desc
 */
public enum CardVersionEnum {

    // 超级SIM卡 seid与数据库对应关系
    CARD_3_0_UP("10", "3"),
    CARD_3_0("09", "2"),
    CARD_OTHER("", "1"),
    ;

    private String seidVersion;

    private String dbVersion;

    public static String getCardVersionBySeid(String seid) {
        if (StringUtils.isBlank(seid)) {
            return CARD_OTHER.getDbVersion();
        }
        String version = seid.substring(8, 10);

        CardVersionEnum[] values = CardVersionEnum.values();
        for (CardVersionEnum cv : values) {
            if (cv.getSeidVersion().equals(version)) {
                return cv.getDbVersion();
            }
        }
        return CARD_OTHER.getDbVersion();
    }

    CardVersionEnum(String seidVersion, String dbVersion) {
        this.seidVersion = seidVersion;
        this.dbVersion = dbVersion;
    }

    public String getSeidVersion() {
        return seidVersion;
    }

    public void setSeidVersion(String seidVersion) {
        this.seidVersion = seidVersion;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }
}
