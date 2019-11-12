package com.manage.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import javax.persistence.Transient;

public class VisitorRecordExcelModel extends BaseRowModel {

    /**
     * 访问日期
     */
    @ExcelProperty(value="访问日期",index=0)
    private String visitDate;

    /**
     * 访问时间
     */
    @ExcelProperty(value="访问时间",index=1)
    private String visitTime;

    /**
     * 访客id
     */
    @ExcelProperty(value="访客id",index=2)
    private Long userId;

    /**
     * 被访者id
     */
    @ExcelProperty(value="被访者id",index=3)
    private Long visitorId;

    /**
     * 访问原因
     */
    @ExcelProperty(value="访问原因",index=4)
    private String reason;

    /**
     * 状态 applying:申请中，applySuccess:接受访问，applyFail:拒绝访问
     */
    @ExcelProperty(value="状态",index=5)
    private String cstatus;

    /**
     * 日期类型:无期：Indefinite,有限期:limitPeriod
     */
    @ExcelProperty(value="日期类型",index=6)
    private String dateType;

    /**
     * 开始日期
     */
    @ExcelProperty(value="开始日期",index=7)
    private String startDate;

    /**
     * 结束日期
     */
    @ExcelProperty(value="结束日期",index=8)
    private String endDate;

    /**
     * 被访者回复
     */
    @ExcelProperty(value="被访者回复",index=9)
    private String answerContent;

    @Transient
    private String userName;
    @Transient
    private String visitorName;

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }
}
