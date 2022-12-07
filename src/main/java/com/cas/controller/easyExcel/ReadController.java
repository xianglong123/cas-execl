package com.cas.controller.easyExcel;

import com.alibaba.excel.EasyExcel;
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

import java.io.IOException;

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

}
