package com.cas;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: big文件 中剔除 D1文件的 手机号，剔除包含的数据
 * @author: xianglong
 * @create: 2024-12-16 16:28
 **/
public class ExcelTest {

    public static void main(String[] args) throws FileNotFoundException {
        String name = "A10";
        String num = "429961-5804";
        File big = new File("/Users/xianglong/Desktop/DID数据处理/提取数据/数据/浙中_结果-3659755-49416-out/"+name + "-" + num + ".xlsx");
        File D1 = new File("/Users/xianglong/Desktop/DID数据处理/第二次104W/D1.xlsx");
        File out = new File("/Users/xianglong/Desktop/DID数据处理/提取数据/数据/浙中_结果-3659755-49416-out/out/"+name+ ".xlsx");
        ExcelWriter excelWriter = EasyExcel.write(out).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "模板" + 7).build();
        FileInputStream bigFis = new FileInputStream(big);
        FileInputStream d1File = new FileInputStream(D1);
        AtomicInteger index = new AtomicInteger();
        Set<String> set = new HashSet<>();
        EasyExcel.read(bigFis, Td.class, new PageReadListener<Td>(dataList -> {
            // 先写小文件
            dataList.forEach(x -> {
                set.add(x.getMobileNo());
            });
        })).sheet(0).headRowNumber(1).doRead();

        AtomicInteger count = new AtomicInteger();
        //
        EasyExcel.read(d1File, Td.class, new PageReadListener<Td>(dataList -> {
            // 再筛选大文件
            dataList.forEach(x -> {
                if(set.contains(x.getMobileNo())) {
                    count.getAndIncrement();
                    System.out.println("重复手机号: " + x.getMobileNo());
                    set.remove(x.getMobileNo());
                }
            });
        })).sheet(0).headRowNumber(1).doRead();

        System.out.println("重复数据量: " + count);
        // 将 Set 转换为 List
        List<Td> list = new ArrayList<>();
        set.forEach(x -> list.add(new Td(x)));

        excelWriter.write(list, writeSheet);
        excelWriter.finish();
        System.out.println(index);
    }

    public static class Td {

        public Td(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public Td() {
        }

        private String mobileNo;

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }
    }

}
