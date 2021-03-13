//package com.dome.mp.server.web.controller;
//
//import com.dome.mp.server.utils.PdfUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//
///**
// * <p>description</p>
// *
// * @author TanXin
// * @date 2021/3/12 10:07
// */
//@Slf4j
//@Validated
//@RestController
//@RequestMapping("/demo/pdf")
//public class PdfCreateController {
//
//
//    @GetMapping("/test")
//    public void test(HttpServletRequest request, HttpServletResponse response, String path) {
//        try {
//            OutputStream outputStream = response.getOutputStream();
//            PdfUtils.createPdfByUrl(outputStream, path);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
