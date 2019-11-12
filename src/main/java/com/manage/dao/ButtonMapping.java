package com.manage.dao;

import com.manage.model.Button;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ButtonMapping extends MyMapper<Button> {

    List<Button> findByMenuId(Long menuId);

    /**
     * 根据url查询按钮对象
     * @param actionUrl
     * @return
     */
    Button findByActionUrl(String actionUrl);

}