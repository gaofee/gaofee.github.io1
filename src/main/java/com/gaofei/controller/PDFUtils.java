package com.gaofei.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.gaofei.domain.User;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * PDF工具类
 * @author 45466
 *
 */

public class PDFUtils {


	public static void YuLan(HttpServletResponse response, String file) throws Exception {
		response.setHeader("Content-Disposition", "inline;fileName=");
		FileInputStream iStream = new FileInputStream(file);
		IOUtils.copy(iStream, response.getOutputStream());
		iStream.close();
	}


	private static BaseFont getChinseFont() throws DocumentException, IOException {
		// TODO Auto-generated method stub

		String path="";

		if(System.getProperty("os.name").toLowerCase().equals("windows 10")){
			path="D:\\IdeaProjects\\jsp-demo\\src\\main\\resources\\huawen.ttf";
		}else{
			path="D:\\IdeaProjects\\jsp-demo\\src\\main\\resources\\huawen.ttf";
		}
		BaseFont bfChinese = BaseFont.createFont(path, BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
		return bfChinese;

	}


	/**
	 * 使用步骤
	 * @param args
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, DocumentException, IOException {
		User u1 = new User();
		User u2 = new User();
		User u3 = new User();
		u1.setUsername("gaofei");
		u1.setId(1);
		u2.setUsername("gaofei2");
		u2.setId(2);
		u3.setUsername("gaofei3");
		u3.setId(3);
		List<User> list = Arrays.asList(u1,u2,u3);
		Document document = new Document();
		document.open();
		PdfWriter pw = PDFUtils.createDoc(document, "D:\\IdeaProjects\\jsp-demo\\src\\main\\resources\\bb.pdf");
		PDFUtils.addTable(pw, new String[]{"姓名", "id"}, new String[]{"username", "id"}, list);
		document.close();
	}


		//第一步生成docment
	//第二步创建pdf第二个参数是pdf地址
	/**
	 * 获取文档对象
	 * @throws
	 * @throws
	 */
	public static PdfWriter createDoc(Document document ,String filePath) throws FileNotFoundException, DocumentException {

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        // 3.打开文档
        document.open();
        return writer;
	}


	/**
	 * 将pdf文件转换成图片
	 * @param pdfFile
	 * @param pngFile
	 * @return
	 */
	//pdf转图片，第一个是pdf地址，第二个是图片地址
	public static boolean pdf2png(String pdfFile,String pngFile) {

        System.out.println("开始pdf生成png图片");
        // 将pdf装图片 并且自定义图片得格式大小
        File file = new File(pdfFile);
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                ImageIO.write(image, "PNG", new File(pngFile));
                System.out.println("生成png文件：" + pngFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }





	/**
	 *   添加水印
	 * @param is  输入流
	 * @param os  输出流
	 * @param mainMark
	 * @param rootMark
	 * @throws DocumentException
	 * @throws IOException
	 */
    public static void markTxt(InputStream is, OutputStream os, String mainMark, String rootMark)
            throws DocumentException, IOException {
        markTxt(0.5f, 60, true, is, os, mainMark, rootMark);
    }

    //第三部添加水印
    /**
     *      加图片水印
     * @param iconPath
     * @param is
     * @param os
     * @param rootMark
     * @throws DocumentException
     * @throws IOException
     */
    //添加水印
		//第一个是盖章，第二个是读入流 new出来的，地址是pdf地址，第三个读出流，地址是生成加水印后的pdf地址
    public static void markImage( InputStream is, OutputStream os, String rootMark)
            throws DocumentException, IOException {
		String path="";
			if(System.getProperty("os.name").toLowerCase().equals("windows 10")){
				path="D:\\img\\gai.jpg";
			}else{
				path="/xiangmu/gai.jpg";
			}
        markImage(path, 0.5f, 60, true, is, os, rootMark);
    }
    /**
     *
     * @param alpha        透明度 0-1
     * @param degree    角度
     * @param isUnder    水印置于文本上/下
     * @param is        输入IO
     * @param os        输出IO
     * @param mainMark    主文本
     * @param rootMark    页脚文本
     */
    private static void markTxt(float alpha, int degree, boolean isUnder, InputStream is, OutputStream os,
            String mainMark, String rootMark) throws DocumentException, IOException {
        PdfReader reader = new PdfReader(is);
        PdfStamper stamper = new PdfStamper(reader, os);

        try {
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(alpha);

            BaseFont base = getChinseFont();

            PdfContentByte content;
            int total = reader.getNumberOfPages() + 1;
            for (int i = 1; i < total; i++) {
                if (isUnder) {
                    content = stamper.getUnderContent(i);
                } else {
                    content = stamper.getOverContent(i);
                }
                content.setGState(gs);
                content.beginText();
                content.setColorFill(BaseColor.BLUE);
                content.setFontAndSize(base, 50);
                content.setTextMatrix(70, 200);
                content.showTextAligned(Element.ALIGN_CENTER, mainMark, 300, 350, degree);

                content.setColorFill(BaseColor.GREEN);
                content.setFontAndSize(base, 18);
                content.showTextAligned(Element.ALIGN_CENTER, rootMark, 300, 300, 0);
                content.endText();

            }
        } finally {
            stamper.close();
            reader.close();
            is.close();
        }
    }



    /**
     *
     * @param iconPath     图标
     * @param alpha        透明度
     * @param degree    角度
     * @param isUnder    在内容下/上方加水印
     * @param is        输入IO
     * @param os        输出IO
     * @param rootMark    页脚文本描述
     */
    private static void markImage(String iconPath, float alpha, int degree, boolean isUnder, InputStream is,
            OutputStream os, String rootMark) throws DocumentException, IOException {
        PdfReader reader = new PdfReader(is);
        PdfStamper stamper = new PdfStamper(reader, os);
        try {
            BaseFont base =getChinseFont();

            PdfGState gs = new PdfGState();
            gs.setFillOpacity(alpha);

            PdfContentByte content;
            int total = reader.getNumberOfPages() + 1;
            for (int i = 1; i < total; i++) {
                if (isUnder) {
                    content = stamper.getUnderContent(i);
                } else {
                    content = stamper.getOverContent(i);
                }

                content.setGState(gs);
                content.beginText();

                Image image = Image.getInstance(iconPath);
                image.setAlignment(Image.LEFT | Image.TEXTWRAP);
                image.setRotationDegrees(degree);
                image.setAbsolutePosition(200, 200);
                image.scaleToFit(200, 200);

                content.addImage(image);
                content.setColorFill(BaseColor.BLACK);
                content.setFontAndSize(base, 8);
                content.showTextAligned(Element.ALIGN_CENTER, rootMark, 300, 300, 0);
                content.endText();
            }
        } finally {
            stamper.close();
            reader.close();
            is.close();
        }
    }




//    /**
//     *       向文章中写如内容
//     * @param document
//     * @param list
//     * @param fontName
//     * @param i
//     * @throws DocumentException
//     * @throws IOException
//     */
	public static void addContent(Document document, List<String> list, int i) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		Font myFont = getFont();


		myFont.setSize(i);

		for (String string : list) {
			Paragraph paragraph = new Paragraph(string, myFont);
			document.add(paragraph);
		}
	}

	public static Font getFont() throws IOException, DocumentException {

		Font myFont;
		String fontName="";

		// 获取操作系统
		  String os = System.getProperty("os.name");
		 Properties properties = new Properties();
		 properties.load(PDFUtils.class.getResourceAsStream("/config.properties"));
        if(!os.toLowerCase().startsWith("win")){ // linux 操作系统
        	fontName=properties.getProperty("linuxfontpath");
        }else{
        	fontName=properties.getProperty("winfontpath");
        }

		if(StringUtils.hasText(fontName)) {
			BaseFont bfChinese = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			 myFont = new Font(bfChinese);
		}else {
			BaseFont bfChinese = getChinseFont();
			 myFont = new Font(bfChinese);
		}
		return myFont;
	}



	/**
	 *
	 * @param document
	 * @param imgName
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */

	public static void addImg(Document document, String imgName, int x, int y, int width, int height) throws MalformedURLException, IOException, DocumentException  {
		// TODO Auto-generated method stub


		// 添加图片
		Image img;
			img = Image.getInstance(imgName);
			img.setAbsolutePosition(x, y);
			img.scaleAbsolute(width, height);
			document.add(img);

	}


	//第三步添加数据
//	/**
//	 * 添加表格
//	 * @param writer
//	 * @param strings
//	 * @param list2
//	 * @throws SecurityException
//	 * @throws NoSuchFieldException
//	 * @throws IllegalAccessException
//	 * @throws IllegalArgumentException
//	 * @throws IOException
//	 * @throws DocumentException
//	 */
	//第一个是pdf地址，第二个第三是table表头，和entity字段名,第三个是加入的list 添加数据后别忘了关闭docment
	public static PdfPTable addTable(PdfWriter writer, String[] titles, String[] props, List dataList) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, DocumentException, IOException {
		int x=50;
		int y=500;

		// TODO Auto-generated method stub
		PdfContentByte content = writer.getDirectContent();
		PdfPTable pTable = new PdfPTable(titles.length);

		BaseFont bfChinese = getChinseFont();
		 Font font = new Font(bfChinese);


		ArrayList<PdfPRow> rows = pTable.getRows();
		//第一行是标题
		PdfPCell[] cells = new PdfPCell[titles.length];
		for (int i = 0; i < cells.length; i++) {
			cells[i]=new PdfPCell(new Paragraph(titles[i],font));
		}
		rows.add(new PdfPRow(cells));

		// 中间其他的数据
		for (Object data : dataList) {
			// 存储每行的数组
			PdfPCell[] cells2 = new PdfPCell[titles.length];


			  for (int i = 0; i < cells2.length; i++) {
				  // 获取data当中某个属性数值

			  // 通过反射获取字段
				Field field = data.getClass().getDeclaredField(props[i]);
				if(field!=null  ) {
					//暴力打破封装
					field.setAccessible(true);
					if(field.get(data)!=null ) {
						// 设置内容
						cells2[i] = new PdfPCell(new Paragraph(field.get(data).toString(),font));
					}else {
						cells2[i] = new PdfPCell(new Paragraph("",font));
					}
				}else {
					cells2[i] = new PdfPCell(new Paragraph("",font));
				}

			  }
			rows.add(new PdfPRow(cells2));
		}
		pTable.setTotalWidth(500);
		pTable.writeSelectedRows(0, 6, x, y, content);
		return pTable;
	}
}
