package com.manage.dao;

import com.manage.model.VisitorRecord;
import com.manage.model.VisitorRecordExcelModel;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VisitorRecordMapping extends MyMapper<VisitorRecord> {

    List<VisitorRecord> findByPage(
            @Param("orgCode")String orgCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("companyName") String companyName,
            @Param("visitorName") String visitorName

            );

}