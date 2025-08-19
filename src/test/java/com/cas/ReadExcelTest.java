package com.cas;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cas.po.DatabaseMetadataBO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: xianglong
 * @create: 2024-12-16 16:28
 **/
public class ReadExcelTest {

    public static void main(String[] args) throws FileNotFoundException {
        simCardProcess();
    }

    private static String simCardProcess() throws FileNotFoundException {
        File file = new File("/Users/xianglong/Desktop/1111.xlsx");
        File out = new File("/Users/xianglong/Desktop/3333.xlsx");
        ExcelWriter excelWriter = EasyExcel.write(out, DatabaseMetadataBO.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "模板" + 7).build();
        FileInputStream fis = new FileInputStream(file);
        AtomicInteger index = new AtomicInteger();
        List<DatabaseMetadataBO> list = new ArrayList<>();
        EasyExcel.read(fis, DatabaseMetadataBO.class, new PageReadListener<DatabaseMetadataBO>(dataList -> {
            dataList.forEach(x -> {
                x.setDataCategory(x.getFullClassification());
                x.setDataLevel(x.getDataLevel());
            });
            list.addAll(dataList);
        })).sheet(0).headRowNumber(1).doRead();

        excelWriter.write(list, writeSheet);
        excelWriter.finish();
        System.out.println(index);
        return "success";
    }

}
