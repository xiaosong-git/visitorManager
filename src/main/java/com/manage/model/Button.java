package com.manage.model;

import com.manage.model.base.Base;

import javax.persistence.*;

@Table(name = "tbl_button")
public class Button extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "btnName")
    private String btnName;
    @Column(name = "btnCode")
    private String btnCode;
    @Column(name = "actionUrl")
    private String actionUrl;
    @Column(name = "menuId")
    private Long menuId;

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
     * @return btnName
     */
    public String getBtnName() {
        return btnName;
    }

    /**
     * @param btnName
     */
    public void setBtnName(String btnName) {
        this.btnName = btnName == null ? null : btnName.trim();
    }

    /**
     * @return btnCode
     */
    public String getBtnCode() {
        return btnCode;
    }

    /**
     * @param btnCode
     */
    public void setBtnCode(String btnCode) {
        this.btnCode = btnCode == null ? null : btnCode.trim();
    }

    /**
     * @return actionUrl
     */
    public String getActionUrl() {
        return actionUrl;
    }

    /**
     * @param actionUrl
     */
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl == null ? null : actionUrl.trim();
    }

    /**
     * @return menuId
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "Button{" +
                "id=" + id +
                ", btnName='" + btnName + '\'' +
                ", btnCode='" + btnCode + '\'' +
                ", actionUrl='" + actionUrl + '\'' +
                ", menuId=" + menuId +
                '}';
    }
}