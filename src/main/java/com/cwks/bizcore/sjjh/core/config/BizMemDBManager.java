package com.cwks.bizcore.sjjh.core.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cwks.common.core.systemConfig.SpringContextUtil;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.core.ext.memdb.DBConnectionManager;
import com.cwks.common.log.LogWritter;
import com.cwks.common.core.systemConfig.Manager;


/**
 * <p>File: BizMemDBManager.java</p>
 * <p>Title: 业务缓存库初始化</p>
 * <p>Description: 业务缓存库初始化</p>
 *
 * @author 胡锐
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class BizMemDBManager implements Manager {

	private static final long serialVersionUID = 1L;
	
	private JdbcDao idao = (JdbcDao) SpringContextUtil.getBean("JdbcDao");
    
	@Override
	public void runJob() {
		LogWritter.sysInfo("**** BizMemDBManager start.......");
		PreparedStatement ps=null;
		Connection ncConnect =null;
		try {
			ncConnect = DBConnectionManager.getInstance().getConnection();
			if(ncConnect != null){
				// 初始化menDB，建表。
				StringBuffer sqlcreate = new StringBuffer();
				sqlcreate.append("create table T_XT_HCBXX\n");
				sqlcreate.append("(\n");
				sqlcreate.append("  gx_xh        VARCHAR(10) not null,\n");
				sqlcreate.append("  bm_mc        VARCHAR(50) not null,\n");
				sqlcreate.append("  ds_mc        VARCHAR(50),\n");
				sqlcreate.append("  xy_bj        CHAR(1),\n");
				sqlcreate.append("  read_order   VARCHAR(200),\n");
				sqlcreate.append("  hclx         CHAR(1) not null,\n");
				sqlcreate.append("  bm_ms        VARCHAR(2000) not null,\n");
				sqlcreate.append("  column_id    VARCHAR(50),\n");
				sqlcreate.append("  column_value VARCHAR(50),\n");
				sqlcreate.append("  bm_sql       VARCHAR(2000),\n");
				sqlcreate.append("  schema       VARCHAR(50)\n");
				sqlcreate.append(")");
				ps = ncConnect.prepareStatement(sqlcreate.toString());
				ps.executeUpdate();
				/******创建数据交换缓存表********/
				sqlcreate = new StringBuffer();
				sqlcreate.append("create table jh_bho_exchangedata(jyuuid VARCHAR(36),data VARCHAR(4000))");
				ps = ncConnect.prepareStatement(sqlcreate.toString());
				ps.executeUpdate();
				ps.close();
				ncConnect.close();
				LogWritter.sysInfo("**** BizMemDBManager success ****");
			}
		} catch (Exception e) {
			closeResource(ps,ncConnect);
			LogWritter.sysError("**** BizMemDBManager 出错 ****", e);
			return;
		}finally{
			closeResource(ps,ncConnect);
		}
	}
	private void closeResource(PreparedStatement ps,Connection ncConnect) {
		try {
			if(ps != null){
				ps.close();
			}
			if(ncConnect != null){
				ncConnect.close();
			}
		} catch (SQLException e) {
		}
	}

}