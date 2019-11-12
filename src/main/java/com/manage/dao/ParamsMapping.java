package com.manage.dao;

import com.manage.model.Params;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ParamsMapping extends MyMapper<Params> {
    /**
     * 通过 参数名 获取 参数值
     * @param paramName 参数名
     * @return
     *
     */
    public String findByParamName(String paramName);

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
    public int updateParams(@Param("params") Params params) throws Exception;
}