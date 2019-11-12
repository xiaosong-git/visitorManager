package com.manage.dao;

import com.manage.model.Menu;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapping extends MyMapper<Menu> {

    List<Menu> selectAll();
}