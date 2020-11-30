package com.dome.mp.server.utils.bean;

import okhttp3.MediaType;

import java.io.File;
import java.util.Map;

/**
 * describe:
 *
 * @author: TanXin
 * @date: 2020/4/30 9:09
 */
public class UploadDTO {

    private Map<String, String> otherData;

    private String fileKeyName;

    private File file;

    private MediaType mediaType;

    public Map<String, String> getOtherData() {
        return otherData;
    }

    public void setOtherData(Map<String, String> otherData) {
        this.otherData = otherData;
    }

    public String getFileKeyName() {
        return fileKeyName;
    }

    public void setFileKeyName(String fileKeyName) {
        this.fileKeyName = fileKeyName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
