package com.zyb.mini.mall.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * httpclient4.5.x 工具类
 *
 * @author TanXin
 * @data 2018/8/19
 */
public final class HttpClient {

    private HttpClient() {
    }

    private static Logger log = LoggerFactory.getLogger(HttpClient.class);

    // 连接池管理器
    private static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    // 默认请求设置
    private static RequestConfig defaultRequestConfig = null;
    // 字符集
    private static final String CHARSET = "UTF-8";
    // 连接池最大连接数
    private static final int MAX_TOTAL = 300;
    // 默认每个路由最大连接数
    private static final int DEFAULT_MAX_PERROUTE = 100;
    // 接收数据的等待超时时间，单位ms
    private static final int SO_TIME_OUT = 3 * 1000;
    // 关闭Socket时，要么发送完所有数据，要么等待30s后，就关闭连接，此时socket.close()是阻塞的
    private static final int SO_LINGER = 30;
    // 连接超时时间
    private static final int CONNECT_TIME_OUT = 3 * 1000;
    // 读超时时间（等待数据超时时间）
    private static final int SOCKET_TIME_OUT = 3 * 1000;
    // 从池中获取连接超时时间
    private static final int CONNECTION_REQUEST_TIME_OUT = 1000;

    static {
        /**
         * 连接数相关设置
         */
        // 最大连接数
        connManager.setMaxTotal(MAX_TOTAL);
        // 默认的每个路由的最大连接数
        connManager.setDefaultMaxPerRoute(DEFAULT_MAX_PERROUTE);
        // 设置到某个路由的最大连接数，会覆盖defaultMaxPerRoute

        /**
         * socket配置（默认配置 和 某个host的配置）
         */
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(false) // 是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
                .setSoReuseAddress(true) // 是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                .setSoTimeout(SO_TIME_OUT) // 接收数据的等待超时时间，单位ms
                .setSoLinger(SO_LINGER) // 关闭Socket时，要么发送完所有数据，要么等待30s后，就关闭连接，此时socket.close()是阻塞的
                .setSoKeepAlive(true) // 开启监视TCP连接是否有效
                .build();
        connManager.setDefaultSocketConfig(socketConfig);
        /**
         * request请求相关配置
         */
        defaultRequestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT) // 连接超时时间
                .setSocketTimeout(SOCKET_TIME_OUT) // 读超时时间（等待数据超时时间）
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT) // 从池中获取连接超时时间
                .build();
    }

    /**
     * 从连接池取得一个httpclient连接
     *
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager) // 连接管理器
                .setDefaultRequestConfig(defaultRequestConfig) // 默认请求配置
                .build();
        return httpClient;
    }

    /**
     * 发送get请求
     *
     * @param api    请求地址
     * @param params 参数
     * @return
     */
    public static String sendGetRequest(String api, Map<String, String> params) {
        String result = null;
        CloseableHttpClient httpClient = getHttpClient();
        if (params != null) {
            int i = 0;
            StringBuffer paramStr = new StringBuffer("");
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                if (i == 0) {
                    paramStr.append("?");
                } else {
                    paramStr.append("&");
                }
                paramStr.append(entry.getKey()).append("=").append(entry.getValue());
            }
            api += paramStr.toString();
        }
        HttpGet httpGet = new HttpGet(api);
        CloseableHttpResponse response = null;
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + CHARSET);
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, CHARSET);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("发送HTTP GET请求异常", e);
        } finally {
            closeResponse(response);
            closeGet(httpGet);
        }
        return result;
    }


    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param api
     * @return
     */
    public static String sendPostRequest(String api) {
        return sendPostRequest(api, null);
    }

    /**
     * 发送POST请求 (HTTP),数据在URL后  api?K=V&K=V...
     *
     * @param api    请求地址
     * @param params 参数
     * @return
     * @author tanxin
     */
    public static String sendPostRequestByUrlParam(String api, Map<String, String> params) {
        String result = null;
        CloseableHttpClient httpClient = getHttpClient();
        // 遍历params
        if (params != null) {
            int i = 0;
            StringBuffer paramStr = new StringBuffer("");
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                if (i == 0) {
                    paramStr.append("?");
                } else {
                    paramStr.append("&");
                }
                paramStr.append(entry.getKey()).append("=").append(entry.getValue());
            }
            api += paramStr.toString();
        }
        HttpPost httpPost = new HttpPost(api);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + CHARSET);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, CHARSET);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("HTTP  POST URL参数  请求失败", e);
        } finally {
            closeResponse(response);
            closePost(httpPost);
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），form-data数据 K-V形式
     *
     * @param api    API接口URL
     * @param params 参数map
     * @return
     */
    public static String sendPostRequest(String api, Map<String, String> params) {
        String result = null;
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(api);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + CHARSET);
        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Entry<String, String> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(CHARSET)));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, CHARSET);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("HTTP  POST FORM-DATA 请求失败", e);
        } finally {
            closeResponse(response);
            closePost(httpPost);
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param api  请求地址
     * @param json json 字符串
     * @return
     */
    public static String sendPostRequestByJSON(String api, String json) {
        CloseableHttpClient httpClient = getHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(api);
        httpPost.addHeader("Content-Type", "application/json; charset=" + CHARSET);
        CloseableHttpResponse response = null;
        try {
            StringEntity stringEntity = new StringEntity(json, CHARSET);// 解决中文乱码问题
            stringEntity.setContentEncoding(CHARSET);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, CHARSET);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("HTTP  POST JSON 请求失败", e);
        } finally {
            closeResponse(response);
            closePost(httpPost);
        }
        return httpStr;
    }

    /**
     * 发送 PUT 请求（HTTP），JSON形式
     *
     * @param api  请求地址
     * @param json json 字符串
     * @return
     */
    public static String sendPutRequestByJSON(String api, String json) {
        CloseableHttpClient httpClient = getHttpClient();
        String httpStr = null;
        HttpPut httpPut = new HttpPut(api);
        httpPut.addHeader("Content-Type", "application/json; charset=" + CHARSET);
        CloseableHttpResponse response = null;
        try {
            StringEntity stringEntity = new StringEntity(json, CHARSET);// 解决中文乱码问题
            stringEntity.setContentEncoding(CHARSET);
            httpPut.setEntity(stringEntity);
            response = httpClient.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, CHARSET);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("HTTP  POST JSON 请求失败", e);
        } finally {
            closeResponse(response);
            closePut(httpPut);
        }
        return httpStr;
    }


    /**
     * 发送 POST 请求（HTTP），xml形式
     *
     * @param api    请求地址
     * @param xmlStr xml 字符串
     * @return
     */
    public static String sendPostRequestByXML(String api, String xmlStr) {
        CloseableHttpClient httpClient = getHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(api);
        CloseableHttpResponse response = null;
        httpPost.setHeader("Content-Type", "text/xml; charset=" + CHARSET);
        try {
            StringEntity stringEntity = new StringEntity(xmlStr, CHARSET);// 解决中文乱码问题
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, CHARSET);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("HTTP  POST XML 请求失败", e);
        } finally {
            closeResponse(response);
            closePost(httpPost);
        }
        return httpStr;
    }

    /**
     * 下载文件
     *
     * @param downloadUrl
     * @param savePath
     * @param fileName
     * @author tanxin
     */
    public static void downLoad(String downloadUrl, String savePath, String fileName) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(downloadUrl);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream is = entity.getContent();
                    File file = new File(savePath + fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[2048];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    is.close();
                }
            }
        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
            log.debug("HTTP 下载文件失败", e);
        } finally {
            closeResponse(response);
            closeGet(httpGet);
        }

    }


    //
    //
    //关闭方法
    //
    //
    //
    private static void closeResponse(CloseableHttpResponse response) {
        if (response != null) {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void closePost(HttpPost httpPost) {
        if (httpPost != null) {
            try {
                httpPost.abort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeGet(HttpGet httpGet) {
        if (httpGet != null) {
            try {
                httpGet.abort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void closePut(HttpPut httpPut) {
        if (httpPut != null) {
            try {
                httpPut.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

}
