package com.manage.dao;

import com.manage.model.Auth;
import com.manage.model.Role;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthMapping extends MyMapper<Auth> {

    /**
     * 查找角色菜单 单向关联
     * @param roleId
     * @return
     */
    Role findMenu(Long roleId);

    Auth selectBybtnIdAndRoleIdAndMenuId(@Param("buttonId") Long buttonId, @Param("roleId") Long roleId, @Param("menuId") Long menuId);

    List<Auth> selectByRoleIdAndMenuId(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int batchInsert(List<Auth> authss);

    void batchDelete(@Param("ids") List<String> ids);

    void batchUpdate(@Param("auths") List<Auth> auths);

    void deleteByRoleid(Long roleId);
}