package com.manage.dao;

import com.manage.model.Role;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapping extends MyMapper<Role> {

    List<Role> selectAll();

    /**
     * 查询本级以及子孙级的角色
     * @param relation_no 登录的操作员角色的RelationNo
     * @return
     */
    List<Role> findLowerRole(String relation_no);
    /**
     * 通过角色Id查询
     * @param roleId 角色Id
     * @return
     */
    Role findById(Long roleId);

    /**
     * 作用：删除
     * @param id
     * @throws Exception
     */
    void batchDelete(String id);

    /**
     * 作用：添加信息
     * @param role
     * @return
     * @throws Exception
     */
    Integer add(@Param("role") Role role);

    /**
     * 作用：修改
     * @param role
     * @return
     * @throws Exception
     */
    Integer update(@Param("role") Role role);

    /**
     * 作用：查看是否有下级
     * @param role_relation_no
     * @return
     * @throws Exception
     */
    List<Role> findByRoleRelationNo(@Param("role_relation_no") String role_relation_no);

    /**
     * 作用：查看是否有下级
     * @param role_relation_no
     * @return
     * @throws Exception
     */
    List<Role> findByAllRelationNo(@Param("role_relation_no") String role_relation_no);
}