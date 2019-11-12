package com.manage.service;

import com.manage.model.Menu;

import java.util.List;

/**
 * Created by L on 2017/8/3.
 */
public interface IMenuService {

    public int deleteByPrimaryKey(Long id);

    public int insert(Menu record);

    public int insertSelective(Menu record);

    public Menu selectByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(Menu record);

    public int updateByPrimaryKey(Menu record);

    public List<Menu> findAll() throws Exception;
}
