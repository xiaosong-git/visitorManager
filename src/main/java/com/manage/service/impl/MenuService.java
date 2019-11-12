package com.manage.service.impl;

import com.manage.dao.MenuMapping;
import com.manage.model.Menu;
import com.manage.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by L on 2017/8/9.
 */
@Service
public class MenuService implements IMenuService {

    @Autowired
    private MenuMapping menuMapping;

    @Override
    public List<Menu> findAll() throws Exception{
        return menuMapping.selectAll();
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return menuMapping.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Menu record) {
        return menuMapping.insert(record);
    }

    @Override
    public int insertSelective(Menu record) {
        return menuMapping.insertSelective(record);
    }

    @Override
    public Menu selectByPrimaryKey(Long id) {
        return menuMapping.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Menu record) {
        return menuMapping.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Menu record) {
        return menuMapping.updateByPrimaryKey(record);
    }
}
