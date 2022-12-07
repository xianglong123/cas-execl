package com.cas.controller.easyExcel;

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
    @ApiOperation(value="Easy-execl获取简单模版", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping("model")
    public void model(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "simple.xlsx";
        DownloadExcelUtil.downAchievementTemplate(request, response, fileName);
    }

}
