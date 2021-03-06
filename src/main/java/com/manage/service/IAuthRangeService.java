package com.manage.service;

import com.manage.model.AuthRange;

import java.util.List;

public interface IAuthRangeService {

    /*
     * 作用：获取该角色的所有菜单
     * @param roleId
     * @return
     * @throws Exception
     */
    public List<AuthRange> findAllByRoleId(String roleId) throws Exception;

    /**
     * 作用：获取该角色的所有按钮
     * @param roleId
     * @param menuId
     * @return
     * @throws Exception
     */
    public List<AuthRange> findAllButton(String roleId, String menuId) throws Exception;

    /**
     * 作用：获取该角色
     * @param menu_id
     * @param button_id
     * @param role_id
     * @return
     * @throws Exception
     */
    public AuthRange findByAuthAgentRange(Long menu_id, Long button_id, Long role_id) throws Exception;

    /**
     * 作用：删除该角色的权限
     * @param role_id
     * @return
     * @throws Exception
     */
    public void deleteByRoleId(Long role_id) throws Exception;

    /**
     * 作用：批量添加
     * @param authAgentRanges
     * @return
     * @throws Exception
     */
    public Integer batchInsert(List<AuthRange> authAgentRanges) throws Exception;
}
