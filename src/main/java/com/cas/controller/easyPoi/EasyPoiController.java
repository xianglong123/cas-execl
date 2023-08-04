package com.cas.controller.easyPoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.cas.po.WriteBO;
import io.swagger.annotations.Api;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @GetMapping("/download")
    public void download(HttpServletResponse response) {

        // 创建 Excel 导出参数
        ExportParams exportParams = new ExportParams("苏证通批量写卡配置文件模板-SZT", "注意:\n" +
                "1.所有字段内容格式均为文本格式;\n" +
                "2.单次批量查询手机号不超过10000条;", "苏证通批量写卡配置文件模板");

        // 导出 Excel
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Arrays.asList(new ExcelExportEntity("手机号", "mobileNo", 30), new ExcelExportEntity("PID", "pid", 30)), getData());
        LocalDate today = LocalDate.now();
        String name = today.minus(1, ChronoUnit.WEEKS).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + today.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "-Dial_Test";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + name + ".xlsx");
        // 保存到文件
        try {
            workbook.write(response.getOutputStream());
            System.out.println("Excel 导出成功");
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<WriteBO> getData() {
        List<WriteBO> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            WriteBO bo = new WriteBO();
            bo.setPid("11111");
            bo.setMobileNo("15811317734");
            bo.setReason("异常");
            bo.setWriteType("SZT");
            list.add(bo);
        }

        return list;
    }

    /**
     * 获取案例模版
     */
    @GetMapping("/model/{fileName}")
    public void model(@PathVariable("fileName") String fileName) {


        System.out.println("下载文件名 " + fileName);
    }

    /**
     * 导入数据
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/import")
    public void importExcel(@RequestParam("file") MultipartFile file) throws IOException {


    }


    /**
     * 导入数据
     *
     * @return
     */
    @PostMapping(value = "/local/read")
    public void localRead() throws IOException {

        File file = new File("/Users/xianglong/Desktop/123.xlsx");

        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(1);
        importParams.setStartRows(1);
        List<WriteBO> list = ExcelImportUtil.importExcel(file, WriteBO.class, importParams);
        list.forEach(System.out::println);
    }



}
