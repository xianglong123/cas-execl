package com.cas.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.time.LocalDateTime;

public class DicpEmptyCardWhiteBO {

    public DicpEmptyCardWhiteBO(String journalNo, String seid, String mobileNo, String companyName, String authType, String operateName, LocalDateTime operateTime) {
        this.journalNo = journalNo;
        this.seid = seid;
        this.mobileNo = mobileNo;
        this.companyName = companyName;
        this.authType = authType;
        this.operateName = operateName;
        this.operateTime = operateTime;
    }

    /**
     * 第几页
     */
    private Integer pageNum;
    /**
     * 每页数
     */
    private Integer pageSize;

    /**
     * @Fields journalNo 流水号
     */
    @Excel(name = "流水号", width = 20)
    private String journalNo;
    /**
     * @Fields seid seid
     */
    @Excel(name = "seid", width = 20)
    private String seid;
    /**
     * @Fields mobileNo 手机号
     */
    @Excel(name = "手机号", width = 20)
    private String mobileNo;
    /**
     * @Fields companyName 合作方名称
     */
    @Excel(name = "合作方名称", width = 20)
    private String companyName;
    /**
     * @Fields authType 认证场景
     */
    @Excel(name = "认证场景", width = 20)
    private String authType;
    /**
     * @Fields operateMobile 操作人
     */
    @Excel(name = "操作人", width = 20)
    private String operateName;
    /**
     * @Fields operateTime 操作时间
     */
    @Excel(name = "操作时间", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operateTime;

    private String operateMobile;


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getJournalNo() {
        return journalNo;
    }

    public void setJournalNo(String journalNo) {
        this.journalNo = journalNo;
    }

    public String getSeid() {
        return seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public LocalDateTime getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(LocalDateTime operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateMobile() {
        return operateMobile;
    }

    public void setOperateMobile(String operateMobile) {
        this.operateMobile = operateMobile;
    }
}
