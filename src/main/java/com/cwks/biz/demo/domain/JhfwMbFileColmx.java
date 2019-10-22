package com.cwks.biz.demo.domain;

import javax.persistence.*;

@Table(name = "T_JHFW_MB_FILE_COLMX")
public class JhfwMbFileColmx {
    @Id
    @Column(name = "MB_ID")
    private String mbId;

    @Id
    @Column(name = "SHEET_INDEX")
    private Short sheetIndex;

    @Id
    @Column(name = "DB_COLMC")
    private String dbColmc;

    @Column(name = "TABLE_NAME")
    private String tableName;

    @Column(name = "EXCEL_COLMC")
    private String excelColmc;

    @Column(name = "ZD_MC")
    private String zdMc;

    @Column(name = "ZF_BJ")
    private String zfBj;

    @Column(name = "DB_COLMC_TYPE")
    private String dbColmcType;

    @Column(name = "DB_COLMC_LEN")
    private Short dbColmcLen;

    /**
     * @return MB_ID
     */
    public String getMbId() {
        return mbId;
    }

    /**
     * @param mbId
     */
    public void setMbId(String mbId) {
        this.mbId = mbId == null ? null : mbId.trim();
    }

    /**
     * @return SHEET_INDEX
     */
    public Short getSheetIndex() {
        return sheetIndex;
    }

    /**
     * @param sheetIndex
     */
    public void setSheetIndex(Short sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    /**
     * @return DB_COLMC
     */
    public String getDbColmc() {
        return dbColmc;
    }

    /**
     * @param dbColmc
     */
    public void setDbColmc(String dbColmc) {
        this.dbColmc = dbColmc == null ? null : dbColmc.trim();
    }

    /**
     * @return TABLE_NAME
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * @return EXCEL_COLMC
     */
    public String getExcelColmc() {
        return excelColmc;
    }

    /**
     * @param excelColmc
     */
    public void setExcelColmc(String excelColmc) {
        this.excelColmc = excelColmc == null ? null : excelColmc.trim();
    }

    /**
     * @return ZD_MC
     */
    public String getZdMc() {
        return zdMc;
    }

    /**
     * @param zdMc
     */
    public void setZdMc(String zdMc) {
        this.zdMc = zdMc == null ? null : zdMc.trim();
    }

    /**
     * @return ZF_BJ
     */
    public String getZfBj() {
        return zfBj;
    }

    /**
     * @param zfBj
     */
    public void setZfBj(String zfBj) {
        this.zfBj = zfBj == null ? null : zfBj.trim();
    }

    /**
     * @return DB_COLMC_TYPE
     */
    public String getDbColmcType() {
        return dbColmcType;
    }

    /**
     * @param dbColmcType
     */
    public void setDbColmcType(String dbColmcType) {
        this.dbColmcType = dbColmcType == null ? null : dbColmcType.trim();
    }

    /**
     * @return DB_COLMC_LEN
     */
    public Short getDbColmcLen() {
        return dbColmcLen;
    }

    /**
     * @param dbColmcLen
     */
    public void setDbColmcLen(Short dbColmcLen) {
        this.dbColmcLen = dbColmcLen;
    }
}