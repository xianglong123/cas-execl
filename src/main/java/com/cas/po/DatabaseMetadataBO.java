package com.cas.po;

import com.cas.utils.DataClassificationRules;
import io.swagger.models.auth.In;
import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

public class DatabaseMetadataBO {
    @ExcelProperty(value = "序号", index = 0)
    private Integer serialNumber;
    
    @ExcelProperty(value = "平台系统名称", index = 1)
    private String systemName;
    
    @ExcelProperty(value = "所属部门", index = 2)
    private String department;
    
    @ExcelProperty(value = "表英文名", index = 3)
    private String tableEnglishName;
    
    @ExcelProperty(value = "所在机房", index = 4)
    private String machineRoom;
    
    @ExcelProperty(value = "数据库IP端口", index = 5)
    private String dbIpPort;
    
    @ExcelProperty(value = "数据库英文名", index = 6)
    private String databaseEnglishName;
    
    @ExcelProperty(value = "表中文名", index = 7)
    private String tableChineseName;
    
    @ExcelProperty(value = "字段英文名", index = 8)
    private String fieldEnglishName;
    
    @ExcelProperty(value = "字段中文名", index = 9)
    private String fieldChineseName;
    
    @ExcelProperty(value = "表大小（GB）", index = 10)
    private Double tableSizeGB;
    
    @ExcelProperty(value = "数据分类", index = 11)
    private String dataCategory;
    
    @ExcelProperty(value = "数据级别", index = 12)
    private Integer dataLevel;
    
    @ExcelProperty(value = "字段类型", index = 13)
    private String fieldType;
    
    @ExcelProperty(value = "是否已接入", index = 14)
    private String isConnected;
    
    @ExcelProperty(value = "数据分类", index = 15)
    private String dataClassification;
    
    @ExcelProperty(value = "拉通分工", index = 16)
    private String divisionOfLabor;

    // 数据分类方法
    public DataClassificationRules.ClassificationResult classifyData() {
        return DataClassificationRules.classifyField(
                this.getFieldEnglishName(),
                this.getFieldChineseName()
        );
    }

    // 获取完整分类信息
    public String getFullClassification() {
        DataClassificationRules.ClassificationResult result = classifyData();
        if (result.getCategoryCode() == null) {
            return null;
        }
        return String.format("%s：%s",
                result.getCategoryCode(),
                result.getCategoryDescription());
    }


    // 获取数据级别
    public Integer getDataLevel() {
        return classifyData().getLevel();
    }


    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTableEnglishName() {
        return tableEnglishName;
    }

    public void setTableEnglishName(String tableEnglishName) {
        this.tableEnglishName = tableEnglishName;
    }

    public String getMachineRoom() {
        return machineRoom;
    }

    public void setMachineRoom(String machineRoom) {
        this.machineRoom = machineRoom;
    }

    public String getDbIpPort() {
        return dbIpPort;
    }

    public void setDbIpPort(String dbIpPort) {
        this.dbIpPort = dbIpPort;
    }

    public String getDatabaseEnglishName() {
        return databaseEnglishName;
    }

    public void setDatabaseEnglishName(String databaseEnglishName) {
        this.databaseEnglishName = databaseEnglishName;
    }

    public String getTableChineseName() {
        return tableChineseName;
    }

    public void setTableChineseName(String tableChineseName) {
        this.tableChineseName = tableChineseName;
    }

    public String getFieldEnglishName() {
        return fieldEnglishName;
    }

    public void setFieldEnglishName(String fieldEnglishName) {
        this.fieldEnglishName = fieldEnglishName;
    }

    public String getFieldChineseName() {
        return fieldChineseName;
    }

    public void setFieldChineseName(String fieldChineseName) {
        this.fieldChineseName = fieldChineseName;
    }

    public Double getTableSizeGB() {
        return tableSizeGB;
    }

    public void setTableSizeGB(Double tableSizeGB) {
        this.tableSizeGB = tableSizeGB;
    }

    public String getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(String dataCategory) {
        this.dataCategory = dataCategory;
    }

    public void setDataLevel(Integer dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(String isConnected) {
        this.isConnected = isConnected;
    }

    public String getDataClassification() {
        return dataClassification;
    }

    public void setDataClassification(String dataClassification) {
        this.dataClassification = dataClassification;
    }

    public String getDivisionOfLabor() {
        return divisionOfLabor;
    }

    public void setDivisionOfLabor(String divisionOfLabor) {
        this.divisionOfLabor = divisionOfLabor;
    }
}