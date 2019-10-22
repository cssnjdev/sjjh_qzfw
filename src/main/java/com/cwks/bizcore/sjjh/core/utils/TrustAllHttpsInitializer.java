package com.cwks.bizcore.sjjh.core.utils;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 信任所有https的请求
 * 注意：使用该初始器化之后会存在安全认证风险，慎用
 * @Author lnk
 * @Date 2018/5/29
 */
public class TrustAllHttpsInitializer implements RestfulHttpClient.URLConnectionInitializer {
    @Override
    public HttpURLConnection init(HttpURLConnection connection, RestfulHttpClient.HttpClient client) {
        String protocol = connection.getURL().getProtocol();
        if ("https".equalsIgnoreCase(protocol)) {
            HttpsURLConnection https = (HttpsURLConnection) connection;
            trustAllHosts(https);
            https.setHostnameVerifier(DO_NOT_VERIFY);
            return https;
        }
        return connection;
    }

    /**
     * 覆盖java默认的证书验证
     */
    private static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }};

    /**
     * 设置不验证主机
     */
    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * 信任所有
     * @param connection
     * @return
     */
    private static SSLSocketFactory trustAllHosts(HttpsURLConnection connection) {
        SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, null);
            SSLSocketFactory newFactory = sc.getSocketFactory();
            connection.setSSLSocketFactory(newFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldFactory;
    }

    public static void main(String[] args) throws IOException {
        String url = "https://b2b.10086.cn/b2b/main/viewNoticeContent.html?noticeBean.id=";

        RestfulHttpClient.HttpResponse response = RestfulHttpClient.getClient(url)
                .addInitializer(new TrustAllHttpsInitializer())
                .request(); //发起请求

        System.out.println(response.getCode());     //响应状态码
        System.out.println(response.getRequestUrl());//最终发起请求的地址
        if(response.getCode() == 200){
            //请求成功
            System.out.println(response.getContent());  //响应内容
        }
    }
}