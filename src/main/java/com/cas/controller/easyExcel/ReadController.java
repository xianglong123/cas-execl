package com.cas.controller.easyExcel;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SM4;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cas.listener.ErrQueryDeatilService;
import com.cas.listener.ReadDataListener;
import com.cas.po.SimpleReadData;
import com.cas.service.DicpUserOpenServiceImpl;
import com.cas.utils.Contexts;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Resource
    private ErrQueryDeatilService queryDeatilService;

    @Resource
    private DicpUserOpenServiceImpl dicpUserOpenService;


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
        File file = new File("/Users/xianglong/Desktop/2222.xlsx");
        File out = new File("/Users/xianglong/Desktop/3333.xlsx");
        ExcelWriter excelWriter = EasyExcel.write(out, Demo.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "模板" + 7).build();
        FileInputStream fis = new FileInputStream(file);
        AtomicInteger index = new AtomicInteger();
        List<Demo> list = new ArrayList<>();
        EasyExcel.read(fis, Demo.class, new PageReadListener<Demo>(dataList -> {
            dataList.forEach(x -> {
                try {

                    x.setTime(SM4.decryptStr(x.getTime()));
                } catch (Exception e) {
                    System.out.println("有问题：" + x.getTime());
//                    throw new RuntimeException(e);
                }
            });
            list.addAll(dataList);
        })).sheet(0).headRowNumber(1).doRead();

        excelWriter.write(list, writeSheet);
        excelWriter.finish();
        System.out.println(index);
        return "success";
    }

    @ApiOperation(value="[SIMPLE]读取文档中数据", notes = "采用model文档上传解析",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @PostMapping("ss")
    public String ssss(@RequestBody De de) {
        System.out.println(de.getTm());
        return "";
    }

    class De {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime tm;

        public LocalDateTime getTm() {
            return tm;
        }

        public void setTm(LocalDateTime tm) {
            this.tm = tm;
        }
    }

    private String getCardType(String seid) {
        String versionType = seid.substring(6, 8);
        // 2武汉天喻/3江西捷德/4东信和平/5大唐/7东京握奇/8恒宝/9北京华虹/C楚天龙
        switch (versionType) {
            case "02" : return "武汉天喻";
            case "03" : return "江西捷德";
            case "04" : return "东信和平";
            case "05" : return "大唐";
            case "07" : return "东京握奇";
            case "08" : return "恒宝";
            case "09" : return "北京华虹";
            case "0C" : return "楚天龙";
        }
        return "";
    }


    public static class Demo {

        private String time;
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;
        private String g;
        private String h;
        private String i;
        private String j;
        private String k;
        private String l;
        private String m;
        private String n;
        private String o;
        private String p;
        private String q;
        private String r;
        private String s;
        private String t;
        private String u;
        private String v;
        private String w;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getE() {
            return e;
        }

        public void setE(String e) {
            this.e = e;
        }

        public String getF() {
            return f;
        }

        public void setF(String f) {
            this.f = f;
        }

        public String getG() {
            return g;
        }

        public void setG(String g) {
            this.g = g;
        }

        public String getH() {
            return h;
        }

        public void setH(String h) {
            this.h = h;
        }

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getJ() {
            return j;
        }

        public void setJ(String j) {
            this.j = j;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getL() {
            return l;
        }

        public void setL(String l) {
            this.l = l;
        }

        public String getM() {
            return m;
        }

        public void setM(String m) {
            this.m = m;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getO() {
            return o;
        }

        public void setO(String o) {
            this.o = o;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public String getW() {
            return w;
        }

        public void setW(String w) {
            this.w = w;
        }
    }

}
