package com.manage.service;

import com.manage.model.SystemParam;

import java.util.List;

/**
 * Created LZ Administrator on 2017/8/18.
 */
public interface ISystemParamsService {

    /**
     * 作用：获取所有的系统参数信息
     * @return
     * @throws Exception
     */
    public List<SystemParam> findAllSystemParam() throws Exception;

    /**
     * 作用：修改系统参数信息
     * @return
     * @throws Exception
     */
    public int updateSystemParam(SystemParam systemParam) throws Exception;

    /**20180609
     * 根据变量名取参数值
     * @param paramName
     * @return
     */
    public SystemParam findByParamName(String paramName) throws Exception;
}
