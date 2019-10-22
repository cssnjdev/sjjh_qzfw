package com.cwks.biz.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cwks.biz.demo.dao.JhfwMbFileColmxMapper;
import com.cwks.biz.demo.dao.JhfwMbFileMapper;
import com.cwks.biz.demo.dao.JhfwMbFileSheetMapper;
import com.cwks.biz.demo.domain.JhfwMbFile;
import com.cwks.biz.demo.domain.JhfwMbFileColmx;
import com.cwks.biz.demo.domain.JhfwMbFileSheet;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.systemConfig.SystemContext;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.log.LogWritter;
import com.cwks.common.util.StringUtils;


@Component
@Service("sjjhmbpzService")
public class SjjhmbpzService{
    @Autowired
    private JdbcDao jdbcDao;
    @Autowired
    private JhfwMbFileMapper jhfwMbFileMapper;
    @Autowired
    private JhfwMbFileSheetMapper jhfwMbFileSheetMapper;
    @Autowired
    private JhfwMbFileColmxMapper jhfwMbFileColmxMapper;
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
    public ResponseEvent queryMbpzList(RequestEvent requestEvent) throws Exception {
    	LogWritter.bizDebug("查询模板配置queryMbpzList");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap resMap=new HashMap();
        HashMap reqMap=(HashMap) requestEvent.getRequestMap();
        String mbid=(String) reqMap.get("mbid");
        String mbmc=(String) reqMap.get("mbmc");
        String mbms=(String) reqMap.get("mbms");
        String lrsj=(String) reqMap.get("lrsj");
        ArrayList prams=new ArrayList();
    	if(StringUtils.isEmpty(mbid)) {
    		prams.add(mbid);
    		prams.add(1);
    	}else {
    		prams.add(mbid);
    		prams.add(0);
    	}
    	if(StringUtils.isEmpty(mbmc)) {
    		prams.add(mbmc);
    		prams.add(1);
    	}else {
    		prams.add(mbmc);
    		prams.add(0);
    	}
    	if(StringUtils.isEmpty(mbms)) {
    		prams.add(mbms);
    		prams.add(1);
    	}else {
    		prams.add(mbms);
    		prams.add(0);
    	}
    	if(StringUtils.isEmpty(lrsj)) {
    		prams.add(lrsj);
    		prams.add(1);
    	}else {
    		prams.add(lrsj);
    		prams.add(0);
    	}
        String sql=SystemContext.getSql("SQL_SJJH_QUERY_MBBYPARAMS");
        List list=jdbcDao.queryforlist(sql,prams);
        resMap.put("JSONDATA",JsonUtil.toJson(list));
        resEvent.setResMap(resMap);
        return resEvent;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEvent queryMbSheetList(RequestEvent requestEvent) throws Exception {
    	LogWritter.bizDebug("查询模板配置queryMbSheetList");
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap resMap=new HashMap();
    	HashMap map=new HashMap();
    	String mbid=(String) requestEvent.getRequestMap().get("mbid");
    	ArrayList prams=new ArrayList();
    	prams.add(mbid);
    	String sql=SystemContext.getSql("SQL_SJJH_QUERY_SHEETBYMBID");
    	List list=jdbcDao.queryforlist(sql,prams);
    	map.put("list",list);
    	JhfwMbFile jhfwMbFile=jhfwMbFileMapper.selectByPrimaryKey(mbid);
    	map.put("jhfwMbFile",jhfwMbFile);
    	resMap.put("JSONDATA",JsonUtil.toJson(map));
    	resEvent.setResMap(resMap);
    	return resEvent;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEvent queryMbSheetColmxList(RequestEvent requestEvent) throws Exception {
    	LogWritter.bizDebug("查询模板配置queryMbSheetColmxList");
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap resMap=new HashMap();
    	HashMap map=new HashMap();
    	String mbid=(String) requestEvent.getRequestMap().get("mbid");
    	String sheetIndex=(String) requestEvent.getRequestMap().get("sheetIndex");
    	ArrayList prams=new ArrayList();
    	prams.add(mbid);
    	prams.add(sheetIndex);
    	String sql=SystemContext.getSql("SQL_SJJH_QUERY_SHEETCOLBYMBIDANDINDEX");
    	List list=jdbcDao.queryforlist(sql,prams);
    	map.put("list",list);
    	resMap.put("JSONDATA",JsonUtil.toJson(map));
    	resEvent.setResMap(resMap);
    	return resEvent;
    }
    @SuppressWarnings({"rawtypes", "unchecked" })
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEvent saveMb(RequestEvent requestEvent) throws Exception {
    	LogWritter.bizDebug("saveMb");
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap resMap=new HashMap();
    	HashMap restrnMap=new HashMap();
    	String mbid="";
		JhfwMbFileSheet jhfwMbFileSheet=null;
		JhfwMbFileSheet jhfwMbFileSheetKey=null;
		JSONObject obj=null;
		HashMap reqMap=(HashMap) requestEvent.getRequestMap();
		JSONObject mbFormData=(JSONObject)JSONObject.parse((String) reqMap.get("mbFormData"));
		JSONArray tabledata=(JSONArray)JSONArray.parse((String) reqMap.get("tabledata"));
		JhfwMbFile jhfwMbFile=jhfwMbFileMapper.selectByPrimaryKey((String) mbFormData.get("mbid"));
		if(jhfwMbFile!=null) {
			mbid=jhfwMbFile.getMbId();
			jhfwMbFile.setMbLx((String) mbFormData.get("mblx"));
			jhfwMbFile.setMbMc((String) mbFormData.get("mbmc"));
			jhfwMbFile.setMbMs((String) mbFormData.get("mbms"));
			String yxbj=(String) mbFormData.get("yxbj");
			jhfwMbFile.setYxBj((yxbj!=null&&yxbj.equals("on"))?"1":"0");
			jhfwMbFileMapper.updateByPrimaryKey(jhfwMbFile);
			for(Object o:tabledata) {
				if(o instanceof JSONArray) {
					continue;
				}
    			obj=(JSONObject)o;
    			jhfwMbFileSheetKey=new JhfwMbFileSheet();
    			jhfwMbFileSheetKey.setMbId(mbid);
    			jhfwMbFileSheetKey.setSheetIndex(obj.getShort("SHEET_INDEX"));
    			jhfwMbFileSheet=jhfwMbFileSheetMapper.selectByPrimaryKey(jhfwMbFileSheetKey);
    			if(jhfwMbFileSheet==null) {
    				jhfwMbFileSheet=new JhfwMbFileSheet();
    				jhfwMbFileSheet.setMbId(mbid);
    				jhfwMbFileSheet.setQshs(obj.getShort("QSHS"));
    				jhfwMbFileSheet.setQsls(obj.getShort("QSLS"));
    				jhfwMbFileSheet.setSheetIndex(obj.getShort("SHEET_INDEX"));
    				jhfwMbFileSheet.setTableMs(obj.getString("TABLE_MS"));
    				jhfwMbFileSheet.setTableSchema(obj.getString("TABLE_SCHEMA"));
    				jhfwMbFileSheet.setTableName(obj.getString("TABLENAME"));
    				jhfwMbFileSheetMapper.insert(jhfwMbFileSheet);
    			}else {
    				jhfwMbFileSheet.setMbId(mbid);
    				jhfwMbFileSheet.setQshs(obj.getShort("QSHS"));
    				jhfwMbFileSheet.setQsls(obj.getShort("QSLS"));
    				jhfwMbFileSheet.setSheetIndex(obj.getShort("SHEET_INDEX"));
    				jhfwMbFileSheet.setTableMs(obj.getString("TABLE_MS"));
    				jhfwMbFileSheet.setTableSchema(obj.getString("TABLE_SCHEMA"));
    				jhfwMbFileSheet.setTableName(obj.getString("TABLENAME"));
    				jhfwMbFileSheetMapper.updateByPrimaryKey(jhfwMbFileSheet);
    			}
			}
		}else {
			jhfwMbFile=new JhfwMbFile();
			Map map=jdbcDao.queryformap("select SEQ_MB.nextval nextvalue from dual");
			jhfwMbFile.setLrrq(new Date());
			mbid=map.get("nextvalue").toString();
			jhfwMbFile.setMbId(mbid);
			jhfwMbFile.setMbLx((String) mbFormData.get("mblx"));
			jhfwMbFile.setMbMc((String) mbFormData.get("mbmc"));
			jhfwMbFile.setMbMs((String) mbFormData.get("mbms"));
			jhfwMbFile.setYxBj(((String) mbFormData.get("yxbj")).equals("on")?"1":"0");
			jhfwMbFileMapper.insert(jhfwMbFile);
			for(Object o:tabledata) {
    			obj=(JSONObject)o;
				jhfwMbFileSheet=new JhfwMbFileSheet();
				jhfwMbFileSheet.setMbId(mbid);
				jhfwMbFileSheet.setQshs(obj.getShort("QSHS"));
				jhfwMbFileSheet.setQsls(obj.getShort("QSLS"));
				jhfwMbFileSheet.setSheetIndex(obj.getShort("SHEET_INDEX"));
				jhfwMbFileSheet.setTableMs(obj.getString("TABLE_MS"));
				jhfwMbFileSheet.setTableSchema(obj.getString("TABLE_SCHEMA"));
				jhfwMbFileSheet.setTableName(obj.getString("TABLENAME"));
				jhfwMbFileSheetMapper.insert(jhfwMbFileSheet);
			}
			
		}
    	resMap.put("code", "1");
    	restrnMap.put("JSONDATA",JsonUtil.toJson(resMap));
    	resEvent.setResMap(restrnMap);
    	return resEvent;
    }
    @SuppressWarnings({"rawtypes", "unchecked" })
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEvent deleteMb(RequestEvent requestEvent) throws Exception {
    	LogWritter.bizDebug("deleteMb");
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap resMap=new HashMap();
    	HashMap restrnMap=new HashMap();
    	try {
    		HashMap reqMap=(HashMap) requestEvent.getRequestMap();
    		String mbid=(String) reqMap.get("mbid");
    		jhfwMbFileColmxMapper.deleteByMbId(mbid);
    		jhfwMbFileSheetMapper.deleteByMbId(mbid);
    		jhfwMbFileMapper.deleteByPrimaryKey(mbid);
    		resMap.put("code", "1");
    	}catch(Exception e) {
    		resMap.put("code", "0");
    	}
    	restrnMap.put("JSONDATA",JsonUtil.toJson(resMap));
    	resEvent.setResMap(restrnMap);
    	return resEvent;
    }
    @SuppressWarnings({"rawtypes", "unchecked" })
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEvent deleteMbSheet(RequestEvent requestEvent) throws Exception {
    	LogWritter.bizDebug("deleteMbSheet");
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap resMap=new HashMap();
    	HashMap restrnMap=new HashMap();
		HashMap reqMap=(HashMap) requestEvent.getRequestMap();
		String mbid=(String) reqMap.get("mbid");
		Short sheetIndex=new Short((String)reqMap.get("sheetIndex"));
		JhfwMbFileSheet jhfwMbFileSheetKey=new JhfwMbFileSheet();
		jhfwMbFileSheetKey.setMbId(mbid);
		jhfwMbFileSheetKey.setSheetIndex(sheetIndex);
		jhfwMbFileSheetMapper.deleteByPrimaryKey(jhfwMbFileSheetKey) ;
		jhfwMbFileColmxMapper.deleteByMbIdAndSheetIndex(mbid, sheetIndex);
		resMap.put("code", "1");
    	restrnMap.put("JSONDATA",JsonUtil.toJson(resMap));
    	resEvent.setResMap(restrnMap);
    	return resEvent;
    }
    @SuppressWarnings({"rawtypes", "unchecked" })
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEvent saveMbSheetColmx(RequestEvent requestEvent) throws Exception {
    	LogWritter.bizDebug("保存saveMbSheetColmx");
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap resMap=new HashMap();
    	HashMap restrnMap=new HashMap();
    	JSONObject obj=null;
    	HashMap reqMap=(HashMap) requestEvent.getRequestMap();
    	String mbid=(String) reqMap.get("mbid");
    	Short sheetIndex=new Short( (String) reqMap.get("sheetIndex"));
    	try {
		jhfwMbFileColmxMapper.deleteByMbIdAndSheetIndex(mbid,sheetIndex);
    	JSONArray tabledata=(JSONArray)JSONArray.parse((String) reqMap.get("tabledata"));
    	JhfwMbFileColmx jhfwMbFileColmx=null;
    	for(Object o:tabledata) {
			if(o instanceof JSONArray) {
				continue;
			}
			obj=(JSONObject)o;
			jhfwMbFileColmx=new JhfwMbFileColmx();
			jhfwMbFileColmx.setMbId(mbid);
			jhfwMbFileColmx.setSheetIndex(obj.getShort("SHEET_INDEX"));
			jhfwMbFileColmx.setTableName(obj.getString("TABLE_NAME"));
			jhfwMbFileColmx.setExcelColmc(obj.getString("EXCEL_COLMC"));
			jhfwMbFileColmx.setZdMc(obj.getString("ZD_MC"));
			jhfwMbFileColmx.setDbColmc(obj.getString("DB_COLMC"));
			jhfwMbFileColmx.setDbColmcType(obj.getString("DB_COLMC_TYPE"));
			jhfwMbFileColmx.setDbColmcLen(obj.getShort("DB_COLMC_LEN"));
			jhfwMbFileColmx.setZfBj(obj.getString("ZF_BJ"));
			jhfwMbFileColmxMapper.insert(jhfwMbFileColmx);
    	}
    	     resMap.put("code", "1");
    	}catch(Exception e) {
    		 resMap.put("code", "-1");
    		 resMap.put("errmsg", e.toString());
    	}
    	restrnMap.put("JSONDATA",JsonUtil.toJson(resMap));
    	resEvent.setResMap(restrnMap);
    	return resEvent;
    }
}
