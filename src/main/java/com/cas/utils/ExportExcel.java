package com.cas.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.cas.po.DicpEmptyCardWhiteBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
 *
 * @author ThinkGem
 * @version 2013-04-21
 */
@Slf4j
public class ExportExcel {
    public ExportExcel() {
    }

    public static MultipartFile exportExcelReturnFile(String filename, Class<?> pojoClass, Collection<?> dataSet) {
        ExportParams params = new ExportParams(filename, filename, ExcelType.XSSF);
        params.setStyle(ExcelStyleUtil.class);
        try (
             Workbook workbook = ExcelExportUtil.exportExcel(params, pojoClass ,dataSet);
            ){
            // 需要将workbook通过流对象转为MultipartFile，不然直接转为workbook的字节生成的文件无法打开
            FileItemFactory factory = new DiskFileItemFactory(16, null);
            FileItem fileItem = factory.createItem("textField", "text/plain", true, filename);
            OutputStream os = fileItem.getOutputStream();
            workbook.write(os);
            os.close();
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            return multipartFile;
        }catch (Exception e){

        }
        return null;
    }

    public static void main(String[] args) {
        List<DicpEmptyCardWhiteBO> list = new ArrayList<>();
            list.add(new DicpEmptyCardWhiteBO("20230621175717606026", "00000066666666666666", "15174304316", "测试商户三", "政务服务", "明月", LocalDateTime.now()));
            list.add(new DicpEmptyCardWhiteBO("20230621174256606021", "00000000005555555555", "15174304315", "测试商户二", "专用场景", "明月", LocalDateTime.now()));
            list.add(new DicpEmptyCardWhiteBO("20230621174240606020", "00000000044444444444", "15174304314", "测试商户四", "政务服务", "明月", LocalDateTime.now()));
            list.add(new DicpEmptyCardWhiteBO("20230621174220606019", "00000003333333333333", "15174304313", "测试商户三", "营业厅", "明月", LocalDateTime.now()));
            list.add(new DicpEmptyCardWhiteBO("20230621174159606018", "00000000000222222222", "15174304312", "测试商户一", "金融认证", "明月", LocalDateTime.now()));
            list.add(new DicpEmptyCardWhiteBO("20230621174138606017", "00000001111111111111", "15174304311", "测试商户二", "文旅服务", "明月", LocalDateTime.now()));
            list.add(new DicpEmptyCardWhiteBO("20230621173821606016", "00156134800000000000", "15174304300", "测试商户一", "访客系统", "明月", LocalDateTime.now()));
            list.add(new DicpEmptyCardWhiteBO("20230616120515602011", "1212", "12121212121", "试试", "文旅服务", "明月", LocalDateTime.now()));
        MultipartFile multipartFile = exportExcelReturnFile("123", DicpEmptyCardWhiteBO.class, list);

        File file = new File("/Users/xianglong/IdeaProjects/cas-execl/test.xlsx");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
