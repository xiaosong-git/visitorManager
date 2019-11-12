package com.manage.model;

import javax.persistence.*;

@Table(name = "tbl_app_version")
public class AppVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appType;

    private String channel;

    private String versionName;

    private String versionNum;

    private String isImmediatelyUpdate;

    private String updateUrl;

    private String memo;

    private String createDate;

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
     * @return appType
     */
    public String getAppType() {
        return appType;
    }

    /**
     * @param appType
     */
    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    /**
     * @return channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel
     */
    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    /**
     * @return versionName
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * @param versionName
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }

    /**
     * @return versionNum
     */
    public String getVersionNum() {
        return versionNum;
    }

    /**
     * @param versionNum
     */
    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum == null ? null : versionNum.trim();
    }

    /**
     * @return isImmediatelyUpdate
     */
    public String getIsImmediatelyUpdate() {
        return isImmediatelyUpdate;
    }

    /**
     * @param isImmediatelyUpdate
     */
    public void setIsImmediatelyUpdate(String isImmediatelyUpdate) {
        this.isImmediatelyUpdate = isImmediatelyUpdate == null ? null : isImmediatelyUpdate.trim();
    }

    /**
     * @return updateUrl
     */
    public String getUpdateUrl() {
        return updateUrl;
    }

    /**
     * @param updateUrl
     */
    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl == null ? null : updateUrl.trim();
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * @return createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }
}