package com.dome.mp.server.utils.net;

import cn.hutool.core.util.StrUtil;
import com.dome.mp.server.utils.bean.UploadDTO;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * describe:
 *
 * @author: TanXin
 * @date: 2020/4/2 16:59
 */
public class OkHttpUtils {

    private static OkHttpClient client = OkHttpClientEnum.OK_HTTP_CLIENT.getInstance();


    public static final MediaType JSON = MediaType.parse("application/json");
    public static final MediaType XML = MediaType.parse("text/xml");
    public static final MediaType IMG_JPEG = MediaType.parse("image/jpeg");
    public static final MediaType AUDIO_MP3 = MediaType.parse("audio/mp3");

    static Request.Builder builderHeader(Map<String, String> headerMap) {
        Request.Builder requestBuilder = new Request.Builder();
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }
        return requestBuilder;
    }


    private static String httpExecute(Request request) {
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (response.isSuccessful() && responseBody != null) {
                String result = responseBody.string();
                responseBody.close();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 参数为空时返回 空字符串
     *
     * @param params query参数
     * @return 查询参数字符串
     */
    private static String paramsMap2QueryString(Map<String, String> params) {
        if (params == null || params.isEmpty()) return "";
        int i = 0;
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (i == 0) {
                queryString.append("?");
                i++;
            } else {
                queryString.append("&");
            }
            queryString.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return queryString.toString();
    }

    private static FormBody getFormBody(Map<String, String> params) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                bodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        return bodyBuilder.build();
    }

    /**
     * get 请求
     *
     * @param api       地址
     * @param params    参数
     * @param headerMap 自定义头
     * @return 返回的数据
     */
    public static String get(String api, Map<String, String> params, Map<String, String> headerMap) {
        Request.Builder requestBuilder = builderHeader(headerMap);
        Request request = requestBuilder.url(api + paramsMap2QueryString(params)).build();
        return httpExecute(request);
    }

    /**
     * get 请求-无参数
     *
     * @param api       请求地址
     * @param headerMap 请求头
     * @return 返回数据
     */
    public static String getNoQueryParam(String api, Map<String, String> headerMap) {
        Request.Builder requestBuilder = builderHeader(headerMap);
        Request request = requestBuilder.url(api).build();
        return httpExecute(request);
    }


    /**
     * put json参数请求
     *
     * @param api       请求地址
     * @param json      json字符串
     * @param headerMap 请求头设置
     * @return 返回的数据
     */
    public static String putJson(String api, String json, Map<String, String> headerMap) {
        RequestBody body = RequestBody.create(OkHttpUtils.JSON, json);
        Request.Builder requestBuilder = builderHeader(headerMap);
        requestBuilder.url(api);
        Request request = requestBuilder.put(body).build();
        return httpExecute(request);
    }


    /**
     * put form-data参数请求
     *
     * @param api       请求的地址
     * @param params    参数
     * @param headerMap 请求头设置
     * @return 返回的数据
     */
    public static String putFormData(String api, Map<String, String> params, Map<String, String> headerMap) {
        Request.Builder requestBuilder = builderHeader(headerMap);
        requestBuilder.url(api);
        Request request = requestBuilder.put(getFormBody(params)).build();
        return httpExecute(request);
    }

    /**
     * post json 请求
     *
     * @param api       地址
     * @param json      json数据
     * @param headerMap 自定义头
     * @return 返回的数据
     */
    public static String postJson(String api, String json, Map<String, String> headerMap) {
        RequestBody body = RequestBody.create(OkHttpUtils.JSON, json);
        Request.Builder requestBuilder = builderHeader(headerMap);
        requestBuilder.url(api);
        Request request = requestBuilder.post(body).build();
        return httpExecute(request);
    }

    /**
     * delete query-param 请求
     *
     * @param api       请求地址
     * @param params    query参数
     * @param headerMap 请求头
     * @return 返回的数据
     */
    public static String deleteQueryParam(String api, Map<String, String> params, Map<String, String> headerMap) {
        Request.Builder requestBuilder = builderHeader(headerMap);
        requestBuilder.url(api + paramsMap2QueryString(params));
        Request request = requestBuilder.delete(new FormBody.Builder().build()).build();
        return httpExecute(request);
    }

    /**
     * post form-data 请求
     *
     * @param api       地址
     * @param params    参数
     * @param headerMap 自定义头
     * @return 返回的数据
     */
    public static String postFormData(String api, Map<String, String> params, Map<String, String> headerMap) {
        Request.Builder requestBuilder = builderHeader(headerMap);
        requestBuilder.url(api);
        Request request = requestBuilder.post(getFormBody(params)).build();
        return httpExecute(request);
    }

    /**
     * post form-data 请求
     *
     * @param api       地址
     * @param params    query-param 参数
     * @param headerMap 自定义头
     * @return 返回的数据
     */
    public static String postQueryParam(String api, Map<String, String> params, Map<String, String> headerMap) {
        Request.Builder requestBuilder = builderHeader(headerMap);
        requestBuilder.url(api + paramsMap2QueryString(params));
        Request request = requestBuilder.post(new FormBody.Builder().build()).build();
        return httpExecute(request);
    }

    /**
     * post xml 上报请求
     *
     * @param api       地址
     * @param xmlStr    xml字符串数据
     * @param headerMap 请求头设置
     * @return 返回的数据
     */
    public static String postXml(String api, String xmlStr, Map<String, String> headerMap) {
        RequestBody body = RequestBody.create(OkHttpUtils.XML, xmlStr);
        Request.Builder requestBuilder = builderHeader(headerMap);
        requestBuilder.url(api);
        Request request = requestBuilder.post(body).build();
        return httpExecute(request);
    }

    /**
     * 上传的图片name 默认data
     * 本上传接口为小钴人脸设备的专用接口
     *
     * @param uploadUrl 上传地址
     * @param file      需要上传的文件
     * @param fileName  文件名
     * @param json      唯一可带的数据 默认名称json
     * @param headerMap 自定义请求头
     * @return 返回的数据
     */
    public static String uploadFile(String uploadUrl, File file, String fileName, String json, Map<String, String> headerMap) {
        RequestBody fileBody = RequestBody.create(OkHttpUtils.IMG_JPEG, file);
        //定义请求体，前面三个为表单中的string类型参数，第四个为需要上传的文件
        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("json", json)
                .addFormDataPart("data", fileName, fileBody)
                .build();
        Request.Builder requestBuilder = builderHeader(headerMap);
        requestBuilder.url(uploadUrl);
        Request request = requestBuilder.post(mBody).build();
        return httpExecute(request);
    }

    /**
     * 上传的图片
     *
     * @param uploadUrl 上传地址
     * @param uploadDTO {@link UploadDTO} 文件上传的对象 file fileKeyName mediaType 不能为空
     * @param headerMap 自定义请求头
     * @return 返回的数据
     */
    public static String newUploadFile(String uploadUrl, UploadDTO uploadDTO, Map<String, String> headerMap) {
        if (uploadDTO == null || StrUtil.isEmpty(uploadDTO.getFileKeyName()) || uploadDTO.getFile() == null || uploadDTO.getMediaType() == null) {
            throw new RuntimeException("必要参数不能为空，否则不能上传");
        }
        RequestBody fileBody = RequestBody.create(uploadDTO.getMediaType(), uploadDTO.getFile());
        MultipartBody.Builder bodyBuild = new MultipartBody.Builder();
        bodyBuild.setType(MultipartBody.FORM);
        bodyBuild.addFormDataPart(uploadDTO.getFileKeyName(), uploadDTO.getFile().getName(), fileBody);
        if (uploadDTO.getOtherData() != null && !uploadDTO.getOtherData().isEmpty()) {
            uploadDTO.getOtherData().entrySet().forEach(entry -> {
                bodyBuild.addFormDataPart(entry.getKey(), entry.getValue());
            });
        }
        Request.Builder requestBuilder = builderHeader(headerMap);
        Request request = requestBuilder.url(uploadUrl).post(bodyBuild.build()).build();
        return httpExecute(request);
    }

    /**
     * json 请求下载图片
     *
     * @param downloadUrl 下载地址
     * @param savePath    保持地址
     * @param headerMap   自定义头
     */
    public static void downloadFile(String downloadUrl, String savePath, Map<String, String> headerMap) {
        Request.Builder requestBuilder = builderHeader(headerMap);
        Request request = requestBuilder
                .url(downloadUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                File file = new File(savePath);
                try (InputStream is = response.body().byteStream();
                     FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] bytes = new byte[2048];
                    int len = 0;
                    while ((len = is.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


// 异步下载
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println(Thread.currentThread().getName() + "文件下载出错");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    File file = new File(savePath + fileName);
//                    try (InputStream is = response.body().byteStream();
//                         FileOutputStream fos = new FileOutputStream(file)) {
//                        byte[] bytes = new byte[2048];
//                        int len = 0;
//                        while ((len = is.read(bytes)) != -1) {
//                            fos.write(bytes, 0, len);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    public static void main(String[] args) {
//        File file = new File("/img1/upload/face");
////        if(!file.exists()){
//        file.mkdirs();
////        }

     //     downloadFile("https://pics0.baidu.com/feed/30adcbef76094b365d21ea965d1fbedf8c109d71.jpeg?token=790742ee43b4f8745045b6c7649f8ec0","D://test/d.jpg",null);
        //downloadFile("http://192.168.16.168:8888/group1/M00/00/05/wKgQ5l7W9_eAPYyMAFFmS68VOLA890.mp3", "D://test.mp3", null);
    }

}
