package com.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.manage.dao.AppVersionMapping;
import com.manage.model.AppVersion;
import com.manage.service.IAppVersionService;
import com.manage.service.IParamsService;
import com.manage.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppVersionService implements IAppVersionService {

    @Autowired
    private AppVersionMapping appVersionMapping;
    @Autowired
    private IParamsService paramsService;

    @Override
    public List<AppVersion> findByPage(String appType,String channel,String versionName,String versionNum,String startDate,String endDate, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return appVersionMapping.findByPage(appType, channel, versionName, versionNum, startDate, endDate);
    }

    @Override
    public Integer add(AppVersion appVersion) throws Exception {
        Integer result=  appVersionMapping.add(appVersion);
        if(result > 0){
            saveToRedis(appVersion);
        }
        return result;
    }

    @Override
    public Integer update(AppVersion appVersion) throws Exception {
        AppVersion appVersion2=findById(appVersion.getId());
        Integer result= appVersionMapping.update(appVersion);
        if(result > 0){
            deleteToRedis(appVersion2);
            saveToRedis(appVersion);
        }
        return result;
    }

    @Override
    public Integer del(List<Long> ids) throws Exception {
        for(int i=0; i<ids.size(); i++){
            deleteToRedis(findById(ids.get(i)));
        }
        return appVersionMapping.del(ids);
    }

    @Override
    public AppVersion findById(Long id) throws Exception {
        return appVersionMapping.findById(id);
    }

    @Override
    public void deleteToRedis(AppVersion appVersion) throws Exception {
        Integer apiAuthCheckRedisDbIndex = Integer.valueOf(paramsService.findByParamName("apiAuthCheckRedisDbIndex"));
        String appType=appVersion.getAppType();
        String channel=appVersion.getChannel();
        if (appType.equals("android")) {
            String Key = "appVersion_android_"+appType+"_"+channel;
            RedisUtil.del(apiAuthCheckRedisDbIndex, Key);
        }
        if (appType.equals("ios")) {
            String Key = "appVersion_ios_"+appType+"_"+channel;
            RedisUtil.del(apiAuthCheckRedisDbIndex, Key);
        }
    }

    @Override
    public void saveToRedis(AppVersion appVersion) throws Exception {
        Integer apiAuthCheckRedisDbIndex = Integer.valueOf(paramsService.findByParamName("apiAuthCheckRedisDbIndex"));
        String appType=appVersion.getAppType();
        String channel=appVersion.getChannel();
        if (appType.equals("android")) {
            String Key = "appVersion_android_"+appType+"_"+channel;
            String json = JSONObject.toJSONString(appVersion);
            RedisUtil.setStr(Key, json, apiAuthCheckRedisDbIndex, null);
        }
        if (appType.equals("ios")) {
            String Key = "appVersion_ios_"+appType+"_"+channel;
            String json = JSONObject.toJSONString(appVersion);
            RedisUtil.setStr(Key, json, apiAuthCheckRedisDbIndex, null);
        }
    }
}
