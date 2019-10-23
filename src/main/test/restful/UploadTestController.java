package restful;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.cwks.CssnjWorksApplication;

@Controller
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CssnjWorksApplication.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class UploadTestController  {

	@Autowired
	private RestTemplate restTemplate;
	/*@Test
	public void start() {
		System.out.println("FTP上传问题件测试开始----------------");
		String transaction_id=UUID.randomUUID().toString();
		//文件成功上传ftp服务器后执行数据交换文件交换服务调用
			System.out.println("调用接口数据交换平台接口解析文件开始----------------");
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("ACCESS-CLIENT-ID", "COM.CSSNJ.TAX.GX.QZFW.001");
			requestHeaders.add("TRANSACTION-ID", transaction_id);
			requestHeaders.add("REQUSET-CLIENT-IP", "127.0.0.1");
			requestHeaders.add("RESOURCE-ID", "GOV.CSSNJ.MB.GX.FILE.EXCEL");
			ConcurrentHashMap reqMap = new  ConcurrentHashMap();
			HttpEntity requestEntity = new HttpEntity("{\"mbid\":\"001\",\"groupkey\":\"13208738475\",\"path\":\"/20191021\",\"filename\":\"tmp001.xlsx\"}", requestHeaders);
			try{
				ResponseEntity<String>  jsonResStr = restTemplate.postForEntity("http://localhost:8001/jhfw-api/jhdl-wbfw", requestEntity, String.class);
				System.out.println("接口返回报文："+jsonResStr);
			}catch (Exception e){
				System.out.println("调用接口异常："+e.getMessage());
			}
			System.out.println("调用接口数据交换平台接口解析文件结束----------------");
	}*/
	@Test
	public void generateMetadataTable() {
			System.out.println("调用接口数据交换平台接口解析文件开始----------------");
			HttpHeaders requestHeaders = new HttpHeaders();
			ConcurrentHashMap reqMap = new  ConcurrentHashMap();
			HttpEntity requestEntity = new HttpEntity("{\"MB_ID\":\"b6a7d94711ce4186a3c0f0a0ac74a4c9\",\"DS_CONF_ID\":\"DB_GXSW_SJJH_ORACLE\",\"DS_BIZ_ID\":\"DB_GXSW_SJJH_ORACLE\"}", requestHeaders);
			try{
				ResponseEntity<String>  jsonResStr = restTemplate.postForEntity("http://localhost:8001/jhfw-file-conf-api/generate-metadata-table", requestEntity, String.class);
				System.out.println("接口返回报文："+jsonResStr);
			}catch (Exception e){
				System.out.println("调用接口异常："+e.getMessage());
			}
			System.out.println("调用接口数据交换平台接口解析文件结束----------------");
	}
}
