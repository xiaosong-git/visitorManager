package com.manage.service.impl;

import com.manage.dao.AuthMapping;
import com.manage.model.Auth;
import com.manage.model.Role;
import com.manage.service.IAuthService;
import com.manage.utils.mapper.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限管理
 * Created by L on 2017/8/9.
 */
@Service
//@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
public class AuthService implements IAuthService {


    @Autowired
    private AuthMapping authMapping;

    /**
     * 获取角色对象 其中包含菜单集合
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public Role findRole(Long roleId) throws Exception {
        return authMapping.findMenu(roleId);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return authMapping.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Auth record) {
        return authMapping.insert(record);
    }

    @Override
    public int insertSelective(Auth record) {
        return authMapping.insertSelective(record);
    }

    @Override
    public Auth selectByPrimaryKey(Long id) {
        return authMapping.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Auth record) {
        return authMapping.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Auth record) {
        return authMapping.updateByPrimaryKey(record);
    }

    /**
     * 查询是否有这个权限
     * @param buttonId
     * @param roleId
     * @param menuId
     * @return
     */
    @Override
    public Auth selectBybtnIdAndRoleIdAndMenuId(Long buttonId, Long roleId, Long menuId) {
        return authMapping.selectBybtnIdAndRoleIdAndMenuId(buttonId,roleId,menuId);
    }

    @Override
    public List<Auth> selectByRoleIdAndMenuId(Long roleId, Long menuId) {
        return authMapping.selectByRoleIdAndMenuId(roleId,menuId);
    }

    @Override
    public int batchInsert(List<Auth> auths) {
        return authMapping.batchInsert(auths);
    }

    @Override
    public void batchDelete(String ids) {
        authMapping.batchDelete(SqlUtil.getIdsList(ids));
    }

    @Override
    public void batchUpdate(List<Auth> auths) {
        authMapping.batchUpdate(auths);
    }

    @Override
    public void deleteByRoleid(Long roleId) {
        authMapping.deleteByRoleid(roleId);
    }

    /**
     * 测试用
     * @param auth
     * @return
     */
    @Override
    public Integer testAddGetKey(Auth auth) {
        return authMapping.insertUseGeneratedKeys(auth);
    }


}
