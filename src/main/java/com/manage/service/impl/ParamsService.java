package com.manage.service.impl;

import com.manage.dao.ParamsMapping;
import com.manage.model.Params;
import com.manage.service.IParamsService;
import com.manage.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LZ on 2017/8/18.
 */
@Service("paramsService")
public class ParamsService implements IParamsService {
    @Autowired
    private ParamsMapping paramsMapping;
    /**
     * 通过 参数名 获取 参数值
     * @param paramName 参数名
     * @return
     *
     */
    @Override
    public String findByParamName(String paramName) throws Exception {
        String key="params_"+paramName;
        String parmas= RedisUtil.getStrVal(key,8);
        if(StringUtils.isBlank(parmas)){
           parmas=  paramsMapping.findByParamName(paramName);
            RedisUtil.setStr(key,parmas,8,null);
        }
        return parmas;



    }

    /**
     * 作用：获取所有的业务参数信息
     * @return
     * @throws Exception
     */
    @Override
    public List<Params> findAllParams() throws Exception {
        return paramsMapping.findAllParams();
    }

    /**
     * 作用：修改业务参数信息
     * @return
     * @throws Exception
     */
    @Override
    public int updateParams(Params params) throws Exception {
        return paramsMapping.updateParams(params);
    }
}
