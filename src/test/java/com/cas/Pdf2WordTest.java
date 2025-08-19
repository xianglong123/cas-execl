package com.cas;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

/**
 * @description:
 * @author: xianglong
 * @create: 2025-02-08 10:43
 **/
public class Pdf2WordTest {


    private static final String basePath = "/Users/xianglong/Desktop/";
    private static final String pdfPath = basePath + "123.pdf";
    private static final String wordPath = basePath + "out.docx";

    public static void main(String[] args) {

        Document pdfDocument = new Document(pdfPath);
        pdfDocument.save(wordPath, SaveFormat.DocX);
        System.out.println("PDF to DOCX conversion performed successfully.");


    }

}
