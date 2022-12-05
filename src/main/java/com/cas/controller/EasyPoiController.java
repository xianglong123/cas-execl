package com.cas.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/11/25 3:53 下午
 * @desc
 */
@Api(tags = "Easy-poi 相关功能")
@RestController("/easy/poi")
public class EasyPoiController {

    /**
     * 获取案例模版
     */
    @GetMapping("/download/model-p")
    public void model() {



    }



}
