import com.cwks.CssnjWorksApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CssnjWorksApplication.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class UploadTestController  {

	@Autowired
	private RestTemplate restTemplate;

//	@Value("${biz.sjjhpt.qzfw.core.wbfw.restful.url}")
	private String qzfw_restful;
//	@Value("${ftp.server}")
//	private String ftp_server;
//	@Value("${ftp.port}")
//	private int ftp_port;
//	@Value("${ftp.userName}")
//	private String ftp_userName;
//	@Value("${ftp.userPassword}")
//	private String ftp_userPassword;
//	@Value("${ftp.isPassiveMode}")
//	private boolean ftp_isPassiveMode;
//	@Value("${ftp.timeout}")
//	private int ftp_timeout;
//	@Value("${ftp.encoding}")
//	private String ftp_encoding;



	@Test
	public void start() {
		System.out.println("FTP上传问题件测试开始----------------");
		String transaction_id=UUID.randomUUID().toString();
		System.out.println(System.currentTimeMillis());
		String basePath="";
		String filePath="";
		String filename="";
		InputStream inputStream=null;
		final String path = "D:/1.xlsx";
		boolean isok = false;
//		try {
//			File file = new File(path);
//			if(file.exists()){
//				Date now=new Date();
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//yyyyMMddHHmmss
//				filePath=dateFormat.format(now);
//				filename = transaction_id+"_"+System.currentTimeMillis()+"_"+file.getName();
//				inputStream = new FileInputStream(new File(path));
//				isok = FtpUtil.uploadFile(ftp_server,ftp_port,ftp_userName,ftp_userPassword,basePath,filePath,filename,inputStream,ftp_isPassiveMode,ftp_timeout,ftp_encoding);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(inputStream != null){
//					inputStream.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println("FTP上传问题件测试结束----------------");
		//文件成功上传ftp服务器后执行数据交换文件交换服务调用
		if(true){
			System.out.println("调用接口数据交换平台接口解析文件开始----------------");
			//headers
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("ACCESS-CLIENT-ID", "COM.CSSNJ.TAX.GX.QZFW.001");
			requestHeaders.add("TRANSACTION-ID", transaction_id);
			requestHeaders.add("REQUSET-CLIENT-IP", "127.0.0.1");
			requestHeaders.add("RESOURCE-ID", "GOV.CSSNJ.MB.GX.FILE.EXCEL");
			ConcurrentHashMap reqMap = new  ConcurrentHashMap();
			reqMap.put("basePath",basePath);
			reqMap.put("filePath",filePath);
			reqMap.put("filename",filename);
			HttpEntity requestEntity = new HttpEntity("{\"mbid\":\"001\",\"groupkey\":\"13208738475\",\"path\":\"/20191021\",\"filename\":\"tmp001.xlsx\"}", requestHeaders);
			try{
				ResponseEntity<String>  jsonResStr = restTemplate.postForEntity("http://localhost:8001/jhfw-api/jhdl-wbfw", requestEntity, String.class);
				System.out.println("接口返回报文："+jsonResStr);
			}catch (Exception e){
				System.out.println("调用接口异常："+e.getMessage());
			}
			//根据jsonResStr返回的结果，删除ftp上面的文件（可选）
//			FtpUtil.deleteFile(ftp_server,ftp_port,ftp_userName,ftp_userPassword,basePath,filePath,filename,ftp_isPassiveMode,ftp_timeout,ftp_encoding);
			System.out.println("调用接口数据交换平台接口解析文件结束----------------");
		}


	}

}
