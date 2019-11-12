package com.manage.model;

import javax.persistence.*;

@Table(name = "tbl_terminal")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备号
     */
    private String terminalNo;

    /**
     * 设备状态 start:开启，stop:关闭
     */
    private String terminalCstatus;

    /**
     * 设备备注
     */
    private String remark;

    /**
     * 大楼id
     */
    private String orgId;

    //扩展
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
     * 获取设备号
     *
     * @return terminalNo - 设备号
     */
    public String getTerminalNo() {
        return terminalNo;
    }

    /**
     * 设置设备号
     *
     * @param terminalNo 设备号
     */
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo == null ? null : terminalNo.trim();
    }

    /**
     * 获取设备状态 start:开启，stop:关闭
     *
     * @return terminalCstatus - 设备状态 start:开启，stop:关闭
     */
    public String getTerminalCstatus() {
        return terminalCstatus;
    }

    /**
     * 设置设备状态 start:开启，stop:关闭
     *
     * @param terminalCstatus 设备状态 start:开启，stop:关闭
     */
    public void setTerminalCstatus(String terminalCstatus) {
        this.terminalCstatus = terminalCstatus == null ? null : terminalCstatus.trim();
    }

    /**
     * 获取设备备注
     *
     * @return remark - 设备备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置设备备注
     *
     * @param remark 设备备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取大楼id
     *
     * @return orgId - 大楼id
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 设置大楼id
     *
     * @param orgId 大楼id
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}