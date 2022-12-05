package com.cas.controller;

import cn.hutool.core.util.ZipUtil;
import com.cas.utils.ZipUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/11/28 11:25 上午
 * @desc
 */
@Api(tags = "文件压缩/解压")
@RequestMapping("zip")
@RestController
public class ZipController {

    /**
     * 压缩文件
     */
    @ApiOperation(value="压缩单个文件", notes="...")
    @PostMapping("compressionOne")
    public void compressionOne(HttpServletRequest request,
                            HttpServletResponse response,
                            @ApiParam(value = "上传文件", required = true) @RequestParam(value = "file") MultipartFile multipartFile
                            ) throws IOException {
        ZipUtils.toZip(request, response, Collections.singletonList(multipartFile));
    }

    /**
     * 压缩文件
     */
    @ApiOperation(value="压缩多个文件", notes="...")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "压缩多个文件", paramType = "formData", allowMultiple = true, required = true, dataType = "file")
    })
    @PostMapping("compressionMore")
    public void compressionMore(HttpServletRequest request,
                               HttpServletResponse response,
                               @ApiParam(value = "上传文件", required = true) @RequestParam(value = "files") List<MultipartFile> multipartFiles
    ) throws IOException {
        ZipUtils.toZip(request, response, multipartFiles);
    }

    /**
     * 解压文件
     */
    @PostMapping("decompression")
    public String decompression(HttpServletRequest request,
                              HttpServletResponse response,
                              @ApiParam(value = "上传文件", required = true) @RequestParam(value = "file")MultipartFile multipartFile
    ) {
        return "自己解压，谢谢。还想我给你写功能？？？？";
    }

}
