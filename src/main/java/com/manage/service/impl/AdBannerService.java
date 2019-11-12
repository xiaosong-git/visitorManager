package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.AdBannerMapping;
import com.manage.model.AdBanner;
import com.manage.service.IAdBannerService;
import com.manage.service.IParamsService;
import com.manage.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdBannerService implements IAdBannerService {

	@Autowired
	private AdBannerMapping adBannerMapping;

	@Autowired
	private IParamsService paramsService;
	/**
	 * 分页查询广告
	 * @return
	 * @param title
	 * @param bDate
	 * @param eDate
	 * @param page
	 * @param pageSize
	 */
	public List<AdBanner> findByPager(String title, String bDate, String eDate, int page, int pageSize) throws Exception {
		PageHelper.startPage(page, pageSize);
		return adBannerMapping.findByPager(title,bDate,eDate);
	}
	/**
	 * 插入广告
	 * @param adBanner
	 * @return
	 */
	public Integer addAdBanner(AdBanner adBanner) throws Exception {
		deleteFromRedis();
		return adBannerMapping.addAdBanner(adBanner);
	}
	/**
	 * 更新状态
	 * @param status
	 * @param id
	 * @return
	 */
	public Integer updateStatus(String status,Long id) throws Exception {
		deleteFromRedis();
		return adBannerMapping.updateStatus(status,id);
	}
	
	/**
	 * 删除
	 * @return
	 */
	public Integer deleteAdBanner(List<Long> ids) throws Exception {
		deleteFromRedis();
		return adBannerMapping.deleteAdBanner(ids);
	}
	
	/**
	 * 更新广告
	 * @param adBanner
	 * @return
	 */
	public Integer updateAdBanner(AdBanner adBanner) throws Exception {
		deleteFromRedis();
		return adBannerMapping.updateAdBanner(adBanner);
	}
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public AdBanner findById(Long id) throws Exception {
		return adBannerMapping.findById(id);
	}
	
	/**
	 * 从缓存中删除
	 * @throws Exception
	 */
	public Long deleteFromRedis() throws Exception {
		String key = "api_adBanner";
		Integer apiAuthCheckRedisDbIndex = Integer.valueOf(paramsService.findByParamName("apiAuthCheckRedisDbIndex"));//存储在缓存中的位置
		return RedisUtil.del(apiAuthCheckRedisDbIndex, key);
	}
}

