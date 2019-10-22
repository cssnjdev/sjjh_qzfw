package com.cwks.biz.sjjh.util;

//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Component
@SuppressWarnings({ "deprecation"})
public class HttpClientUtil {
	private static final String CONTENT_TYPE_KEY = "Content-Type";
	private static final String CONTENT_TYPE_VALUE = "text/xml;charset=UTF-8";

	private static final String USER_AGENT_KEY = "User-Agent";
	private static final String USER_AGENT_VALUE = "Apache-HttpClient/4.1.1";

	private static final String ACCEPT_KEY = "Accept";
	private static final String ACCEPT_VALUE = "*/*";

	private static final String ACCEPT_LANGUAGE_KEY = "Accept-Language";
	private static final String ACCEPT_LANGUAGE_VALUE = "zh-cn";

	private static final String ACCEPT_ENCODING_KEY = "Accept-Encoding";
	private static final String ACCEPT_ENCODING_VALUE = "gzip, deflate";

	private static final String DEFAULT_ENCODING = "UTF-8";

	public static ConcurrentHashMap<String, HttpPost> httpClientMap = new ConcurrentHashMap<String, HttpPost>();

	private static volatile HttpClientUtil httpClientUtil;

    public HttpClientUtil() {}


    public static HttpClientUtil getInstance() {
        if (httpClientUtil == null) {
            synchronized (HttpClientUtil.class) {
                if (httpClientUtil == null) {
                	httpClientUtil = new HttpClientUtil();
                }
            }
        }
        return httpClientUtil;
    }

    private HttpPost getHttpPost(String serviceWsdl) {
        try {
            HttpPost httpPost = new HttpPost(serviceWsdl);
            httpPost.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
            httpPost.setHeader(ACCEPT_LANGUAGE_KEY, ACCEPT_LANGUAGE_VALUE);
            httpPost.setHeader(ACCEPT_KEY, ACCEPT_VALUE);
            httpPost.setHeader(USER_AGENT_KEY, USER_AGENT_VALUE);
            httpPost.setHeader(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VALUE);
            //httpPost.setHeader("SOAPAction", "");
            return httpPost;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	private DefaultHttpClient getProxyHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_KEEPALIVE, 30000);

//		HttpHost proxy = new HttpHost(PROXYHOSTNAME, PROXYPORT);
//		httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        return httpClient;
    }


    private  String parseHttpResponse(HttpResponse httpResponse,
                                      String soapFilterHead, String soapFilterEnd) {
        try {
            InputStream is = httpResponse.getEntity().getContent();
            byte[] byteArray = IOUtils.toByteArray(is);
            if (null != byteArray) {
                //String uncompressResult = GZipUtil.unGZip(byteArray);
                //String uncompressResult =getResString(byteArray);
                String uncompressResult = new String(byteArray, StandardCharsets.UTF_8);

                uncompressResult = removeSoapTag(uncompressResult, soapFilterHead, soapFilterEnd);

                return uncompressResult;
            }
        } catch (IllegalStateException | IOException e) {

        }
        return null;
    }

    /**
     * @param rquestXML
     * @return SOAP
     * @Description
     */
    private  String wrapSoapTag(String rquestXML, String soapHead, String soapEnd) {
        StringBuilder sb = new StringBuilder();
        sb.append(soapHead);
        sb.append(rquestXML);
        sb.append(soapEnd);
        return sb.toString();
    }

    /**
     * @param responseXML
     * @return
     * @Description
     */
    private  String removeSoapTag(String responseXML,
                                        String soapFilterHead, String soapFilterEnd) {
        if (null == responseXML || "".equals(responseXML)) {
            return responseXML;
        }
        String beginTag = "";
        String endTag = "";
        beginTag = soapFilterHead;
        endTag = soapFilterEnd;
        return StringUtils.substringBetween(responseXML, beginTag, endTag);
    }

    public  String sendPostRequest(String dataSource,String serviceWsdl, String requestXml, String soapHead, String soapEnd,
                                         String soapFilterHead, String soapFilterEnd) {
        String returnMsg = "";
    	requestXml = wrapSoapTag(requestXml, soapHead, soapEnd);

        HttpPost httpPost=httpClientMap.get(dataSource);
        if(httpPost==null) {
        	httpPost = getHttpPost(serviceWsdl);
        	httpClientMap.put(dataSource, httpPost);
        }
        HttpClient httpClient = getProxyHttpClient();
        HttpResponse httpResponse = null;
        try {
        	 byte[] bytes = requestXml.getBytes(DEFAULT_ENCODING);
             ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bytes, 0, bytes.length);
             httpPost.setEntity(byteArrayEntity);
             httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
            	returnMsg=parseHttpResponse(httpResponse, soapFilterHead, soapFilterEnd);
            }
        } catch (IOException e) {
        	returnMsg=e.toString();
        }finally {
        	httpPost.releaseConnection();
        }
        return returnMsg;
    }


    public  String sendPostRequest(String serviceWsdl, String requestXml, String soapHead, String soapEnd,
                                   String soapFilterHead, String soapFilterEnd) {
        String returnMsg = "";
        requestXml = wrapSoapTag(requestXml, soapHead, soapEnd);

        HttpPost httpPost= getHttpPost(serviceWsdl);
        HttpClient httpClient = getProxyHttpClient();
        HttpResponse httpResponse = null;
        try {
            byte[] bytes = requestXml.getBytes(DEFAULT_ENCODING);
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bytes, 0, bytes.length);
            httpPost.setEntity(byteArrayEntity);
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                returnMsg=parseHttpResponse(httpResponse, soapFilterHead, soapFilterEnd);
            }
        } catch (IOException e) {
            returnMsg=e.toString();
        }finally {
            httpPost.releaseConnection();
        }
        return returnMsg;
    }

    public static void main(String[] args)   {
		DefaultHttpClient httpClient = null;
		try {
			httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://127.0.0.1:8080/service/cssnjWebService?wsdl"); //webservice服务地址
			String soapRequestData =
					"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soa=\"http://www.cssnj.com.cn/soa_service/\">\n" +
							"   <soapenv:Header/>\n" +
							"   <soapenv:Body>\n" +
							"      <soa:invokeTask>\n" +
							"      <arg0><![CDATA[<?xml version='1.0' encoding='UTF-8'?><reqws><tran_id>CSSNJ.WS.WORKFLOW.RWZX</tran_id><channel_id>RwzxService_getZbrw</channel_id><tran_seq></tran_seq><body><![CDATA[<domData><rydm>23201000946</rydm><jgdm>23201051100</jgdm><gwdm>232000000040</gwdm><cxdm>01</cxdm><pageNumber>1</pageNumber><pageSize>15</pageSize></domData>]]]]>><![CDATA[</body></reqws>]]></arg0></soa:invokeTask>\n" +
							"   </soapenv:Body>\n" +
							"</soapenv:Envelope>";
	        HttpEntity re = new StringEntity(soapRequestData, HTTP.UTF_8);
	        httppost.setHeader("Content-Type","application/soap+xml; charset=utf-8");
	        httppost.setEntity(re);
	        HttpResponse response = httpClient.execute(httppost); //调用接口
	        System.out.println(response.getStatusLine().getStatusCode());
	        if(response.getStatusLine().getStatusCode() == 200) {
	        	//获得输出的字符串
	        	InputStream is = response.getEntity().getContent();
	            byte[] byteArray = IOUtils.toByteArray(is);
	            if (null != byteArray) {
	                String uncompressResult = new String(byteArray, StandardCharsets.UTF_8);
	                System.out.println(uncompressResult);
	            }
	        }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        httpClient.getConnectionManager().shutdown(); //关闭连接
		}

	}
}
