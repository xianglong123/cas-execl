package com.cas.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/11/28 10:37 上午
 * @desc
 * Ext    MIME Type
 * .doc	    application/msword
 * .dot	    application/msword
 * .docx	application/vnd.openxmlformats-officedocument.wordprocessingml.document
 * .dotx	application/vnd.openxmlformats-officedocument.wordprocessingml.template
 * .docm	application/vnd.ms-word.document.macroEnabled.12
 * .dotm	application/vnd.ms-word.template.macroEnabled.12
 * .xls	    application/vnd.ms-excel
 * .xlt	    application/vnd.ms-excel
 * .xla	    application/vnd.ms-excel
 * .xlsx	application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
 * .xltx	application/vnd.openxmlformats-officedocument.spreadsheetml.template
 * .xlsm	application/vnd.ms-excel.sheet.macroEnabled.12
 * .xltm	application/vnd.ms-excel.template.macroEnabled.12
 * .xlam	application/vnd.ms-excel.addin.macroEnabled.12
 * .xlsb	application/vnd.ms-excel.sheet.binary.macroEnabled.12
 * .ppt	    application/vnd.ms-powerpoint
 * .pot	    application/vnd.ms-powerpoint
 * .pps	    application/vnd.ms-powerpoint
 * .ppa	    application/vnd.ms-powerpoint
 * .pptx	application/vnd.openxmlformats-officedocument.presentationml.presentation
 * .potx	application/vnd.openxmlformats-officedocument.presentationml.template
 * .ppsx	application/vnd.openxmlformats-officedocument.presentationml.slideshow
 * .ppam	application/vnd.ms-powerpoint.addin.macroEnabled.12
 * .pptm	application/vnd.ms-powerpoint.presentation.macroEnabled.12
 * .potm	application/vnd.ms-powerpoint.presentation.macroEnabled.12
 * .ppsm	application/vnd.ms-powerpoint.slideshow.macroEnabled.12
 */
public class DownloadExcelUtil {
    private static final Logger log = LoggerFactory.getLogger(DownloadExcelUtil.class);


    public static void downAchievementTemplate(
            HttpServletRequest request,
            HttpServletResponse response,
            String fileName
    ) {
        ServletOutputStream out;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {
            InputStream inputStream = DownloadExcelUtil.class.getClassLoader().getResourceAsStream(fileName);
            String userAgent = request.getHeader("User-Agent");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String((fileName).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            response.setHeader("Content-disposition", "attachment;fileName=" + fileName);
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[1024];
            while ((b = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, b);
            }
            inputStream.close();

            if (out != null) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            log.error("文件下载失败，异常：{}", e.getMessage());
            throw new RuntimeException("文件下载失败");
        }
    }

}
