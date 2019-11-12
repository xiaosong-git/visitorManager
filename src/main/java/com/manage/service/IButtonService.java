package com.manage.service;

import com.manage.model.Button;

import java.util.List;

/**
 * 按钮
 * Created by L on 2017/8/3.
 */
public interface IButtonService {

    public Button findById(Long buttonId) throws Exception;

    public List<Button> findByMenuId(Long MenuId) throws Exception;

    public int deleteByPrimaryKey(Long id);

    public int insert(Button record);

    public int insertSelective(Button record);

    public Button selectByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(Button record);

    public int updateByPrimaryKey(Button record);

}
