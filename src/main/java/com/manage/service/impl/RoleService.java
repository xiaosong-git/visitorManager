package com.manage.service.impl;

import com.manage.dao.RoleMapping;
import com.manage.model.Role;
import com.manage.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by L on 2017/8/8.
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleMapping roleMapping;

    /*@Override
    public int deleteByPrimaryKey(Long id) {
        return roleMapping.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Role record) {
        return roleMapping.insert(record);
    }

    @Override
    public int insertSelective(Role record) {
        return roleMapping.insertSelective(record);
    }

    @Override
    public Role selectByPrimaryKey(Long id) {
        return roleMapping.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapping.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return roleMapping.updateByPrimaryKey(record);
    }*/

    public List<Role> selectAll() throws Exception {
        return roleMapping.selectAll();
    }

    /**
     * 查询本级以及下级的角色 LZ
     * @param relation_no 登录的操作员的relation_no
     * @return
     * @throws Exception
     */
    @Override
    public List<Role> findLowerRole(String relation_no) throws Exception {
        if(relation_no != null){
            return roleMapping.findLowerRole(relation_no+"%");
        }
        return null;
    }
    /**
     * 通过角色Id查询
     * @param roleId 角色Id
     * @return
     * @throws Exception
     */
    @Override
    public Role findById(Long roleId) throws Exception {
        if(roleId != null){
            return roleMapping.findById(roleId);
        }
        return null;
    }

    /**
     * 作用：删除
     * @param id
     * @throws Exception
     */
    @Override
    public void batchDelete(String id) throws Exception {
        roleMapping.batchDelete(id);
    }

    /**
     * 作用：添加信息
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public Integer add(Role role) throws Exception {
        return roleMapping.add(role);
    }

    /**
     * 作用：修改
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public Integer update(Role role) throws Exception {
        return roleMapping.update(role);
    }

    /**
     * 作用：查看是否有下级
     * @param role_relation_no
     * @return
     * @throws Exception
     */
    @Override
    public Role findByRoleRelationNo(String role_relation_no) throws Exception {
        List<Role> roles = roleMapping.findByRoleRelationNo(role_relation_no);
        if(roles.size() > 0){
            return roles.get(0);
        }
        return null;
    }

    /**
     * 作用：查看是否有下级
     * @param role_relation_no
     * @return
     * @throws Exception
     */
    @Override
    public List<Role> findByAllRelationNo(String role_relation_no) throws Exception {
        return roleMapping.findByAllRelationNo(role_relation_no);
    }

}
