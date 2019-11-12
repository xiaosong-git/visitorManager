package com.manage.dao;

import com.manage.model.AppVersion;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppVersionMapping extends MyMapper<AppVersion> {
    List<AppVersion> findByPage(@Param("appType") String appType,@Param("channel") String channel,@Param("versionName") String versionName,@Param("versionNum") String versionNum,@Param("startDate") String startDate,@Param("endDate") String endDate);

    Integer add(@Param("appVersion") AppVersion appVersion);

    Integer update(@Param("appVersion") AppVersion appVersion);

    AppVersion findById(@Param("id") Long id);

    Integer del(@Param("ids") List<Long> ids);
}