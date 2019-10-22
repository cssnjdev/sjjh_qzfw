package com.cwks.biz.demo.dao;

import org.apache.ibatis.annotations.Param;

import com.cwks.biz.demo.domain.JhfwMbFileColmx;
import com.cwks.common.config.MyMapper;
import org.springframework.context.annotation.Primary;

@Primary
public interface JhfwMbFileColmxMapper extends MyMapper<JhfwMbFileColmx> {
	int deleteByMbId(String mbId);
    int deleteByMbIdAndSheetIndex(@Param("mbId") String mbId,@Param("sheetIndex") Short sheetIndex);
}