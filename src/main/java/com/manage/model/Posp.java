package com.manage.model;

import javax.persistence.*;

@Table(name = "tbl_posp")
public class Posp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 大楼ID
     */
    private Long orgId;

    /**
     * 上位机编码
     */
    private String pospCode;

    /**
     * 上位机名称
     */
    private String pospName;

    /**
     * normal:正常  disable:禁止
     */
    private String cstatus;

    @Transient
    private String org_name;

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取大楼ID
     *
     * @return orgId - 大楼ID
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置大楼ID
     *
     * @param orgId 大楼ID
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取上位机编码
     *
     * @return pospCode - 上位机编码
     */
    public String getPospCode() {
        return pospCode;
    }

    /**
     * 设置上位机编码
     *
     * @param pospCode 上位机编码
     */
    public void setPospCode(String pospCode) {
        this.pospCode = pospCode == null ? null : pospCode.trim();
    }

    /**
     * 获取上位机名称
     *
     * @return pospName - 上位机名称
     */
    public String getPospName() {
        return pospName;
    }

    /**
     * 设置上位机名称
     *
     * @param pospName 上位机名称
     */
    public void setPospName(String pospName) {
        this.pospName = pospName == null ? null : pospName.trim();
    }

    /**
     * 获取normal:正常  disable:禁止
     *
     * @return cstatus - normal:正常  disable:禁止
     */
    public String getCstatus() {
        return cstatus;
    }

    /**
     * 设置normal:正常  disable:禁止
     *
     * @param cstatus normal:正常  disable:禁止
     */
    public void setCstatus(String cstatus) {
        this.cstatus = cstatus == null ? null : cstatus.trim();
    }
}