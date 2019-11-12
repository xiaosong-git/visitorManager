package com.manage.service.impl;

import com.manage.dao.SystemParamMapping;
import com.manage.model.SystemParam;
import com.manage.service.ISystemParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LZ on 2017/8/18.
 */
@Service()
public class SystemParamsService implements ISystemParamsService {
    @Autowired
    private SystemParamMapping systemParamMapping;

    /**
     * 作用：获取所有的系统参数信息
     * @return
     * @throws Exception
     */
    @Override
    public List<SystemParam> findAllSystemParam() throws Exception {
        return systemParamMapping.findAllSystemParam();
    }

    /**
     * 作用：修改系统参数信息
     * @return
     * @throws Exception
     */
    @Override
    public int updateSystemParam(SystemParam systemParam) throws Exception {
        return systemParamMapping.updateSystemParam(systemParam);
    }

    /**
     * 根据变量名取参数值
     * @param paramName
     * @return
     */
    public SystemParam findByParamName(String paramName)throws Exception{
        return systemParamMapping.systemParam(paramName);
    }

}
