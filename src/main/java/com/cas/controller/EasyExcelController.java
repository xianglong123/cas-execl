package com.cas.controller;

import com.cas.utils.DownloadExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/11/25 3:53 下午
 * @desc
 */
@Api(tags = "Easy-excel 相关功能")
@RestController("/easy/execl")
public class EasyExcelController {

    /**
     * 获取案例模版
     */
    @ApiOperation(value="Easy-execl获取简单模版", notes="s:简单模版 c:复杂模版", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping("/download/model-e")
    public void model(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "model-e.xlsx";
        DownloadExcelUtil.downAchievementTemplate(request, response, fileName);
    }





}
