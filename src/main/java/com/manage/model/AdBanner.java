package com.manage.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tbl_ad_banner")
public class AdBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告图片路径
     */
    private String imgUrl;

    /**
     * 超链接
     */
    private String hrefUrl;

    /**
     * 广告状态 0无效 1有效
     */
    private Integer status;

    private Date createTime;

    /**
     * 排序，数值越高，越靠前
     */
    private Integer orders;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取广告标题
     *
     * @return title - 广告标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置广告标题
     *
     * @param title 广告标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取广告图片路径
     *
     * @return imgUrl - 广告图片路径
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置广告图片路径
     *
     * @param imgUrl 广告图片路径
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     * 获取超链接
     *
     * @return hrefUrl - 超链接
     */
    public String getHrefUrl() {
        return hrefUrl;
    }

    /**
     * 设置超链接
     *
     * @param hrefUrl 超链接
     */
    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl == null ? null : hrefUrl.trim();
    }

    /**
     * 获取广告状态 0无效 1有效
     *
     * @return status - 广告状态 0无效 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置广告状态 0无效 1有效
     *
     * @param status 广告状态 0无效 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取排序，数值越高，越靠前
     *
     * @return orders - 排序，数值越高，越靠前
     */
    public Integer getOrders() {
        return orders;
    }

    /**
     * 设置排序，数值越高，越靠前
     *
     * @param orders 排序，数值越高，越靠前
     */
    public void setOrders(Integer orders) {
        this.orders = orders;
    }
}