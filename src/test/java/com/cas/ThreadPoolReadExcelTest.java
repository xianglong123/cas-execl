package com.cas;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: xianglong
 * @create: 2024-12-16 16:28
 **/
public class ThreadPoolReadExcelTest {

    private static final int THREAD_COUNT = 8; // 根据实际情况调整线程数


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        long startTime = System.currentTimeMillis();
        File file = new File("/Users/xianglong/Desktop/zmonth11.xlsx");
        AtomicInteger count = new AtomicInteger();
        EasyExcel.read(file, DicpDidScreenBO.class, new PageReadListener<DicpDidScreenBO>(dataList -> {
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            // 将数据处理任务提交给线程池
            dataList.forEach(x -> {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                    try {
////                        Thread.sleep(100L);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    count.getAndIncrement();
                }, executorService);
                futures.add(future);
            });
            // 等待所有任务完成
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            System.out.println(count);
        })).sheet(0).headRowNumber(1).doRead();
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
