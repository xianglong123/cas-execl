package com.cas.controller.easyExcel;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SM4;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.cas.listener.ReadDataListener;
import com.cas.po.SimpleReadData;
import com.cas.utils.Contexts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/12/7 11:09 上午
 * @desc
 */
@Api(tags = "[easy-excel] sheet读取")
@RequestMapping(Contexts.EASY_EXCEL_READ)
@RestController
public class ReadController {



    @ApiOperation(value="[SIMPLE]读取文档中数据", notes = "采用model文档上传解析",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @PutMapping("simpleRead")
    public String simpleRead(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), SimpleReadData.class, new ReadDataListener()).sheet().headRowNumber(2).doRead();
        return "success";
    }

    @ApiOperation(value="[COMPLEX]读取文档中数据", notes = "采用model文档上传解析",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @PutMapping("complexRead")
    public String complexRead(MultipartFile file) throws IOException {
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(file.getInputStream()).build();
            ReadSheet sheet0 = EasyExcel.readSheet(0).head(SimpleReadData.class).registerReadListener(new ReadDataListener()).headRowNumber(2).build();
            ReadSheet sheet1 = EasyExcel.readSheet(1).head(SimpleReadData.class).registerReadListener(new ReadDataListener()).headRowNumber(2).build();
            excelReader.read(sheet0, sheet1);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
        return "success";
    }


    @ApiOperation(value="[SIMPLE]读取文档中数据", notes = "采用model文档上传解析",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @PutMapping("simpleReadSzt")
    public String simpleReadSzt() throws FileNotFoundException {
        cn.hutool.crypto.symmetric.SM4 SM4 = new SM4(SecureUtil.decode(""));
        File file = new File("/Users/xianglong/Desktop/20230400.xlsx");
        FileInputStream fis = new FileInputStream(file);
        AtomicInteger index = new AtomicInteger();
        EasyExcel.read(fis, Szt.class, new PageReadListener<Szt>(dataList -> {
            for (Szt demoData : dataList) {
                index.getAndIncrement();
                System.out.println(SM4.decryptStr(demoData.getMobileNo())+"\t"+demoData.getCreateTime());
//                System.out.println(demoData);
            }
        })).sheet().doRead();
        System.out.println(index);
        return "success";
    }


    public static class Szt {

        private String id;

        private String mobileNo;

        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

}
