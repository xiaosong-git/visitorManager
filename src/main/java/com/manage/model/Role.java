package com.manage.model;

import com.manage.model.base.Base;

import javax.persistence.*;
import java.util.List;

@Table(name = "t_role")
public class Role extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role_name;

    private Long sid;

    private String role_relation_no;

    //非数据库映射字段
    @Transient
    private List<Menu> menus;
    @Transient
    private String s_name;

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
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
     * @return role_name
     */
    public String getRole_name() {
        return role_name;
    }

    /**
     * @param role_name
     */
    public void setRole_name(String role_name) {
        this.role_name = role_name == null ? null : role_name.trim();
    }

    /**
     * @return sid
     */
    public Long getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(Long sid) {
        this.sid = sid;
    }

    /**
     * @return role_relation_no
     */
    public String getRole_relation_no() {
        return role_relation_no;
    }

    /**
     * @param role_relation_no
     */
    public void setRole_relation_no(String role_relation_no) {
        this.role_relation_no = role_relation_no == null ? null : role_relation_no.trim();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role_name='" + role_name + '\'' +
                ", sid=" + sid +
                ", role_relation_no='" + role_relation_no + '\'' +
                ", menus=" + menus +
                '}';
    }
}