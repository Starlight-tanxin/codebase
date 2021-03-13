package com.dome.mp.server.web.controller;

import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2021/3/12 16:50
 */
//@WebServlet(name = "/fileUpload/one" , urlPatterns = "/fileUpload/one")
public class FileUploadOneServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        upload(req, resp);
    }

    public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> list = upload.parseRequest(request);
        for (FileItem item : list) {
            if (!item.isFormField()) {
                String fileName = item.getName();
                String realSavePath = "D:\\testFileUpload\\real\\";
                String tempSavePath = "D:\\testFileUpload\\temp\\";
                File realFile = new File(realSavePath + fileName);
                File tempFile = new File(tempSavePath + fileName);
                if (realFile.exists()) {//文件已经传输成功
                    System.out.println("文件已经存在，请不要重复上传");
                } else {
                    InputStream in = item.getInputStream();
                    long needSkipBytes = 0;
                    if (tempFile.exists()) {//续传
                        needSkipBytes = tempFile.length();
                    } else {//第一次传
                        tempFile.createNewFile();
                    }
                    System.out.println("跳过的字节数为：" + needSkipBytes);
                    in.skip(needSkipBytes);
                    RandomAccessFile tempRandAccessFile = new RandomAccessFile(tempFile, "rw");
                    tempRandAccessFile.seek(needSkipBytes);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    int count = 0;
                    while ((len = in.read(buffer)) > 0) {
                        tempRandAccessFile.write(buffer);
                        count++;
                    }
                    in.close();
                    tempRandAccessFile.close();
                    realFile.createNewFile();
                    if (fileCopy(tempFile, realFile)) {
                        tempFile.delete();
                    }
                }
            }
        }
    }

    private boolean fileCopy(File sourceFile, File targetFile) {
        boolean success = true;
        try {
            FileInputStream in = new FileInputStream(sourceFile);
            FileOutputStream out = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            success = false;
        } catch (IOException e) {
            success = false;
        }
        return success;
    }
}
