package com.cwks.biz.sjjh.mb;

import com.alibaba.fastjson.JSONObject;
import com.cwks.bizcore.sjjh.core.config.log.JhLogWritter;
import com.cwks.bizcore.sjjh.core.utils.ExcelUtil;
import com.cwks.bizcore.sjjh.core.utils.db.DataSourceUtils;
import com.cwks.bizcore.sjjh.core.vo.ExcelSheetPO;
import com.cwks.bizcore.sjjh.core.vo.JhDlTempletEvent;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * ExcelGxTaxMb
 *
 * @author CSSNJ
 * @version 1.0
 */
@Component
public class ExcelTaxDataLoadMb {
	private static final String STRING_SPLIT_GZ="\\.";
	private  final String SQL_JHDL_DATALOAD_QUERY_MB_SHEET="SQL_JHDL_DATALOAD_QUERY_MB_SHEET";
	private  final String SQL_JHDL_DATALOAD_QUERY_MB_COLMX="SQL_JHDL_DATALOAD_QUERY_MB_COLMX";
	private  final String col="col";
	/**
	 * 获取源数据根据文件
	 * 
	 * @param jhDlTempletEvent
	 * @param jhLogWritter
	 * @return
	 * @throws Exception 
	 * @throws BeansException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public JhDlTempletEvent loadDataByExcel(JhDlTempletEvent jhDlTempletEvent, JhLogWritter jhLogWritter) throws Exception{
		jhLogWritter.bizDebug("*******1.获取报文信息*********");
		List<?> excelDataList=null,colmxList=null,colRowDataList=null,sheetList = null,valRowDataList = null;
		Map m=null;
		String jsonreq = jhDlTempletEvent.getReqBizMsg();
		if(jsonreq.indexOf("&lt;")>-1) {
			jsonreq=jsonreq.replaceAll("&quot;", "\"");
			jsonreq=jsonreq.substring(jsonreq.indexOf("&lt;body&gt;")+"&lt;body&gt;".length(), jsonreq.indexOf("&lt;/body&gt;"));
		}
		JSONObject jSONObject=(JSONObject) JSONObject.parse(jsonreq);
		String  filename=jSONObject.getString("filename");
		String pathname=jSONObject.getString("path");
		String mbid=jSONObject.getString("mbid");
		String groupkey=jSONObject.getString("groupkey");
		String transactionId=jhDlTempletEvent.getTransactionId();
		String pch=UUIDGenerator.getUUID()+"_"+org.apache.http.client.utils.DateUtils.formatDate(new Date(),"yyyyMMddhh24mmss");
		jhLogWritter.bizDebug("*******2.获取根据配置文件构造的连接池信息*********");
		ConcurrentHashMap dataExchangeMap = new ConcurrentHashMap();
		//ConcurrentHashMap resourceMap = jhDlTempletEvent.getJhqzfw_resource_map();
		FTPClient ftpClient = (FTPClient) jhDlTempletEvent.getDataSoureces().get("FTP_GXSW_SJJH_EXCEL");
		DataSourceUtils dataSourceUtils = (DataSourceUtils) jhDlTempletEvent.getDataSoureces().get("DB_GXSW_SJJH_ORACLE");
		jhLogWritter.bizDebug("*******3.读取数据库模板配置*********");
		ArrayList params = new ArrayList();
		jhLogWritter.bizDebug("*******4.读取ftp上面的文件*********");
		String[] fNameAry=filename.split(STRING_SPLIT_GZ);
		String remoteFileName = fNameAry[0];
		String remoteFileType = "."+fNameAry[1];
		File localFile = null;
		OutputStream is = null;
		ArrayList sqlPrams = new ArrayList();
		try {
			ftpClient.changeWorkingDirectory(pathname);
			localFile = File.createTempFile(remoteFileName, remoteFileType);
			is = new FileOutputStream(localFile);
			ftpClient.retrieveFile(filename, is);
			excelDataList = ExcelUtil.readExcel(localFile, null, null);// 解析excel内容
		}catch(Exception e) {
			if(is != null){
				is.close();
			}
			if(localFile != null){
				 if(!localFile.delete()){
	                    localFile.deleteOnExit();
	                }
			}
			throw new Exception(e);
		}finally {
			if(is != null){
				is.close();
			}
			if(localFile != null){
				 if(!localFile.delete()){
	                    localFile.deleteOnExit();
	                }
			}
		}
		ExcelSheetPO po = null;
		StringBuffer insertBf=null;
		StringBuffer valusBf =null;
		BigDecimal qshs=null,qsls=null;
		String tablename="";
		Object obj=null;
		for (int i = 0; i < excelDataList.size(); i++) {
			params.clear();
			params.add(mbid);
			params.add(i+1);
			m=dataSourceUtils.queryformap(dataSourceUtils.getSql(SQL_JHDL_DATALOAD_QUERY_MB_SHEET), params);
			qshs=(BigDecimal)m.get("QSHS");
			qsls=(BigDecimal) m.get("QSLS");
			tablename=(String) m.get("TABLE_NAME");
			params.add(tablename);
			/*****生成excel字段与数据库字段的对照关系Map*****/
			colmxList=dataSourceUtils.queryforlist(dataSourceUtils.getSql(SQL_JHDL_DATALOAD_QUERY_MB_COLMX), params);
			for(Object o:colmxList) {
				dataExchangeMap.put(((Map)o).get("EXCEL_COLMC"), ((Map)o).get("DB_COLMC"));
			}
			po=(ExcelSheetPO) excelDataList.get(i);
			sheetList=po.getDataList();
			/*****生成excel字段列号与excel字段的对照关系Map*****/
			colRowDataList=(List) sheetList.get(0);
			for(int j=0;j<colRowDataList.size();j++) {
				dataExchangeMap.put(col+j,colRowDataList.get(j));
			}
			/*****拼接插入语句Map*****/
			for(int k=qshs.intValue()-1;k<sheetList.size();k++) {
				insertBf = new StringBuffer();
				valusBf=new StringBuffer(" values(");
				insertBf.append("insert into " + tablename + "(");
				valRowDataList=(List<?>) sheetList.get(k);
				for(int j=qsls.intValue()-1;j<valRowDataList.size();j++) {
					obj=valRowDataList.get(j);
					insertBf.append(dataExchangeMap.get(dataExchangeMap.get(col+j))+",");
					if (obj == null) {
						valusBf.append("" + obj + ",");
					} else {
						valusBf.append("'" + obj + "',");
					}
				}
				insertBf.append("JHFW_PCH,JHFW_TRANSACTIONID,JHFW_TB_SJ,JHFW_GROUP_KEY)");
				valusBf.append("'"+pch+"','"+transactionId+"',sysdate,'"+groupkey+"')");
				sqlPrams.add(insertBf.toString()+valusBf.toString());
			}
		}
		boolean flag=dataSourceUtils.batchUpdate(sqlPrams);//批量
		if(flag) {
			jhDlTempletEvent.setReturnCode(1);
			jhDlTempletEvent.setReturnMsg("原始数据载入成功");
		}else {
			jhDlTempletEvent.setReturnCode(-1);
			jhDlTempletEvent.setReturnMsg("原始数据载入失败");
		}
		return jhDlTempletEvent;
	}
	/**
	 * 处理、清洗数据
	 * 
	 * @param requestEvent
	 * @return
	 * @throws BeansException
	 */
	public JhDlTempletEvent processingData(JhDlTempletEvent jhDlTempletEvent, JhLogWritter jhLogWritter)throws BeansException {
		return jhDlTempletEvent;
	}
	/**
	 * 组装返回报文模板
	 * 
	 * @param requestEvent
	 * @return
	 * @throws BeansException
	 */
	public JhDlTempletEvent returnData(JhDlTempletEvent jhDlTempletEvent, JhLogWritter jhLogWritter)throws BeansException {
		return jhDlTempletEvent;
	}
}
