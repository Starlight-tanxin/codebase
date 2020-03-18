package com.zyb.mini.mall.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

/**
 * @author Created by 谭健 on 2018/10/11 0011. 星期四. 10:43.
 * © All Rights Reserved.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class NetUtils {


    /**
     * 获取本机主机名称
     */
    public static String getHostName() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.error("代码执行错误 ", e);
            return "";
        }
        return inetAddress.getHostName();
    }


    /**
     * 获取本机公网IP
     */
    public static String getInternetIp() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            String localhostAddress = InetAddress.getLocalHost().getHostAddress();
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements()) {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements()) {
                    ip = addrs.nextElement();
                    if (ip instanceof Inet4Address
                            && ip.isSiteLocalAddress() && !localhostAddress.equals(ip.getHostAddress())) {
                        return ip.getHostAddress();
                    }
                }
            }

            // 如果没有外网IP，就返回内网IP
            return localhostAddress;
        } catch (Exception e) {
            return "";
        }
    }


    public static String getRemoteIp(HttpServletRequest request) {
        String ip;
        ip = request.getHeader("x-forwarded-for");
        if (isNullIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isNullIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isNullIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isNullIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isNullIp(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        if ("0.0.0.0.0.0.0.1".equals(ip) || "0.0.0.0.0.0.0.1%0".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    private static boolean isNullIp(final String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }


    public static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper;
    }


    /**
     * 通过ip 地址获取地理地址
     */
    public static String getAddressByIp(String ip) {
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL("http://ip.taobao.com/service/getIpInfo.php");
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间，单位毫秒
            connection.setConnectTimeout(2000);
            // 设置读取数据超时时间，单位毫秒
            connection.setReadTimeout(2000);
            // 是否打开输出流 true|false
            connection.setDoOutput(true);
            // 是否打开输入流true|false
            connection.setDoInput(true);
            // 提交方法POST|GET
            connection.setRequestMethod("POST");
            // 是否缓存true|false
            connection.setUseCaches(false);
            // 打开连接端口
            connection.connect();
            // 打开输出流往对端服务器写数据
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.writeBytes("ip=" + ip);
            // 刷新
            out.flush();
            // 关闭输出流
            out.close();
            // 往对端写完数据对端服务器返回数据 ,以BufferedReader流来读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();

            String s = builder.toString();
            TaobaoIp taobaoIp = JSON.parseObject(s, TaobaoIp.class);
            if (taobaoIp.isSuccess()) {
                TaobaoIp.Data data = taobaoIp.getData();
                Joiner joiner = Joiner.on(" / ").skipNulls();
                return joiner.join(data.getCountry(), data.getArea(), data.getRegion(), data.getCity(), data.getCounty(), data.getIsp());
            }
        } catch (IOException e) {
            log.warn("通过ip获取省市区失败");
            return "";
        } finally {
            if (connection != null) {
                // 关闭连接
                connection.disconnect();
            }
        }

        return "";
    }


    @Data
    private static class TaobaoIp {
        private Integer code;

        private Data data;

        @lombok.Data
        private static class Data {

            private String country;
            private String area;
            private String region;
            private String city;
            private String county;
            private String isp;

        }

        private boolean isSuccess() {
            return code == 0;
        }
    }

}
