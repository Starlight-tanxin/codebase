//package com.dome.mp.server.utils;
//
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.BaseFont;
//import org.xhtmlrenderer.pdf.ITextFontResolver;
//import org.xhtmlrenderer.pdf.ITextRenderer;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//
///**
// * <p>description</p>
// *
// * @author TanXin
// * @date 2021/3/12 10:37
// */
//public class PdfUtils {
//
//    /**
//     * 通过html的字符串转pdf
//     *
//     * @param out
//     * @param html
//     * @throws IOException
//     * @throws DocumentException
//     */
//    public static void createPdfByHtml(OutputStream out, String html) throws IOException, DocumentException {
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocumentFromString(html);
//        // 解决中文支持问题
//        ITextFontResolver fontResolver = renderer.getFontResolver();
//        fontResolver.addFont("c:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//        renderer.layout();
//        renderer.createPDF(out);
//    }
//
//    /**
//     * 通过html的文件路径转pdf
//     *
//     * @param out
//     * @param htmlFilePath
//     * @throws IOException
//     * @throws DocumentException
//     */
//    public static void createPdfByUrl(OutputStream out, String htmlFilePath) throws IOException, DocumentException {
//        ITextRenderer renderer = new ITextRenderer();
//        String url = new File(htmlFilePath).toURI().toURL().toString();
//        renderer.setDocument(url);
//        // 解决中文支持问题
//        ITextFontResolver fontResolver = renderer.getFontResolver();
//        fontResolver.addFont("c:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//        //解决图片的相对路径问题
//        //renderer.getSharedContext().setBaseURL("http://localhost:8080");//file:/e:/
//        renderer.layout();
//        renderer.createPDF(out);
//    }
//}
