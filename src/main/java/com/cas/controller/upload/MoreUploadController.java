package com.cas.controller.upload;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @description:
 * @author: xianglong
 * @create: 2024-05-07 17:51
 **/
@Api(tags = "上传文件 相关功能")
@RequestMapping("/upload")
@RestController
public class MoreUploadController {

    @PostMapping("/1")
    public String handleFileUpload(MultipartFile[] files, RedirectAttributes redirectAttributes) {
        // 检查文件数组是否为空
        if (files == null || files.length == 0) {
            redirectAttributes.addFlashAttribute("message", "请至少选择一个文件上传！");
            return "redirect:/uploadStatus";
        }

        // 处理上传的文件
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; // 跳过空文件
            }
            // 执行文件处理逻辑，例如保存文件到磁盘
            // ...
        }

        redirectAttributes.addFlashAttribute("message", "所有文件上传成功！");
        return "上传成功";
    }

}
