package com.manage.dao;

import com.github.pagehelper.Page;
import com.manage.model.Oper;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperMapping extends MyMapper<Oper> {

    Oper selectByLoginName(String loginName);

    List<Oper> selectAll();

    List<Oper> selectByOrgRelationNo(String orgRelationNo);

    /**
     * 分页查询数据
     *
     * @return
     */
    Page<Oper> findByPage();

    /**
     * 分页查询数据,连表
     *
     * @return
     */
    Page<Oper> findByPageLeftOrg(@Param("oper_name") String oper_name,@Param("org_id") Long org_id);

    /**
     * 查询本机构及其子级
     * @param operName
     * @param orgName
     * @param relationNo
     * @param orgType
     * @return
     */
    public Page<Oper> getOrgPageOper(@Param("oper_name") String operName, @Param("orgName") String orgName, @Param("relationNo") String relationNo, @Param("orgType") String orgType, @Param("orgId") Long orgId);

    void batchDelete(@Param("ids") List<String> ids);

    List<Oper> findSalesmanByOrgId(Long orgId);

    /**
     * 修改操作员密码 lz
     *
     * @param oper
     * @return
     */
    Integer updateOperPwd(Oper oper);

    /**
     * 通过Id查询
     *
     * @param id
     * @return
     */
    Oper findById(@Param("id") Long id);

    Oper findByCode(@Param("operCode") String operCode);

    Integer txInsOperAndorgNotice(Oper oper);

    public Integer updateById(@Param("oper") Oper oper) throws Exception;

    public List<Oper> findByOrgIdAndRoleId(@Param("orgId") Long orgId, @Param("roleRelation") String roleRelation);

    public Integer save(@Param("oper") Oper oper) throws Exception;

    Oper findLoginNameId(@Param("loginName") String loginName);

    List<Oper> findByOrgId(@Param("orgId") Long orgId);

    public List<Oper> agentOperList(@Param("operName") String operName, @Param("orgName") String orgName, @Param("operRelationNo") String operRelationNo, @Param("roleRelation") String roleRelation, @Param("orgType") String orgType, @Param("orgId") String orgId);
}