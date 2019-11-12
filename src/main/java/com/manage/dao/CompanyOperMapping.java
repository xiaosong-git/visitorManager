package com.manage.dao;

import com.manage.model.CompanyOper;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CompanyOperMapping extends MyMapper<CompanyOper> {

    CompanyOper findByLoginName(@Param("login_name") String login_name);

    int addCompanyOper(@Param("companyOper") CompanyOper companyOper);

    Integer delCompanyOperAll(@Param("ids") List<Long> ids);
}