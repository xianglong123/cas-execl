package com.cas.controller.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.cas.po.SimpleWriteData;
import com.cas.utils.Contexts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/12/7 11:09 上午
 * @desc
 */
@Api(tags = "[easy-excel] sheet数据写入")
@RequestMapping(Contexts.EASY_EXCEL_WRITE)
@RestController
public class WriteController {

    @ApiOperation(value = "写数据到单sheet中", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping("simpleWrite")
    public String simpleWrite(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;fileName=simpleWrite.xlsx");
        EasyExcel.write(response.getOutputStream(), SimpleWriteData.class).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("模板").doWrite(data());
        return "success";
    }

    @ApiOperation(value = "写数据到多个sheet中", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping("complexWrite")
    public String complexWrite(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;fileName=complexWrite.xlsx");
        ExcelWriter excelWriter = null;
        try {
            // 记录多个数据
            excelWriter = EasyExcel.write(response.getOutputStream(), SimpleWriteData.class).build();
            for (int i = 0; i < 2; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(data(), writeSheet);
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
        return "success";
    }


    private List<SimpleWriteData> data() {
        List<SimpleWriteData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SimpleWriteData data = new SimpleWriteData();
            data.setAge("24");
            data.setArea("4004100");
            data.setCountry("CHN");
            data.setIdCard("500237199907137975");
            data.setMobileNo("15811133324");
            data.setName("张三");
            data.setSex("男");
            list.add(data);
        }
        return list;
    }

}
