package com.manage.service.impl;

import com.manage.dao.AuthRangeMapping;
import com.manage.model.AuthRange;
import com.manage.service.IAuthRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by L on 2017/8/9.
 */
@Service
public class AuthRangeService implements IAuthRangeService {

    @Autowired
    private AuthRangeMapping authRangeMapping;

    /*
     * 作用：获取该角色的所有菜单
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public List<AuthRange> findAllByRoleId(String roleId) throws Exception {
        return authRangeMapping.findAllByRoleId(roleId);
    }

    /**
     * 作用：获取该角色的所有按钮
     * @param roleId
     * @param menuId
     * @return
     * @throws Exception
     */
    @Override
    public List<AuthRange> findAllButton(String roleId, String menuId) throws Exception {
        return authRangeMapping.findAllButton(roleId,menuId);
    }

    /**
     * 作用：获取该角色
     * @param menu_id
     * @param button_id
     * @param role_id
     * @return
     * @throws Exception
     */
    @Override
    public AuthRange findByAuthAgentRange(Long menu_id, Long button_id, Long role_id) throws Exception {
        List<AuthRange> list = authRangeMapping.findByAuthAgentRange(menu_id, button_id, role_id);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 作用：删除该角色的权限
     * @param role_id
     * @return
     * @throws Exception
     */
    @Override
    public void deleteByRoleId(Long role_id) throws Exception {
        authRangeMapping.deleteByRoleId(role_id);
    }

    /**
     * 作用：批量添加
     * @param authAgentRanges
     * @return
     * @throws Exception
     */
    @Override
    public Integer batchInsert(List<AuthRange> authAgentRanges) throws Exception{
        return authRangeMapping.batchInsert(authAgentRanges);
    }

}
