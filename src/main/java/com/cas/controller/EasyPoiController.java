package com.cas.controller;

import com.cas.utils.ExcelUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/11/25 3:53 下午
 * @desc
 */
@Api(tags = "Easy-poi 相关功能")
@RequestMapping("/easy/poi")
@RestController
public class EasyPoiController {

    /**
     * 获取案例模版
     */
    @GetMapping("/model/{fileName}")
    public void model(@PathVariable("fileName") String fileName) {
        System.out.println("下载文件名 " + fileName);
    }

    /**
     * 导入数据
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/import")
    public void importExcel(@RequestParam("file") MultipartFile file) throws IOException {
    }






}
