package com.manage.dao;

import com.manage.model.AdBanner;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdBannerMapping extends MyMapper<AdBanner> {

    /**
     * 分页查询广告
     * @return
     * @param title
     * @param bDate
     * @param eDate
     */
    List<AdBanner> findByPager(@Param("title") String title, @Param("bDate") String bDate, @Param("eDate") String eDate);

    /**
     * 插入广告
     * @param adBanner
     * @return
     */
    Integer addAdBanner(@Param("adBanner") AdBanner adBanner);

    /**
     * 更新广告
     * @param adBanner
     * @return
     */
    Integer updateAdBanner(@Param("adBanner") AdBanner adBanner);

    /**
     * 删除
     * @return
     */
    Integer deleteAdBanner(@Param("ids") List<Long> ids);

    /**
     * 更新状态
     * @param status
     * @param id
     * @return
     */
    Integer updateStatus(@Param("status") String status, @Param("id") Long id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    AdBanner findById(@Param("id") Long id);
}