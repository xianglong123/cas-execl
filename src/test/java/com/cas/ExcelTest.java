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
        File big = new File("/Users/xianglong/Desktop/C完成的需求/统计/DID数据处理/20250429/20250430.xlsx");
        File directory = new File("/Users/xianglong/Desktop/C完成的需求/统计/DID数据处理/20250107/和包出行/jk");
        File out = new File("/Users/xianglong/Desktop/C完成的需求/统计/DID数据处理/20250429/20250430-out.xlsx");
        ExcelWriter excelWriter = EasyExcel.write(out).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "模板" + 7).build();
        FileInputStream bigFis = new FileInputStream(big);
//        FileInputStream d1File = new FileInputStream(single);
        AtomicInteger index = new AtomicInteger();
        Set<String> set = new HashSet<>();
        // 原始数据
        EasyExcel.read(bigFis, Td.class, new PageReadListener<Td>(dataList -> {
            // 先写小文件
            dataList.forEach(x -> {
                set.add(x.getMobileNo());
            });
        })).sheet(0).headRowNumber(0).doRead();

        // 单个文件进行去重复
//        EasyExcel.read(d1File, Td.class, new PageReadListener<Td>(dataList -> {
//            // 再筛选大文件
//            dataList.forEach(x -> {
//                if(set.contains(x.getMobileNo())) {
//                    count.getAndIncrement();
//                    System.out.println("重复手机号: " + x.getMobileNo());
//                    set.remove(x.getMobileNo());
//                }
//            });
//        })).sheet(0).headRowNumber(0).doRead();

        // 指定目录路径
        // 检查路径是否存在并且是一个目录
        if (directory.exists() && directory.isDirectory()) {
            AtomicInteger count = new AtomicInteger();
            // 获取目录中的所有文件和子目录
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    long start = System.currentTimeMillis();
                    EasyExcel.read(file, More.class, new PageReadListener<More>(dataList -> {
                        // 再筛选大文件
                        dataList.forEach(x -> {
                            if(set.contains(x.getMobileNo())) {
                                count.getAndIncrement();
                                System.out.println("重复手机号: " + x.getMobileNo());
                                set.remove(x.getMobileNo());
                            }
                        });
                    })).sheet(0).headRowNumber(1).doRead();
                    long end = System.currentTimeMillis();
                    System.out.println(file.getName() + " 重复数据量: " + count + " 耗时：" + (end - start));
                    count.set(0);
                }
            }
        } else {
            System.out.println("指定的路径不存在或不是一个目录");
        }

        // 将 Set 转换为 List
        List<Td> list = new ArrayList<>();
        set.forEach(x -> list.add(new Td(x)));

        excelWriter.write(list, writeSheet);
        excelWriter.finish();
        System.out.println(index);
    }

    public static class More {

        public More() {
        }

        private String seid;
        private String mobileNo;

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
    }

    public static class Td {

        public Td(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public Td() {
        }

        private String mobileNo;
        private String sf;

        public String getSf() {
            return sf;
        }

        public void setSf(String sf) {
            this.sf = sf;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }
    }

}
