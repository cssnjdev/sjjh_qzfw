package com.cwks.biz.demo.dao;

import com.cwks.biz.demo.domain.JhfwMbFileSheet;
import com.cwks.common.config.MyMapper;

public interface JhfwMbFileSheetMapper extends MyMapper<JhfwMbFileSheet> {
	 int deleteByMbId(String mbId);
}