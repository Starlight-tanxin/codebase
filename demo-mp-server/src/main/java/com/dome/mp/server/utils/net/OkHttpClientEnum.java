package com.dome.mp.server.utils.net;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * describe: 枚举单例
 *
 * @author: TanXin
 * @date: 2020/4/2 23:51
 */
public enum OkHttpClientEnum {

    OK_HTTP_CLIENT;

    private final OkHttpClient okHttpClient;

    private final int connectTimeout = 5;
    private final int writeTimeout = 30;
    private final int readTimeout = 30;
    private final int callTimeout = 30;


    OkHttpClientEnum() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .callTimeout(callTimeout, TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor())
                .build();
    }

    public OkHttpClient getInstance() {
        return okHttpClient;
    }

    // 请求头拦截器
    private static class RequestInterceptor implements Interceptor {

        public enum HttpHeaderEnum {
            CONNECTION("Connection", "keep-alive"),
            ACCEPT_ENCODING("Accept-Encoding", "gzip, deflate"),
            ACCEPT("Accept", "*/*");
            private String headerName;
            private String headerValue;

            HttpHeaderEnum(String headerName, String headerValue) {
                this.headerName = headerName;
                this.headerValue = headerValue;
            }

            public String getHeaderName() {
                return headerName;
            }

            public String getHeaderValue() {
                return headerValue;
            }
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
//                    .addHeader(HttpHeaderEnum.ACCEPT_ENCODING.getHeaderName(), HttpHeaderEnum.ACCEPT_ENCODING.getHeaderValue())
                    .addHeader(HttpHeaderEnum.CONNECTION.getHeaderName(), HttpHeaderEnum.CONNECTION.getHeaderValue())
                    .addHeader(HttpHeaderEnum.ACCEPT.getHeaderName(), HttpHeaderEnum.ACCEPT.getHeaderValue())
                    .build();
            return chain.proceed(request);
        }
    }
}
