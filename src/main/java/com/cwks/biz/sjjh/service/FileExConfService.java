package com.cwks.biz.sjjh.service;

import com.cwks.bizcore.sjjh.core.config.JhQzFwContext;
import com.cwks.bizcore.sjjh.core.utils.db.DataSourceUtils;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.log.LogWritter;
import com.cwks.common.core.systemConfig.BizErrorMsgContext;
import com.cwks.common.core.systemConfig.SystemContext;
import com.cwks.bizcore.comm.utils.JsonUtil;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>File: FileExConfService.java</p>
 * <p>Title: FileExConfService</p>
 * <p>Description: 数据交换前置服务根据excel文件配置生成业务表的逻辑service</p>
 *
 * @author cssnj
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Component
@Transactional(timeout = 599)
public class FileExConfService {


    /**
     * <p>方法名：generate_metadata_table</p>
     * <p>Description:数据交换前置服务根据excel文件配置生成业务表的逻辑处理</p>
     *
     * @version 1.0
     */
    @SuppressWarnings("rawtypes")
    public ResponseEvent generate_metadata_table(RequestEvent requestEvent) throws Exception {
        LogWritter.bizDebug(this.getClass().getName() + ".generate_metadata_table 方法开始");
        ResponseEvent resEvent = new ResponseEvent();
        if (requestEvent.getReqCurMap() == null) {
            resEvent.setResCode(-1);
            resEvent.setResMsg("请求的对象为空，处理失败!");
            resEvent.setResStr("");
        }
        ConcurrentHashMap reqMap = requestEvent.getReqCurMap();
        if (reqMap.get("REQUSET_MESSAGE") == null || "".equals(reqMap.get("REQUSET_MESSAGE"))) {
            resEvent.setResCode(-1);
            resEvent.setResMsg("请求的报文为空，处理失败!");
            resEvent.setResStr("");
        }
        Map reqmap = JsonUtil.toMap((String) reqMap.get("REQUSET_MESSAGE"));
        String dataSource_conf_id=null,dataSource_biz_id=null,mb_id=null;
        if(reqmap != null){
            if(reqmap.get("DS_CONF_ID") != null){
                dataSource_conf_id = (String)reqmap.get("DS_CONF_ID");
            }
            if(reqmap.get("DS_BIZ_ID") != null){
                dataSource_biz_id = (String)reqmap.get("DS_BIZ_ID");
            }
            if(reqmap.get("MB_ID") != null){
                mb_id = (String)reqmap.get("MB_ID");
            }
        }

        ConcurrentHashMap dataSourceMap = JhQzFwContext.singleton().getDataSourceMap();
        String db_type = null;
        if(dataSourceMap.get(dataSource_biz_id) != null){
            ConcurrentHashMap<String, String> db_info_biz_Map = (ConcurrentHashMap)dataSourceMap.get(dataSource_biz_id);
            if(db_info_biz_Map.get("driverClassName") != null){
                String driverClassName = db_info_biz_Map.get("driverClassName");
                if(driverClassName.indexOf("mysql") != -1){
                    db_type = "MYSQL";
                }else if(driverClassName.indexOf("OracleDriver") != -1){
                    db_type = "ORACLE";
                }else{
                    db_type = "ORACLE";
                }
            }
        }

        DataSourceUtils dataSourceUtils_conf=new DataSourceUtils(dataSource_conf_id);
        DataSourceUtils dataSourceUtils_biz=null;
        if(!dataSource_conf_id.equals(dataSource_biz_id)){
            dataSourceUtils_biz = dataSourceUtils_conf;
        }else{
            dataSourceUtils_biz=new DataSourceUtils(dataSource_biz_id);
        }
        TransactionStatus status_conf =null,status_biz =null;
        DataSourceTransactionManager transactionManager_conf = null,transactionManager_biz = null;
        DataSource dataSource_conf = dataSourceUtils_conf.getDataSource();
        DataSource dataSource_biz = dataSourceUtils_biz.getDataSource();
        transactionManager_conf = new DataSourceTransactionManager(dataSource_conf);
        transactionManager_biz = new DataSourceTransactionManager(dataSource_biz);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();//事务定义类
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(500);
        status_conf = transactionManager_conf.getTransaction(def);// 返回事务对象
        status_biz = transactionManager_biz.getTransaction(def);// 返回事务对象
        Connection conn_biz = dataSource_biz.getConnection();
        Statement st = null;
        st = conn_biz.createStatement();
        try {
            //获取模板配置表信息
            ArrayList params = new ArrayList();
            params.add(mb_id);
            List<Map> result = dataSourceUtils_conf.queryforlist(SystemContext.getSql("SQL_JHDL_GET_FILE_CONFIG_EXCEL_SHEET"), params);
            if (result != null && result.size() > 0) {
                Map map_tab = null;
                String table_name = null,table_ms = null,table_schema = null,sql_tab_ms_tmp = null,sql_col_ms_create = null,sql_col_edit = null,sheet_index=null;
                StringBuffer sbf_sqlcreate = null;
                StringBuffer sbf_sqledit = null;
                PreparedStatement ps = null;
                List<Map> result_conf = null;
                ArrayList params_mx = null;
                ArrayList params_tab_mx = null;
                ArrayList ms_list = null;
                ArrayList dbColNameColList = null;
                ArrayList dbConfColNameColList = null;
                List<?> colList = null,tabCommList=null;
                for(int i=0;i<result.size();i++){
                    map_tab = result.get(i);
                    table_name = (String)map_tab.get("table_name");
                    table_ms = (String)map_tab.get("table_ms");
                    table_schema = (String)map_tab.get("table_schema");
                    sheet_index =map_tab.get("sheet_index").toString();
                    if(table_name != null && !"".equals(table_name) && table_schema != null && !"".equals(table_schema) ){
                        try {
                            colList = null;
                            tabCommList=null;
                            params_tab_mx = new ArrayList();
                            if(db_type != null && "MYSQL".equals(db_type)){
                                if(table_schema != null && !"".equals(table_schema)){
                                    params_tab_mx.add(table_name);
                                    params_tab_mx.add(table_schema);
                                    colList = dataSourceUtils_biz.queryforlist(SystemContext.getSql("SQL_JHDL_GET_TABLE_STRUCTURE_BY_TBNAME_OWNER_MYSQL"), params_tab_mx);
                                    tabCommList = dataSourceUtils_biz.queryforlist(SystemContext.getSql("SQL_JHDL_GET_TABLE_COMMENTS_BY_TBNAME_MYSQL"), params_tab_mx);
                                }else{
                                    params_tab_mx.add(table_name);
                                    colList = dataSourceUtils_biz.queryforlist(SystemContext.getSql("SQL_JHDL_GET_TABLE_STRUCTURE_BY_TBNAME_MYSQL"), params_tab_mx);
                                    tabCommList = dataSourceUtils_biz.queryforlist(SystemContext.getSql("SQL_JHDL_GET_TABLE_COMMENTS_BY_TBNAME_MYSQL"), params_tab_mx);
                                }
                            }else{
                                if(table_schema != null && !"".equals(table_schema)){
                                    params_tab_mx.add(table_name.toUpperCase());
                                    params_tab_mx.add(table_schema.toUpperCase());
                                    colList = dataSourceUtils_biz.queryforlist(SystemContext.getSql("SQL_JHDL_GET_TABLE_STRUCTURE_BY_TBNAME_OWNER_ORACLE"), params_tab_mx);
                                    tabCommList = dataSourceUtils_biz.queryforlist(SystemContext.getSql("SQL_JHDL_GET_TABLE_COMMENTS_BY_TBNAME_ORACLE"), params_tab_mx);
                                }else{
                                    params_tab_mx.add(table_name.toUpperCase());
                                    colList = dataSourceUtils_biz.queryforlist(SystemContext.getSql("SQL_JHDL_GET_TABLE_STRUCTURE_BY_TBNAME_ORACLE"), params_tab_mx);
                                    tabCommList = dataSourceUtils_biz.queryforlist(SystemContext.getSql("SQL_JHDL_GET_TABLE_COMMENTS_BY_TBNAME_ORACLE"), params_tab_mx);
                                }
                            }
                            if(tabCommList != null && tabCommList.size() >0){
                                Map<?, ?> db_tabcomms_map = null;
                                Iterator iter_tab_comms = tabCommList.iterator();
                                while (iter_tab_comms.hasNext()) {
                                    db_tabcomms_map = (Map<?, ?>) iter_tab_comms.next();
                                    if(db_tabcomms_map.get("TABLE_NAME") != null && !"".equals(db_tabcomms_map.get("TABLE_NAME"))){
                                        if(!db_tabcomms_map.get("TABLE_COMMENT").equals(table_ms)){
                                            sql_col_edit = "comment on table " + table_schema+"."+table_name + " is '"+table_ms+"'" ;
                                            st.addBatch(sql_col_edit);
                                        }
                                    }
                                }
                            }
                            String conf_column_name = null,conf_column_ms = null,conf_data_type =null,conf_data_length =null;
                            params_mx = new ArrayList();
                            params_mx.add(mb_id);
                            params_mx.add(table_name);
                            params_mx.add(sheet_index);
                            result_conf = dataSourceUtils_conf.queryforlist(SystemContext.getSql("SQL_JHDL_GET_FILE_CONFIG_EXCEL_COLMX"), params_mx);
                            Iterator iter_conf = result_conf.iterator();
                            Map<?, ?> db_resmap = null;
                            Map<?, ?> db_conf_resmap = null;
                            //如果当前库里已经有改表的情况下
                            if(colList != null && colList.size() >0){
                                Iterator iter_db = colList.iterator();
                                String db_column_name = null,db_data_type =null,db_data_length =null,db_column_ms =null;
                                Long db_data_scale =null;
                                dbColNameColList = new ArrayList();
                                dbConfColNameColList = new ArrayList();
                                //构造完整的数据库现有表字段列表
                                if (iter_db.hasNext()) {
                                    while (iter_db.hasNext()) {
                                        db_resmap = (Map<?, ?>) iter_db.next();
                                        dbColNameColList.add(((String) db_resmap.get("column_name")).toUpperCase());
                                    }
                                }
                                //构造完整的数据库配置的表的字段列表
                                if (iter_conf.hasNext()) {
                                    while (iter_conf.hasNext()) {
                                        db_conf_resmap = (Map<?, ?>) iter_conf.next();
                                        dbConfColNameColList.add(((String) db_conf_resmap.get("DB_COLMC")).toUpperCase());
                                    }
                                }
                                iter_db = colList.iterator();
                                while (iter_db.hasNext()) {
                                    db_resmap = (Map<?, ?>) iter_db.next();
                                    db_column_name = (String) db_resmap.get("column_name");
                                    db_data_type = (String) db_resmap.get("data_type");
                                    db_data_scale = db_resmap.get("data_scale") == null ? null : new BigDecimal(db_resmap.get("data_scale").toString()).longValue();
                                    db_data_length = db_resmap.get("data_length") == null ? "" : db_resmap.get("data_length").toString();
                                    db_column_ms = db_resmap.get("comments") == null ? "" : db_resmap.get("comments").toString();
                                    if (result_conf != null && result_conf.size() > 0) {
                                        //st = conn_biz.createStatement();
                                        ms_list = new ArrayList();
                                        //配置表的字段不包含目前数据库中现有表的这个字段
                                        if(!dbConfColNameColList.contains(db_column_name)){
                                            sql_col_edit = "alter table " + table_schema+"."+table_name +" drop column "+db_column_name.toLowerCase();
                                            st.addBatch(sql_col_edit);
                                        }else{//配置表的字段包含目前数据库中现有表的这个字段
                                        	iter_conf = result_conf.iterator();
                                            while (iter_conf.hasNext()) {
                                            	conf_column_name = (String) db_conf_resmap.get("DB_COLMC");
                                                db_conf_resmap = (Map<?, ?>) iter_conf.next();
                                                conf_column_ms = (String) db_conf_resmap.get("ZD_MC");
                                                conf_data_type = (String) db_conf_resmap.get("db_colmc_type");
                                                conf_data_length = db_conf_resmap.get("db_colmc_len")+"";
                                                if(db_column_name.toUpperCase().equals(conf_column_name.toUpperCase()) && db_data_type.toUpperCase().equals(conf_data_type.toUpperCase())
                                                        && db_data_length.equals(conf_data_length) && db_column_ms.equals(conf_column_ms)){
                                                    continue;
                                                }else{//有配置修改的地方
                                                    //表字段存在但其余属性发生变化
                                                    if(db_column_name.toUpperCase().equals(conf_column_name.toUpperCase())){
                                                        //修改了字段类型或字段长度
                                                        if(!db_data_type.toUpperCase().equals(conf_data_type.toUpperCase()) || !db_data_length.equals(conf_data_length)){
                                                            sbf_sqledit = new StringBuffer("alter table ").append(table_schema).append(".").append(table_name.toUpperCase()).append(" modify ").append(conf_column_name.toLowerCase());
                                                            sbf_sqledit = getDbCreatSql(conf_data_type,conf_data_length,sbf_sqledit);
                                                            st.addBatch(sbf_sqledit.toString());
                                                        }
                                                        //修改了字段描述
                                                        if(!db_column_ms.equals(conf_column_ms)){
                                                            sql_col_edit = "comment on column " + table_schema+"."+table_name + "."+conf_column_name.toLowerCase()+" is '"+conf_column_ms+"'" ;
                                                            st.addBatch(sql_col_edit);
                                                        }
                                                    }else{//字段不匹配的情况
                                                        //目前已建库表中包含了该字段
                                                        if(dbColNameColList.contains(conf_column_name.toUpperCase())){
                                                            continue;
                                                        }else{//没有包含该字段，则新增字段
                                                            if(conf_column_name != null && !"".equals(conf_column_name) && conf_data_type != null && !"".equals(conf_data_type)){
                                                                sbf_sqledit = new StringBuffer("alter table ").append(table_schema).append(".").append(table_name.toUpperCase()).append(" add ").append(conf_column_name.toLowerCase());
                                                                sbf_sqledit = getDbCreatSql(conf_data_type,conf_data_length,sbf_sqledit);
                                                                st.addBatch(sbf_sqledit.toString());
                                                                if(conf_column_ms != null && !"".equals(conf_column_ms)){
                                                                    sql_col_edit = "comment on column " + table_schema+"."+table_name + "."+conf_column_name.toLowerCase()+" is '"+conf_column_ms+"'" ;
                                                                    st.addBatch(sql_col_edit);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        // 执行批处理sql
                                        st.executeBatch();
                                        //st.close();
                                    }
                                }
                            }else{//数据库中没有这张表
                                sbf_sqlcreate = new StringBuffer("create table " + table_schema+"."+table_name + " (");
                                sql_tab_ms_tmp = "comment on table " + table_schema+"."+table_name + " is '"+table_ms+"'" ;
                                if (result_conf != null && result_conf.size() > 0) {
                                    //st = conn_biz.createStatement();
                                    ms_list = new ArrayList();
                                    while (iter_conf.hasNext()) {
                                        db_conf_resmap = (Map<?, ?>) iter_conf.next();
                                        conf_column_name = (String) db_conf_resmap.get("DB_COLMC");
                                        conf_column_ms = (String) db_conf_resmap.get("ZD_MC");
                                        conf_data_type = (String) db_conf_resmap.get("db_colmc_type");
                                        conf_data_length = db_conf_resmap.get("db_colmc_len")+"";
                                        sql_col_ms_create = "comment on column " + table_schema+"."+table_name + "."+conf_column_name.toLowerCase()+" is '"+conf_column_ms+"'" ;
                                        ms_list.add(sql_col_ms_create);
                                        sbf_sqlcreate.append(conf_column_name.toLowerCase() + " ");
                                        sbf_sqlcreate = getDbCreatSql(conf_data_type,conf_data_length,sbf_sqlcreate);
                                        sbf_sqlcreate.append(", ");
                                    }
                                    sbf_sqlcreate.append("#");
                                    sbf_sqlcreate.append(")");

                                    // 添加批处理sql
                                    st.addBatch(sbf_sqlcreate.toString().replaceAll(", #", ""));
                                    st.addBatch(sql_tab_ms_tmp);

                                    for(int x=0;x<ms_list.size();x++){
                                        st.addBatch((String)ms_list.get(x));
                                    }
                                    // 执行批处理sql
                                    st.executeBatch();
                                }
                            }
                        } catch (Exception e) {
                            LogWritter.sysError("更新创建表失败，错误：", e);
                            resEvent.setResCode(-1);
                            resEvent.setResMsg("更新创建表失败，错误："+e.getMessage());
                            resEvent.setResStr("");
                        } finally {
                            try {
//                                if(conn_biz != null){
//                                    conn_biz.close();;
//                                }
//                                if(st != null){
//                                    st.close();
//                                }
                            } catch (Exception ce) {
                                transactionManager_biz.rollback(status_biz);
                                LogWritter.sysError("更新创建表失败，关闭连接错误", ce);
                                resEvent.setResCode(-1);
                                resEvent.setResMsg("更新创建表失败，关闭连接错误"+ce.getMessage());
                                resEvent.setResStr("");
                            }
                        }
                    }
                }
            }
            st.clearBatch();
        }catch (Exception e) {
            transactionManager_biz.rollback(status_biz);
            LogWritter.bizError("error: "+this.getClass().getName()+".dealBiz方法异常：",e);
            resEvent.setResStr(BizErrorMsgContext.singleton().getValueAsString("BIZ.SJJHPT.QZFW.WBWSFW.ERR_00_0009")+","+e.toString());
            resEvent.setResCode(-1);
            return resEvent;
        }finally {
        	if(st != null){
              st.close();
            }
        	if(conn_biz != null){
                conn_biz.close();;
            }
//            transactionManager_conf.commit(status_conf);
//            transactionManager_biz.commit(status_biz);
            dataSourceUtils_conf.closeConnection();
            dataSourceUtils_biz.closeConnection();
        }
        resEvent.setResCode(resEvent.getResCode());
        resEvent.setResMsg(resEvent.getResMsg());
        resEvent.setResStr(resEvent.getResStr());
        LogWritter.bizDebug(this.getClass().getName() + ".generate_metadata_table 方法结束");
        return resEvent;
    }

    private static Timestamp getOracleTimestamp(Object value) {
        try {
            Class clz = value.getClass();
            clz.getMethods();
            Method method = clz.getMethod("getTime", (Class<?>[]) null);
            return (Timestamp) method.invoke(value, (Object[]) null);
        } catch (Exception e) {
            return null;
        }
    }

    private StringBuffer getDbCreatSql(String  conf_data_type,String conf_data_length,StringBuffer sbf_sqledit) {
        if ("CHAR".equals(conf_data_type.toUpperCase())) {
            sbf_sqledit.append(" " + conf_data_type + " ");
            sbf_sqledit.append("  (" + conf_data_length + ")  ");
        } else if ("VARCHAR2".equals(conf_data_type.toUpperCase()) || "TEXT".equals(conf_data_type.toUpperCase())) {
            sbf_sqledit.append(" VARCHAR2(" + conf_data_length + ")  ");
        } else if ("VARCHAR".equals(conf_data_type.toUpperCase())) {
            sbf_sqledit.append(" VARCHAR(" + conf_data_length + ")  ");
        }else if ("LONGTEXT".equals(conf_data_type.toUpperCase())) {
            sbf_sqledit.append(" VARCHAR2(4000) ");
        }else if ("TIMESTAMP".equals(conf_data_type.toUpperCase()) || "DATETIME".equals(conf_data_type.toUpperCase())) {
            sbf_sqledit.append(" TIMESTAMP ");
        } else if ("NUMBER".equals(conf_data_type.toUpperCase()) || "INT".equals(conf_data_type.toUpperCase()) || "BIGINT".equals(conf_data_type.toUpperCase())) {
            sbf_sqledit.append(" NUMBER(" + conf_data_length + ")  ");
        }else {
            sbf_sqledit.append(" "+ conf_data_type + " ");
        }
        return sbf_sqledit;
    }
}
