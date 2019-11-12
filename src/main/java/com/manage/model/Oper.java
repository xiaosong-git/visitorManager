package com.manage.model;

import com.manage.model.base.Base;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;

@Table(name = "t_oper")
@NameStyle(value = Style.normal)
public class Oper extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oper_code;

    private String oper_name;

    private Long org_id;

    private Long role_id;

    private String org_relation_no;

    private String pwd;

    private String sstatus;

    private String login_name;

    //非数据库字段
    @Transient
    private String token;
    @Transient
    private String org_name;
    @Transient
    private String statusName;
    @Transient
    private String roleName;
    @Transient
    private String imageServerUrl;
    @Transient
    private String orgType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getImageServerUrl() {
        return imageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.imageServerUrl = imageServerUrl;
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
     * @return oper_code
     */
    public String getOper_code() {
        return oper_code;
    }

    /**
     * @param oper_code
     */
    public void setOper_code(String oper_code) {
        this.oper_code = oper_code == null ? null : oper_code.trim();
    }

    /**
     * @return oper_name
     */
    public String getOper_name() {
        return oper_name;
    }

    /**
     * @param oper_name
     */
    public void setOper_name(String oper_name) {
        this.oper_name = oper_name == null ? null : oper_name.trim();
    }

    /**
     * @return org_id
     */
    public Long getOrg_id() {
        return org_id;
    }

    /**
     * @param org_id
     */
    public void setOrg_id(Long org_id) {
        this.org_id = org_id;
    }

    /**
     * @return role_id
     */
    public Long getRole_id() {
        return role_id;
    }

    /**
     * @param role_id
     */
    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    /**
     * @return org_relation_no
     */
    public String getOrg_relation_no() {
        return org_relation_no;
    }

    /**
     * @param org_relation_no
     */
    public void setOrg_relation_no(String org_relation_no) {
        this.org_relation_no = org_relation_no == null ? null : org_relation_no.trim();
    }

    /**
     * @return pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    /**
     * @return sstatus
     */
    public String getSstatus() {
        return sstatus;
    }

    /**
     * @param sstatus
     */
    public void setSstatus(String sstatus) {
        this.sstatus = sstatus == null ? null : sstatus.trim();
    }

    /**
     * @return login_name
     */
    public String getLogin_name() {
        return login_name;
    }

    /**
     * @param login_name
     */
    public void setLogin_name(String login_name) {
        this.login_name = login_name == null ? null : login_name.trim();
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Override
    public String toString() {
        return "Oper{" +
                "id=" + id +
                ", oper_code='" + oper_code + '\'' +
                ", oper_name='" + oper_name + '\'' +
                ", org_id=" + org_id +
                ", role_id=" + role_id +
                ", org_relation_no='" + org_relation_no + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sstatus='" + sstatus + '\'' +
                ", login_name='" + login_name + '\'' +
                ", token='" + token + '\'' +
                ", org_name='" + org_name + '\'' +
                ", statusName='" + statusName + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}