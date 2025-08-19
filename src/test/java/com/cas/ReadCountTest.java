package com.cas;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
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
 * @description: 读取excel并展示有多少行数据
 * @author: xianglong
 * @create: 2025-01-21 17:35
 **/
public class ReadCountTest {

    private static final String path = "/Users/xianglong/Desktop/B处理中的需求/DID数据处理/20250107/和包出行/2024";

    public static void main(String[] args) {
        // 指定目录路径
        File directory = new File(path);

        // 检查路径是否存在并且是一个目录
        if (directory.exists() && directory.isDirectory()) {
            // 获取目录中的所有文件和子目录
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    System.out.println("当前文件：" + file.getName());
                    // 创建一个监听器来统计行数
                    RowCountListener listener = new RowCountListener();

                    // 读取Excel文件
                    EasyExcel.read(file.getAbsoluteFile(), listener).sheet().doRead();

                    // 输出行数
                    System.out.println(file.getName() + " 的行数: " + listener.getRowCount());
                }
            }
        } else {
            System.out.println("指定的路径不存在或不是一个目录");
        }
    }

    static class RowCountListener extends AnalysisEventListener<Object> {
        private int rowCount = 0;

        @Override
        public void invoke(Object data, AnalysisContext context) {
            rowCount++;
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            // 所有数据解析完成后的操作
        }

        public int getRowCount() {
            return rowCount;
        }
    }

}
