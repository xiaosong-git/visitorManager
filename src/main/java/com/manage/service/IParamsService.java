package com.manage.service;

import com.manage.model.Params;

import java.util.List;

/**
 * Created LZ Administrator on 2017/8/18.
 */
public interface IParamsService {
    /**
     * 通过 参数名 获取 参数值
     * @param paramName 参数名
     * @return
     *
     */
    public String findByParamName(String paramName) throws Exception;

    /**
     * 作用：获取所有的业务参数信息
     * @return
     * @throws Exception
     */
    public List<Params> findAllParams() throws Exception;

    /**
     * 作用：修改业务参数信息
     * @return
     * @throws Exception
     */
    public int updateParams(Params params) throws Exception;
}
