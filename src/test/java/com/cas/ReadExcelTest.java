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
 * @description:
 * @author: xianglong
 * @create: 2024-12-16 16:28
 **/
public class ReadExcelTest {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        File file = new File("/Users/xianglong/Desktop/zmonth11.xlsx");
        AtomicInteger count = new AtomicInteger();
        EasyExcel.read(file, DicpDidScreenBO.class, new PageReadListener<DicpDidScreenBO>(dataList -> {
            // 先写小文件
            dataList.forEach(x -> {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count.getAndIncrement();
            });
            System.out.println(count);
        })).sheet(0).headRowNumber(0).doRead();
        long enTime = System.currentTimeMillis();
        System.out.println("总用耗时: " + (enTime - startTime));

    }

    public static class DicpDidScreenBO {

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

}
