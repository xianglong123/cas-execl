package com.cas.po;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class WriteBO {
    @Excel(name="手机号",width = 30, fixedIndex = 0)
    private String mobileNo;
    @Excel(name="PID",width = 30,orderNum="1", fixedIndex = 1)
    private String pid;

    /**
     * 失败原因
     */
    @Excel(name="录入失败原因",width = 30,orderNum="2", fixedIndex = 2)
    private String reason;

    private String writeType;

    public String getWriteType() {
        return writeType;
    }

    public void setWriteType(String writeType) {
        this.writeType = writeType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public WriteBO() {
    }

    public WriteBO(String mobileNo, String pid) {
        this.mobileNo = mobileNo;
        this.pid = pid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Szt{" +
                "mobileNo='" + mobileNo + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}

