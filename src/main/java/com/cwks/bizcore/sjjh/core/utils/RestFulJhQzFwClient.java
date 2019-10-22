package com.cwks.bizcore.sjjh.core.utils;

import java.io.IOException;

/**
 * 自定义RestFulApiClient访问类
 * @author hur
 * 
 */  
public class RestFulJhQzFwClient {

    private static RestFulJhQzFwClient instance = null;

    public static RestFulJhQzFwClient singleton() {
        if (instance == null) {
            instance = new RestFulJhQzFwClient();
        }
        return instance;
    }

    public String postForObject(String jhqz_rest_url,String clientId,String resourceId,String clientIp,String reqMessage) {
        String result = "";
        try {
            String wbfw_url = jhqz_rest_url;
            if(ComProConf.JHDL_RESTAPI_WBFW_URL != null && !"".equals(ComProConf.JHDL_RESTAPI_WBFW_URL)){
                wbfw_url = ComProConf.JHDL_RESTAPI_WBFW_URL;
            }
            RestfulHttpClient.HttpClient client = RestfulHttpClient.getClient(wbfw_url);
            client.post();
            client.connectTimeout(20000);
            client.readTimeout(60*10000);
            client.addHeader("Content-type", "application/json;charsert=UTF-8");
            client.addHeader("ACCESS-CLIENT-ID", clientId);
            client.addHeader("REQUSET-CLIENT-IP", clientIp);
            client.addHeader("RESOURCE-ID",resourceId);
            client.body(reqMessage);

            //发起请求，获取响应结果
            RestfulHttpClient.HttpResponse response = null;
            try {
                response = client.request();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //根据状态码判断请求是否成功
            if(response != null){
                if(response.getCode() == 200){
                    //获取响应内容
                    result = response.getContent();
                }
            }
        } catch (Exception e) {
            return "post error:"+e.getMessage();
        }
        return result;
    }

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) {
        //RestFulApiClient.singleton().httpPost();
        RestfulHttpClient.HttpClient client = RestfulHttpClient.getClient("http://localhost:8080/jhfw-api/jhdl-wbfw");
        client.post();
        client.connectTimeout(20000);
        client.readTimeout(60*10000);
        client.addHeader("Content-type", "application/json;charsert=UTF-8");
        client.addHeader("ACCESS-CLIENT-ID", "123123");
        client.addHeader("REQUSET-CLIENT-IP", "192.168.0.1");
        client.addHeader("RESOURCE-ID", "GOV.JS.SW.SJ.GT3.SWZJ.JSZY.FCJY.TYJKFW.WSZM");
        client.body("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>  <service xmlns=\"http://www.chinatax.gov.cn/spec/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> <head> \t\t <tran_id>SWZJ.JSZY.FCJY.TYJKFW.WSZM</tran_id> \t\t <channel_id>JSDS.NFWB.FCJYXT</channel_id> \t\t <tran_seq>45DC2F22111D12288324441188CC1633</tran_seq> \t\t <tran_date>20180912</tran_date> \t\t <tran_time>112125170</tran_time> \t\t <expand> \t\t\t \t<name>sjry</name> \t\t\t \t<value>23200wsbs00</value> \t\t </expand>  <expand> \t\t\t \t<name>sjjg</name>  \t<value>23212821512</value> \t\t </expand> \t <expand> \t\t\t \t<name>identityType</name> \t\t\t \t<value>SWZJ.JSZY.FCJY.TYJKFW.WSZM</value> \t </expand> \t </head> \t <body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?> <taxML xsi:type=\"Mzcx001Request\" bbh=\"String\" xmlbh=\"String\" xmlmc=\"String\" xsi:schemaLocation=\"http://www.chinatax.gov.cn/dataspec/ TaxMLBw_JSZY_MZ_001_Request_V1.0.xsd\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><reqStr>{HTBH:\"232112016093053384\",SFZH:\"32021119780908383X\"}</reqStr> </taxML>]]></body></service>");

        /*//添加全局信任
        RestfulHttpClient.addInitializer(new TrustAllHttpsInitializer());

        //本次请求添加信任
        RestfulHttpClient.HttpResponse response = RestfulHttpClient.getClient(url)
                .addInitializer(new TrustAllHttpsInitializer())
                .request(); //发起请求
         public class MyConnectionInitializer implements RestfulHttpClient.URLConnectionInitializer {
            @Override
            public HttpURLConnection init(HttpURLConnection connection, RestfulHttpClient.HttpClient client) {
                //添加证书
                return connection;
            }
        }

        // 证书设置全局请求有效
        RestfulHttpClient.addInitializer(new MyConnectionInitializer());
        // 证书只有本次请求有效
        RestfulHttpClient.getClient(url).addInitializer(new MyConnectionInitializer()).request();
        */


        //发起请求，获取响应结果
        /*RestfulHttpClient.HttpResponse response = null;
        try {
            response = client.request();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //根据状态码判断请求是否成功
        /*if(response.getCode() == 200){
            //获取响应内容
            String result = response.getContent();
        }*/
    }

}