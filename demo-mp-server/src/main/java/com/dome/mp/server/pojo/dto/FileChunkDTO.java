package com.dome.mp.server.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2021/3/12 17:52
 */
@Getter
@Setter
public class FileChunkDTO implements java.io.Serializable {

    private static final long serialVersionUID = -989034223739137587L;
    /**
     * 当前文件块，从1开始
     */
    private Integer chunkNumber;
    /**
     * 分块大小
     */
    private Long chunkSize;
    /**
     * 当前分块大小
     */
    private Long currentChunkSize;
    /**
     * 总大小
     */
    private Long totalSize;
    /**
     * 文件标识
     */
    private String identifier;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 总块数
     */
    private Integer totalChunks;

    /**
     * 二进制文件
     */
    private MultipartFile file;

}
