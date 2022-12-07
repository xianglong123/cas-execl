package com.cas.po;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/12/7 11:49 上午
 * @desc
 */
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 18)
public class SimpleWriteData {

    /**
     * 姓名
     */
    // 字符串的头背景设置成粉红 IndexedColors.PINK.getIndex()
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 17)
    @ExcelProperty({"Easy-excel 简单模版", "姓名"})
    private String name;
    /**
     * 身份证
     */
    // @ColumnWidth(25)
    @HeadStyle(fillPatternType = FillPatternTypeEnum.FINE_DOTS, fillForegroundColor = 17)
    @ExcelProperty({"Easy-excel 简单模版", "身份证"})
    private String idCard;
    /**
     * 手机号
     */
    @ExcelProperty({"Easy-excel 简单模版", "手机号"})
    @HeadStyle(fillPatternType = FillPatternTypeEnum.ALT_BARS, fillForegroundColor = 17)
    private String mobileNo;
    /**
     * 性别
     */
    @ExcelProperty({"Easy-excel 简单模版", "性别"})
    @HeadStyle(fillPatternType = FillPatternTypeEnum.FINE_DOTS, fillForegroundColor = 17)
    private String sex;
    /**
     * 年龄
     */
    @ExcelProperty({"Easy-excel 简单模版", "年龄"})
    @HeadStyle(fillPatternType = FillPatternTypeEnum.ALT_BARS, fillForegroundColor = 17)
    private String age;
    /**
     * 国家
     */
    @ExcelProperty({"Easy-excel 简单模版", "国家"})
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SPARSE_DOTS, fillForegroundColor = 17)
    private String country;
    /**
     * 地区
     */
    @ExcelProperty({"Easy-excel 简单模版", "地区"})
    @HeadStyle(fillPatternType = FillPatternTypeEnum.THICK_HORZ_BANDS, fillForegroundColor = 17)
    private String area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
