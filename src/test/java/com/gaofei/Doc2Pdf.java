package com.gaofei;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author : gaofee
 * @date : 10:59 2021/7/1
 * @码云地址 : https://gitee.com/itgaofee
 */

public class Doc2Pdf {

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Doc2Pdf.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        // 验证License
        if (!getLicense()) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File("D:\\IdeaProjects\\jsp-demo\\src\\main\\resources\\aaa.pdf");
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document("D:\\IdeaProjects\\jsp-demo\\src\\main\\resources\\aa.doc");
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换

            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
