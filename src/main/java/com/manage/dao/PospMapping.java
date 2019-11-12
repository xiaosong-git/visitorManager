package com.manage.dao;

import com.manage.model.Posp;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PospMapping extends MyMapper<Posp> {
    List<Posp> findByPage(@Param("relation_no") String relation_no, @Param("orgName") String orgName, @Param("pospName") String pospName);

    Integer add(@Param("posp") Posp posp);

    Integer update(@Param("posp") Posp posp);

    Integer del(@Param("ids") List<Long> ids);

    Integer updateStatus(@Param("posp") Posp posp);

    /**
     * 根据单个参数名查找
     * @param paramName
     * @param paramText
     * @return
     */
    Posp findByParam(@Param("paramName") String paramName, @Param("paramText") String paramText);

}