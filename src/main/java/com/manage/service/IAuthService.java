package com.manage.service;

import com.manage.model.Auth;
import com.manage.model.Role;

import java.util.List;

/**
 * 权限接口
 * Created by L on 2017/8/9.
 */
public interface IAuthService {

    public Role findRole(Long roleId) throws Exception;

    public int deleteByPrimaryKey(Long id);

    public int insert(Auth record);

    public int insertSelective(Auth record);

    public Auth selectByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(Auth record);

    public int updateByPrimaryKey(Auth record);

    public Auth selectBybtnIdAndRoleIdAndMenuId(Long buttonId, Long roleId, Long menuId);

    public List<Auth> selectByRoleIdAndMenuId(Long roleId, Long menuId);

    public int batchInsert(List<Auth> auths);

    public void batchDelete(String ids);

    public void batchUpdate(List<Auth> auths);

    public void deleteByRoleid(Long roleId);

    /**
     * 测试用
     * @param auth
     * @return
     */
    public Integer testAddGetKey(Auth auth);

}
