package com.manage.service;

import com.manage.model.AdBanner;

import java.util.List;

/**
 * tbl_ad_banner service 接口<br/>
 * 2017-05-05 07:01:50 bianzk
 */
public interface IAdBannerService {

 /**
  * 分页查询广告
  * @return
  * @param title
  * @param bDate
  * @param eDate
  * @param page
  * @param pageSize
  */
 public List<AdBanner> findByPager(String title, String bDate, String eDate, int page, int pageSize)throws Exception;

 /**
  * 插入广告
  * @param adBanner
  * @return
  */
 public Integer addAdBanner(AdBanner adBanner) throws Exception;

 /**
  * 更新状态
  * @param status
  * @param id
  * @return
  */
 public Integer updateStatus(String status, Long id) throws Exception;

 /**
  * 删除
  * @return
  */
 public Integer deleteAdBanner(List<Long> ids) throws Exception;

 /**
  * 更新广告
  * @param adBanner
  * @return
  */
 public Integer updateAdBanner(AdBanner adBanner) throws Exception;

 /**
  * 根据ID查询
  * @param id
  * @return
  */
 public AdBanner findById(Long id) throws Exception;

 /**
  * 从缓存中删除
  * @throws Exception
  */
 public Long deleteFromRedis() throws Exception;

}

