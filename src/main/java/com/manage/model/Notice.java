package com.manage.model;

import com.xdream.kernel.dao.jdbc.Table;
import com.xdream.kernel.entity.Entity;

/**
 * tbl_notice 实体类<br/>
 * 2017-04-09 05:15:46 LZ
 */
@SuppressWarnings("serial")
@Table(name="tbl_notice")
public class Notice  extends Entity {
 private Long orgId;
 private String relationNo;
 private String noticeTitle;
 private String content;
 private String createDate;
 private String createTime;
 private String cstatus;
 //扩展字段
 private String org_name;//机构名称
 private String item_text;//公告状态的名称
 private String startDate;//查询时的开始日期
 private String endDate;//结束日期
 public void setOrgId(Long orgId){
     this.orgId=orgId;
 }
 public Long getOrgId(){
     return orgId;
 }
 public void setRelationNo(String relationNo){
     this.relationNo=relationNo;
 }
 public String getRelationNo(){
     return relationNo;
 }
 public void setNoticeTitle(String noticeTitle){
     this.noticeTitle=noticeTitle;
 }
 public String getNoticeTitle(){
     return noticeTitle;
 }
 public void setContent(String content){
     this.content=content;
 }
 public String getContent(){
     return content;
 }
 public void setCreateDate(String createDate){
     this.createDate=createDate;
 }
 public String getCreateDate(){
     return createDate;
 }
 public void setCreateTime(String createTime){
     this.createTime=createTime;
 }
 public String getCreateTime(){
     return createTime;
 }
 public void setCstatus(String cstatus){
     this.cstatus=cstatus;
 }
 public String getCstatus(){
     return cstatus;
 }
 public String getOrg_name() {
     return org_name;
 }
 public void setOrg_name(String org_name) {
     this.org_name = org_name;
 }

 public String getItem_text() {
     return item_text;
 }
 public void setItem_text(String item_text) {
     this.item_text = item_text;
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

}

