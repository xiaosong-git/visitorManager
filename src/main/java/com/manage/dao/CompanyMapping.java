package com.manage.dao;

import com.manage.model.Company;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyMapping extends MyMapper<Company> {

    List<Company> findByPage(@Param("relation_no") String relation_no, @Param("companyName") String companyName,@Param("name") String name);

    Integer add(@Param("company") Company company);

    Integer update(@Param("company") Company company);

    Integer del(@Param("ids") List<Long> ids);

    /**
     * 根据单个参数名查找
     * @param paramName
     * @param paramText
     * @return
     */
    Company findByParam(@Param("paramName") String paramName, @Param("paramText") String paramText);

    Company findByCompanyCode(@Param("companyCode") String companyCode) throws Exception;
}