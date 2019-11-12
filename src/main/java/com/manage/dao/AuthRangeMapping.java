package com.manage.dao;

import com.manage.model.AuthRange;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthRangeMapping extends MyMapper<AuthRange> {

    /*
     * 作用：获取该角色的所有菜单
     * @param roleId
     * @return
     * @throws Exception
     */
    List<AuthRange> findAllByRoleId(@Param("roleId") String roleId);

    /**
     * 作用：获取该角色的所有按钮
     * @param roleId
     * @param menuId
     * @return
     * @throws Exception
     */
    List<AuthRange> findAllButton(@Param("roleId") String roleId, @Param("menuId") String menuId);

    /**
     * 作用：获取该角色
     * @param menu_id
     * @param button_id
     * @param role_id
     * @return
     * @throws Exception
     */
    List<AuthRange> findByAuthAgentRange(@Param("menu_id") Long menu_id, @Param("button_id") Long button_id, @Param("role_id") Long role_id);

    /**
     * 作用：删除该角色的权限
     * @param role_id
     * @return
     * @throws Exception
     */
    void deleteByRoleId(@Param("role_id") Long role_id);

    /**
     * 作用：批量添加
     * @param authAgentRanges
     * @return
     * @throws Exception
     */
    Integer batchInsert(@Param("authAgentRanges") List<AuthRange> authAgentRanges);
}