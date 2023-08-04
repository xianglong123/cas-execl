package com.cas.controller.easyPoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.cas.po.WriteBO;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelExporter {
    public static void main(String[] args) {
        List<WriteBO> userList = getUserList(); // 假设你已经有一个 User 对象列表

        // 创建 Excel 导出参数
        ExportParams exportParams = new ExportParams("苏证通批量写卡配置文件模板-SZT", "注意:\n" +
                "1.所有字段内容格式均为文本格式;\n" +
                "2.单次批量查询手机号不超过10000条;","苏证通批量写卡配置文件模板");

        // 导出 Excel
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, WriteBO.class, userList);



        // 保存到文件
        try {
            FileOutputStream fos = new FileOutputStream("/Users/xianglong/Desktop/123.xlsx");
            workbook.write(fos);
            fos.close();
            System.out.println("Excel 导出成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<WriteBO> getUserList() {
        // TODO: 返回一个含有用户数据的列表
        List<WriteBO> userList = new ArrayList<>();
        userList.add(new WriteBO("Alice", "25"));
        userList.add(new WriteBO("Bob", "30"));
        return userList;
    }
}

