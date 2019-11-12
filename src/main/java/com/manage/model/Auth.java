package com.manage.model;

import com.manage.model.base.Base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_auth")
public class Auth extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long menu_id;

    private Long role_id;

    private Long button_id;

    public Auth() {
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
     * @return menu_id
     */
    public Long getMenu_id() {
        return menu_id;
    }

    /**
     * @param menu_id
     */
    public void setMenu_id(Long menu_id) {
        this.menu_id = menu_id;
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
     * @return button_id
     */
    public Long getButton_id() {
        return button_id;
    }

    /**
     * @param button_id
     */
    public void setButton_id(Long button_id) {
        this.button_id = button_id;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id=" + id +
                ", menu_id=" + menu_id +
                ", role_id=" + role_id +
                ", button_id=" + button_id +
                '}';
    }
}