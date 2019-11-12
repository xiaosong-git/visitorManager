package com.manage.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import javax.persistence.*;

@Table(name = "tbl_visitor_record")
public class VisitorRecord  extends BaseRowModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 访问日期
     */
    private String visitDate;

    /**
     * 访问时间
     */
    private String visitTime;

    /**
     * 访客id
     */
    private Long userId;

    /**
     * 被访者id
     */
    private Long visitorId;

    /**
     * 访问原因
     */
    @ExcelProperty(value="访问原因",index=3)
    private String reason;

    /**
     * 状态 applying:申请中，applySuccess:接受访问，applyFail:拒绝访问
     */
    private String cstatus;

    /**
     * 日期类型:无期：Indefinite,有限期:limitPeriod
     */
    private String dateType;

    /**
     * 开始日期
     */
    @ExcelProperty(value="访问预约开始日期",index=4)
    private String startDate;

    /**
     * 结束日期
     */
    @ExcelProperty(value="访问预约结束日期",index=5)
    private String endDate;

    /**
     * 被访者回复
     */
    private String answerContent;

    //扩展
    @Transient
    @ExcelProperty(value="访问姓名",index=0)
    private String userName;
    @Transient
    @ExcelProperty(value="被访人姓名",index=1)
    private String visitorName;

    @Transient
    @ExcelProperty(value="被访人公司",index=2)
    private String companyName;

    @Transient
    @ExcelProperty(value="实际进入日期",index=6)
    private String indate;
    @Transient
    @ExcelProperty(value="实际离开日期",index=7)
    private String outdate;

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
     * 获取访问日期
     *
     * @return visitDate - 访问日期
     */
    public String getVisitDate() {
        return visitDate;
    }

    /**
     * 设置访问日期
     *
     * @param visitDate 访问日期
     */
    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate == null ? null : visitDate.trim();
    }

    /**
     * 获取访问时间
     *
     * @return visitTime - 访问时间
     */
    public String getVisitTime() {
        return visitTime;
    }

    /**
     * 设置访问时间
     *
     * @param visitTime 访问时间
     */
    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime == null ? null : visitTime.trim();
    }

    /**
     * 获取访客id
     *
     * @return userId - 访客id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置访客id
     *
     * @param userId 访客id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取被访者id
     *
     * @return visitorId - 被访者id
     */
    public Long getVisitorId() {
        return visitorId;
    }

    /**
     * 设置被访者id
     *
     * @param visitorId 被访者id
     */
    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    /**
     * 获取访问原因
     *
     * @return reason - 访问原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置访问原因
     *
     * @param reason 访问原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * 获取状态 applying:申请中，applySuccess:接受访问，applyFail:拒绝访问
     *
     * @return cstatus - 状态 applying:申请中，applySuccess:接受访问，applyFail:拒绝访问
     */
    public String getCstatus() {
        return cstatus;
    }

    /**
     * 设置状态 applying:申请中，applySuccess:接受访问，applyFail:拒绝访问
     *
     * @param cstatus 状态 applying:申请中，applySuccess:接受访问，applyFail:拒绝访问
     */
    public void setCstatus(String cstatus) {
        this.cstatus = cstatus == null ? null : cstatus.trim();
    }

    /**
     * 获取日期类型:无期：Indefinite,有限期:limitPeriod
     *
     * @return dateType - 日期类型:无期：Indefinite,有限期:limitPeriod
     */
    public String getDateType() {
        return dateType;
    }

    /**
     * 设置日期类型:无期：Indefinite,有限期:limitPeriod
     *
     * @param dateType 日期类型:无期：Indefinite,有限期:limitPeriod
     */
    public void setDateType(String dateType) {
        this.dateType = dateType == null ? null : dateType.trim();
    }

    /**
     * 获取开始日期
     *
     * @return startDate - 开始日期
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 设置开始日期
     *
     * @param startDate 开始日期
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    /**
     * 获取结束日期
     *
     * @return endDate - 结束日期
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 设置结束日期
     *
     * @param endDate 结束日期
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    /**
     * 获取被访者回复
     *
     * @return answerContent - 被访者回复
     */
    public String getAnswerContent() {
        return answerContent;
    }

    /**
     * 设置被访者回复
     *
     * @param answerContent 被访者回复
     */
    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent == null ? null : answerContent.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getOutdate() {
        return outdate;
    }

    public void setOutdate(String outdate) {
        this.outdate = outdate;
    }
}