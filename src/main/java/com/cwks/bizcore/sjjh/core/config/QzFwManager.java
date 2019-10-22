package com.cwks.bizcore.sjjh.core.config;

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
public class QzFwManager implements Manager {

	private static final long serialVersionUID = 1L;
	@Override
	public void runJob() {
		LogWritter.sysInfo("**** QzFwManager start.......");
		JhQzFwContext.singleton();
		LogWritter.sysInfo("**** QzFwManager end.......");
	}
}