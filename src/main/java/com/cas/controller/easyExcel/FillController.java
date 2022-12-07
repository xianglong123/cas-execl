package com.cas.controller.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.cas.enums.ModelTypeEnum;
import com.cas.po.SimpleWriteData;
import com.cas.utils.Contexts;
import com.cas.utils.DownloadExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/12/7 11:09 上午
 * @desc
 */
@Api(tags = "[easy-excel] sheet数据回填")
@RequestMapping(Contexts.EASY_EXCEL_FILL)
@RestController
public class FillController {

    /**
     * 获取案例模版
     */
    @ApiOperation(value="简单填充模版", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping("simpleFill")
    public void simpleFill(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;fileName=simpleFill.xlsx");
        InputStream inputStream = DownloadExcelUtil.class.getClassLoader().getResourceAsStream(ModelTypeEnum.FILL.getName());
        EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).sheet().doFill(getSimpleWriteData());
    }

    /**
     * 获取案例模版
     */
    @ApiOperation(value="简单填充模版", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping("simpleFillList")
    public void simpleFillList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;fileName=fillList.xlsx");
        InputStream inputStream = DownloadExcelUtil.class.getClassLoader().getResourceAsStream(ModelTypeEnum.FILL_LIST.getName());
        EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).sheet().doFill(this::data);
    }

    private SimpleWriteData getSimpleWriteData() {
        SimpleWriteData data = new SimpleWriteData();
        data.setAge("24");
        data.setArea("4004100");
        data.setCountry("CHN");
        data.setIdCard("500237199907137975");
        data.setMobileNo("15811133324");
        data.setName("张三");
        data.setSex("男");
        return data;
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
