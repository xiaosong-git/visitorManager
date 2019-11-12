package com.manage.service;

import com.manage.model.Role;

import java.util.List;

/**
 * Created by L on 2017/8/8.
 */
public interface IRoleService {

   /* public int deleteByPrimaryKey(Long id);

    public int insert(Role record);

    public int insertSelective(Role record);

    public Role selectByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(Role record);

    public int updateByPrimaryKey(Role record);*/

    public List<Role> selectAll() throws Exception;
    /**
     * 查询本级以及下级的角色 LZ
     * @param relation_no 登录的操作员的relation_no
     * @return
     * @throws Exception
     */
    public List<Role> findLowerRole(String relation_no) throws Exception;

    /**
     * 通过角色Id查询
     * @param roleId 角色Id
     * @return
     * @throws Exception
     */
    public Role findById(Long roleId) throws Exception;

    /**
     * 作用：删除
     * @param id
     * @throws Exception
     */
    public void batchDelete(String id) throws Exception;

    /**
     * 作用：添加信息
     * @param role
     * @return
     * @throws Exception
     */
    public Integer add(Role role) throws Exception;

    /**
     * 作用：修改
     * @param role
     * @return
     * @throws Exception
     */
    public Integer update(Role role) throws Exception;

    /**
     * 作用：查看是否有下级
     * @param role_relation_no
     * @return
     * @throws Exception
     */
    public Role findByRoleRelationNo(String role_relation_no) throws Exception;

    /**
     * 作用：查看是否有下级
     * @param role_relation_no
     * @return
     * @throws Exception
     */
    public List<Role> findByAllRelationNo(String role_relation_no) throws Exception;

}
