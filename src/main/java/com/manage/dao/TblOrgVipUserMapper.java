package com.manage.dao;

import com.manage.model.TblOrgVipUser;
import com.manage.model.TblOrgVipUserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TblOrgVipUserMapper {
    int countByExample(TblOrgVipUserExample example);

    int deleteByExample(TblOrgVipUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblOrgVipUser record);

    int insertSelective(TblOrgVipUser record);

    List<TblOrgVipUser> selectByExample(TblOrgVipUserExample example);

    TblOrgVipUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblOrgVipUser record, @Param("example") TblOrgVipUserExample example);

    int updateByExample(@Param("record") TblOrgVipUser record, @Param("example") TblOrgVipUserExample example);

    int updateByPrimaryKeySelective(TblOrgVipUser record);

    int updateByPrimaryKey(TblOrgVipUser record);

    Integer updateVipUser(@Param("ids") List<Long> ids);

    Integer updateDelStatus(@Param("ids") List<Long> ids);
}