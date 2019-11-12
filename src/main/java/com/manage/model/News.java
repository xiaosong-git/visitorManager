package com.manage.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tbl_news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String newsDate;

    private String newsName;

    private String newsDetail;

    private String newsImageUrl;

    private String newsUrl;

    private String newsStatus;

    //非数据库映射
    private  String newsStatusName;

    public String getNewsStatusName() {
        return newsStatusName;
    }

    public void setNewsStatusName(String newsStatusName) {
        this.newsStatusName = newsStatusName;
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
     * @return newsDate
     */
    public String getNewsDate() {
        return newsDate;
    }

    /**
     * @param newsDate
     */
    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate == null ? null : newsDate.trim();
    }

    /**
     * @return newsName
     */
    public String getNewsName() {
        return newsName;
    }

    /**
     * @param newsName
     */
    public void setNewsName(String newsName) {
        this.newsName = newsName == null ? null : newsName.trim();
    }

    /**
     * @return newsDetail
     */
    public String getNewsDetail() {
        return newsDetail;
    }

    /**
     * @param newsDetail
     */
    public void setNewsDetail(String newsDetail) {
        this.newsDetail = newsDetail == null ? null : newsDetail.trim();
    }

    /**
     * @return newsImageUrl
     */
    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    /**
     * @param newsImageUrl
     */
    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl == null ? null : newsImageUrl.trim();
    }

    /**
     * @return newsUrl
     */
    public String getNewsUrl() {
        return newsUrl;
    }

    /**
     * @param newsUrl
     */
    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl == null ? null : newsUrl.trim();
    }

    /**
     * @return newsStatus
     */
    public String getNewsStatus() {
        return newsStatus;
    }

    /**
     * @param newsStatus
     */
    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus == null ? null : newsStatus.trim();
    }

}