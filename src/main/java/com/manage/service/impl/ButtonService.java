package com.manage.service.impl;

import com.manage.dao.ButtonMapping;
import com.manage.model.Button;
import com.manage.service.IButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by L on 2017/8/3.
 */
@Service
public class ButtonService implements IButtonService {

    @Autowired
    private ButtonMapping buttonMapping;
//    @Autowired
//    private SqlSession sqlSession;

    public Button findById(Long buttonId) throws Exception{
//        ButtonDao buttonDao=sqlSession.getMapper(ButtonDao.class);
        return buttonMapping.selectByPrimaryKey(buttonId);
    }

    @Override
    public List<Button> findByMenuId(Long menuId) throws Exception {
        return buttonMapping.findByMenuId(menuId);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return buttonMapping.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Button record) {
        return buttonMapping.insert(record);
    }

    @Override
    public int insertSelective(Button record) {
        return buttonMapping.insertSelective(record);
    }

    @Override
    public Button selectByPrimaryKey(Long id) {
        return buttonMapping.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Button record) {
        return buttonMapping.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Button record) {
        return buttonMapping.updateByPrimaryKey(record);
    }

}
