package com.manage.dao;

import com.manage.model.Terminal;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TerminalMapping extends MyMapper<Terminal> {

    List<Terminal> findByPage(@Param("relation_no") String relation_no, @Param("orgName") String orgName,@Param("terminalNo") String terminalNo);

    Integer add(@Param("terminal") Terminal terminal);

    Integer update(@Param("terminal") Terminal terminal);

    Integer del(@Param("ids") List<Long> ids);

    Integer updateStatus(@Param("terminal") Terminal terminal);
}