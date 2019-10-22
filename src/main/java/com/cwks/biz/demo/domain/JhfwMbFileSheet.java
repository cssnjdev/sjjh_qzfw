package com.cwks.biz.demo.domain;

import javax.persistence.*;

@Table(name = "T_JHFW_MB_FILE_SHEET")
public class JhfwMbFileSheet {
    @Id
    @Column(name = "MB_ID")
    private String mbId;

    @Id
    @Column(name = "SHEET_INDEX")
    private Short sheetIndex;

    @Column(name = "TABLE_NAME")
    private String tableName;

    @Column(name = "QSHS")
    private Short qshs;

    @Column(name = "QSLS")
    private Short qsls;

    @Column(name = "TABLE_MS")
    private String tableMs;

    @Column(name = "TABLE_SCHEMA")
    private String tableSchema;

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
     * @return QSHS
     */
    public Short getQshs() {
        return qshs;
    }

    /**
     * @param qshs
     */
    public void setQshs(Short qshs) {
        this.qshs = qshs;
    }

    /**
     * @return QSLS
     */
    public Short getQsls() {
        return qsls;
    }

    /**
     * @param qsls
     */
    public void setQsls(Short qsls) {
        this.qsls = qsls;
    }

    /**
     * @return TABLE_MS
     */
    public String getTableMs() {
        return tableMs;
    }

    /**
     * @param tableMs
     */
    public void setTableMs(String tableMs) {
        this.tableMs = tableMs == null ? null : tableMs.trim();
    }

    /**
     * @return TABLE_SCHEMA
     */
    public String getTableSchema() {
        return tableSchema;
    }

    /**
     * @param tableSchema
     */
    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema == null ? null : tableSchema.trim();
    }
}