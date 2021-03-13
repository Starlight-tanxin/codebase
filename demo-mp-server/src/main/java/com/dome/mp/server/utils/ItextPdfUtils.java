//package com.dome.mp.server.utils;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfSignatureAppearance;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.pdf.security.*;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.PrivateKey;
//import java.security.cert.Certificate;
//import java.util.Map;
//
///**
// * <p>description</p>
// *
// * @author TanXin
// * @date 2021/3/12 11:13
// */
//public class ItextPdfUtils {
//
//    private static Logger logger = LoggerFactory.getLogger(ItextPdfUtils.class);
//
//    private static String CHINA_TEX_INTER = "CHINA_TEX_INTER";  //中纺棉国际
//    private static String ME_SPIN_COTTON = "ME_SPIN_COTTON";   //中纺棉花
//
//    /**
//     * 生成pdf
//     *
//     * @param request
//     * @param root
//     * @param pdfName
//     * @param pngName
//     * @param docurx
//     * @param docury
//     * @return
//     * @throws TemplateException
//     * @throws IOException
//     * @throws Exception
//     */
//    public static ByteArrayOutputStream processPdf(HttpServletRequest request, Map root, String pdfName, String pngName
//            , Float docurx, Float docury) throws TemplateException, IOException, Exception {
//        String basePath = request.getSession().getServletContext().getRealPath("/");
//        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
//        //设置加载模板的目录
////        String ftlUrl = basePath + "/WEB-INF/view/ftl";
//        String ftlUrl = "templates/ftl";
//        logger.info("pdf模板路径:" + ftlUrl);
//        cfg.setDirectoryForTemplateLoading(new File(ftlUrl));
//        // 设置编码
//        cfg.setDefaultEncoding("UTF-8");
//        logger.info("从指定的模板目录中加载对应的模板文件");
//        // 从指定的模板目录中加载对应的模板文件
//        Template temp = cfg.getTemplate("" + pdfName + ".ftl");
//        root.put("basePath", basePath);
//        String fileName = basePath + "/WEB-INF/view/" + pdfName + System.currentTimeMillis() + ".html";
//        logger.info("生成HTML文件名：" + fileName);
//        File file = new File(fileName);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
//        temp.process(root, out);
//        String outputFileName = basePath + "/resources/template" + System.currentTimeMillis() + ".pdf";
//        String outputSignFileName = basePath + "/resources/template" + System.currentTimeMillis() + "sign.pdf";
//        logger.info("生成PDF文件名：" + outputFileName);
//        Document document = null;
//        if (docurx == null || docury == null) {
//            //默认设置
//            document = new Document(PageSize.A4); // 横向打印
//        } else {
//            document = new Document(new RectangleReadOnly(docurx, docury));
//        }
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFileName));
//        document.open();
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(fileName), StandardCharsets.UTF_8);
//        document.close();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        String DEST = outputFileName;
//        if (!StringUtils.isEmpty(pngName)) {
//            /*=============================电子签章 Start===========================================*/
//            String KEYSTORE = basePath + "/lib/pdf/zrong2.p12";
//            char[] PASSWORD = "chinatex".toCharArray();//keystory密码
//            String SRC = outputFileName;//原始pdf
//            DEST = outputSignFileName;//签名完成的pdf
//            String chapterPath = basePath + "/lib/pdf/" + pngName + ".png";//签章图片
//            String reason = "理由";
//            String location = "位置";
//            sign(new FileInputStream(SRC), new FileOutputStream(DEST),
//                    new FileInputStream(KEYSTORE), PASSWORD,
//                    reason, location, chapterPath);
//            /*=============================电子签章 Start==========================================*/
//        }
//        InputStream is = new FileInputStream(DEST);
//        int buf;
//        while ((buf = is.read()) != -1) {
//            baos.write(buf);
//        }
//        baos.flush();
//        is.close();
//        out.close();
//        writer.close();
//        file = new File(fileName);
//        file.delete();
//        file = new File(outputFileName);
//        file.delete();
//        file = new File(DEST);
//        file.delete();
//        return baos;
//    }
//
//
//    /**
//     * 在已经生成的pdf上添加电子签章，生成新的pdf并将其输出出来
//     *
//     * @param src
//     * @param dest
//     * @param p12Stream
//     * @param password
//     * @param reason
//     * @param location
//     * @param chapterPath
//     * @throws GeneralSecurityException
//     * @throws IOException
//     * @throws DocumentException
//     */
//    public static void sign(InputStream src  //需要签章的pdf文件路径
//            , OutputStream dest  // 签完章的pdf文件路径
//            , InputStream p12Stream, //p12 路径
//                            char[] password
//            , String reason  //签名的原因，显示在pdf签名属性中，随便填
//            , String location, String chapterPath) //签名的地点，显示在pdf签名属性中，随便填
//            throws GeneralSecurityException, IOException, DocumentException {
//        //读取keystore ，获得私钥和证书链
//        KeyStore ks = KeyStore.getInstance("PKCS12");
//        ks.load(p12Stream, password);
//        String alias = (String) ks.aliases().nextElement();
//        PrivateKey pk = (PrivateKey) ks.getKey(alias, password);
//        Certificate[] chain = ks.getCertificateChain(alias);
//
//        //下边的步骤都是固定的，照着写就行了，没啥要解释的
//        // Creating the reader and the stamper，开始pdfreader
//        PdfReader reader = new PdfReader(src);
//        //目标文件输出流
//        //创建签章工具PdfStamper ，最后一个boolean参数
//        //false的话，pdf文件只允许被签名一次，多次签名，最后一次有效
//        //true的话，pdf可以被追加签名，验签工具可以识别出每次签名之后文档是否被修改
//        PdfStamper stamper = PdfStamper.createSignature(reader, dest, '\0', null, false);
//        // 获取数字签章属性对象，设定数字签章的属性
//        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
//        appearance.setReason(reason);
//        appearance.setLocation(location);
//        //设置签名的位置，页码，签名域名称，多次追加签名的时候，签名预名称不能一样
//        //签名的位置，是图章相对于pdf页面的位置坐标，原点为pdf页面左下角
//        //四个参数的分别是，图章左下角x，图章左下角y，图章右上角x，图章右上角y
//        appearance.setVisibleSignature(new Rectangle(300, 600, 630, 500), 1, "sig1");
//        //读取图章图片，这个image是itext包的image
//        Image image = Image.getInstance(chapterPath);
//        appearance.setSignatureGraphic(image);
//        appearance.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
//        //设置图章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
//        appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
//
//        // 这里的itext提供了2个用于签名的接口，可以自己实现，后边着重说这个实现
//        // 摘要算法
//        ExternalDigest digest = new BouncyCastleDigest();
//        // 签名算法
//        ExternalSignature signature = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, null);
//        // 调用itext签名方法完成pdf签章CryptoStandard.CMS 签名方式，建议采用这种
//        MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
//    }
//}
