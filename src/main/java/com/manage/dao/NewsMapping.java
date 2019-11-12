package com.manage.dao;

import com.manage.model.News;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapping extends MyMapper<News> {
    List<News> findByPage(@Param("newsName") String newsName);

    Integer addNews(@Param("news") News news);

    Integer updateNews(@Param("news") News news);

    Integer deleteNews(@Param("ids") List<Long> ids);

    Integer updateNewsStatus(@Param("news") News news);
}