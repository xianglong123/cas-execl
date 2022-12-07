package com.cas.controller.easyExcel;

import com.cas.enums.ModelTypeEnum;
import com.cas.utils.Contexts;
import com.cas.utils.DownloadExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/11/25 3:53 下午
 * @desc
 */
@Api(tags = "[easy-excel] 模版下载")
@RequestMapping(Contexts.EASY_EXCEL_MODEL)
@RestController
public class ModelController {

    /**
     * 获取案例模版
     */
    @ApiOperation(value="获取模版", notes = "SIMPLE", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping("model")
    public void model(HttpServletRequest request, HttpServletResponse response,
                      @ApiParam(value = "模版类型：\nSIMPLE-简单模版\n FILL-填充模版\n COMPLEX-复杂模版，多sheet", required = true)  @RequestParam(value = "type") ModelTypeEnum type) {
        DownloadExcelUtil.downAchievementTemplate(request, response, type.getName());
    }

}
