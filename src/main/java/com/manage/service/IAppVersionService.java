package com.manage.service;

import com.manage.model.AppVersion;

import java.util.List;

public interface IAppVersionService {

    /**
     * 分页查询app版本
     * @param appType
     * @param channel
     * @param versionName
     * @param versionNum
     * @param startDate
     * @param endDate
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<AppVersion> findByPage(String appType,String channel,String versionName,String versionNum,String startDate,String endDate, int pageNo, int pageSize)throws Exception;

    /**
     * 插入数据
     * @param appVersion
     * @return
     * @throws Exception
     */
    public Integer add(AppVersion appVersion)throws Exception;

    /**
     * 修改数据
     * @param appVersion
     * @return
     * @throws Exception
     */
    public Integer update(AppVersion appVersion)throws Exception;

    /**
     * 删除设备数据
     * @param ids
     * @return
     * @throws Exception
     */
    public Integer del(List<Long> ids)throws Exception;

    /**
     * 作用：通过ID查找
     * @param id
     * @return
     * @throws Exception
     */
    public AppVersion findById(Long id) throws Exception;

    /**
     * 删除缓存
     * @throws Exception
     */
    public void deleteToRedis(AppVersion appVersion) throws Exception;
    /**
     * 存入缓存
     * @throws Exception
     */
    public void saveToRedis(AppVersion appVersion) throws Exception;
}
