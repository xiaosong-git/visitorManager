package com.manage.dao;

import com.manage.model.TblCompanyUser;
import com.manage.model.TblCompanyUserExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TblCompanyUserMapper {
    int countByExample(TblCompanyUserExample example);

    int deleteByExample(TblCompanyUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblCompanyUser record);

    int insertSelective(TblCompanyUser record);

    List<TblCompanyUser> selectByExample(TblCompanyUserExample example);

    TblCompanyUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblCompanyUser record, @Param("example") TblCompanyUserExample example);

    int updateByExample(@Param("record") TblCompanyUser record, @Param("example") TblCompanyUserExample example);

    int updateByPrimaryKeySelective(TblCompanyUser record);

    int updateByPrimaryKey(TblCompanyUser record);

    List<TblCompanyUser> findByPage(@Param("createDate") String createDate,
                                    @Param("userName") String userName,
                                    @Param("status") String status,
                                    @Param("companyName")String companyName,
                                    @Param("orgId")Long orgId
                                    );

    int updateCheckStatusByPersonIds(@Param(value = "list") List<TblCompanyUser> list);
}