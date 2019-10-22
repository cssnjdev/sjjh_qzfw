package com.cwks.biz.demo.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "T_JHFW_MB_FILE")
public class JhfwMbFile {
    @Id
    @Column(name = "MB_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String mbId;

    @Column(name = "MB_MC")
    private String mbMc;

    @Column(name = "MB_MS")
    private String mbMs;

    @Column(name = "YWDL_DM")
    private String ywdlDm;

    @Column(name = "YWXL_DM")
    private String ywxlDm;

    @Column(name = "YX_BJ")
    private String yxBj;

    @Column(name = "MB_LX")
    private String mbLx;

    @Column(name = "LRRQ")
    private Date lrrq;

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
     * @return MB_MC
     */
    public String getMbMc() {
        return mbMc;
    }

    /**
     * @param mbMc
     */
    public void setMbMc(String mbMc) {
        this.mbMc = mbMc == null ? null : mbMc.trim();
    }

    /**
     * @return MB_MS
     */
    public String getMbMs() {
        return mbMs;
    }

    /**
     * @param mbMs
     */
    public void setMbMs(String mbMs) {
        this.mbMs = mbMs == null ? null : mbMs.trim();
    }

    /**
     * @return YWDL_DM
     */
    public String getYwdlDm() {
        return ywdlDm;
    }

    /**
     * @param ywdlDm
     */
    public void setYwdlDm(String ywdlDm) {
        this.ywdlDm = ywdlDm == null ? null : ywdlDm.trim();
    }

    /**
     * @return YWXL_DM
     */
    public String getYwxlDm() {
        return ywxlDm;
    }

    /**
     * @param ywxlDm
     */
    public void setYwxlDm(String ywxlDm) {
        this.ywxlDm = ywxlDm == null ? null : ywxlDm.trim();
    }

    /**
     * @return YX_BJ
     */
    public String getYxBj() {
        return yxBj;
    }

    /**
     * @param yxBj
     */
    public void setYxBj(String yxBj) {
        this.yxBj = yxBj == null ? null : yxBj.trim();
    }

    /**
     * @return MB_LX
     */
    public String getMbLx() {
        return mbLx;
    }

    /**
     * @param mbLx
     */
    public void setMbLx(String mbLx) {
        this.mbLx = mbLx == null ? null : mbLx.trim();
    }

    /**
     * @return LRRQ
     */
    public Date getLrrq() {
        return lrrq;
    }

    /**
     * @param lrrq
     */
    public void setLrrq(Date lrrq) {
        this.lrrq = lrrq;
    }
}