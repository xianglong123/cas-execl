package com.cas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberFormatter {

    public static void main(String[] args) {
        // 源文件路径和目标文件路径
        String sourceFilePath = "/Users/xianglong/Desktop/B处理中的需求/DID数据处理/20250107/韩晴60W DID数据/2222.txt";
        String targetFilePath = "/Users/xianglong/Desktop/B处理中的需求/DID数据处理/20250107/韩晴60W DID数据/3333.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(targetFilePath))) {

            List<String> phoneNumbers = new ArrayList<>();
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                phoneNumbers.add("\"" + line.trim() + "\"");
                count++;

                if (count == 500) {
                    // 当收集到500个号码时，写入文件并重置列表
                    writer.write(String.join(" ", phoneNumbers));
                    writer.newLine();
                    writer.newLine();
                    phoneNumbers.clear();
                    count = 0;
                }
            }

            // 如果还有未处理的号码，也写入文件
            if (!phoneNumbers.isEmpty()) {
                writer.write(String.join(" ", phoneNumbers));
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}