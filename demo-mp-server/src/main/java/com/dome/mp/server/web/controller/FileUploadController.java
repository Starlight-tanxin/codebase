package com.dome.mp.server.web.controller;

import com.dome.mp.server.pojo.dto.FileChunkDTO;
import com.dome.mp.server.utils.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2021/3/12 17:51
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/file/upload")
public class FileUploadController {


    @RequestMapping("/chunkUpload")
    public String uploadPost(FileChunkDTO chunk, HttpServletResponse response) {
        /**
         * 每一个上传块都会包含如下分块信息：
         * chunkNumber: 当前块的次序，第一个块是 1，注意不是从 0 开始的。
         * totalChunks: 文件被分成块的总数。
         * chunkSize: 分块大小，根据 totalSize 和这个值你就可以计算出总共的块数。注意最后一块的大小可能会比这个要大。
         * currentChunkSize: 当前块的大小，实际大小。
         * totalSize: 文件总大小。
         * identifier: 这个就是每个文件的唯一标示。
         * filename: 文件名。
         * relativePath: 文件夹上传的时候文件的相对路径属性。
         * 一个分块可以被上传多次，当然这肯定不是标准行为，但是在实际上传过程中是可能发生这种事情的，这种重传也是本库的特性之一。
         *
         * 根据响应码认为成功或失败的：
         * 200 文件上传完成
         * 201 文加快上传成功
         * 500 第一块上传失败，取消整个文件上传
         * 507 服务器出错自动重试该文件块上传
         */
        File file = new File(PathUtils.getFileDir(), chunk.getFilename());
        //第一个块,则新建文件
        if (chunk.getChunkNumber() == 1 && !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                response.setStatus(500);
                return "exception:createFileException";
            }
        }
        //进行写文件操作
        //将块文件写入文件中
        try (InputStream fos = chunk.getFile().getInputStream();
             RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            int len = -1;
            byte[] buffer = new byte[1024];
            raf.seek((chunk.getChunkNumber() - 1) * 1024 * 1024 * 5);
            while ((len = fos.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (chunk.getChunkNumber() == 1) {
                file.delete();
            }
            response.setStatus(507);
            return "exception:writeFileException";
        }
        if (chunk.getChunkNumber().equals(chunk.getTotalChunks())) {
            response.setStatus(200);
            return "over";
        } else {
            response.setStatus(201);
            return "ok";
        }
    }

}


