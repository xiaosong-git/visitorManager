package com.manage.service;

import com.github.pagehelper.Page;
import com.manage.model.Oper;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
public interface IOperService {

    public Oper findByLoginName(String loginName) throws Exception;

    public int deleteByPrimaryKey(Long id);

    public int insert(Oper record);

    public int insertSelective(Oper record);

    public Oper selectByPrimaryKey(Long id);

    public int updateByPrimaryKeySelective(Oper record);

    public int updateByPrimaryKey(Oper record);

    public Oper selectByLoginName(String loginName);

    public List<Oper> selectAll();

    public List<Oper> selectByOrgRelationNo(String orgRelationNo);

    /**
     * 分页查询
     * @param pageNo 页号
     * @param pageSize 每页显示记录数
     * @return
     */
    public Page<Oper> findByPage(int pageNo, int pageSize);

    /**
     * 分页查询数据,连表
     * @return
     */
    public Page<Oper> findByPageLeftOrg(String oper_name,Long org_id, int pageNo, int pageSize);

    public Page<Oper> getOrgPageOper(String operName, String orgName, String relationNo, String orgType, Long orgId, int pageNo, int pageSize);

    public void batchDelete(List<String> ids);

    public List<Oper> findSalesmanByOrgId(Long orgId) throws Exception;

    /**
     * 修改操作员密码 LZ
     * @param oper
     * @return
     * @throws Exception
     */
    public Integer updateOperPwd(Oper oper) throws Exception;

    /**
     * 通过Id查询
     * @param id
     * @return
     * @throws Exception
     */
    public Oper findById(Long id) throws Exception;

    public Oper findByCode(String operCode)throws Exception;

    /**20180609
     * 插入操作员时插入公告记录
     * @throws Exception
     */
    public void txInsOperAndorgNotice(Oper oper) throws Exception;

    public Integer updateById(Oper oper) throws Exception;

    public List<Oper> findByOrgIdAndRoleId(Long orgId, String roleRelation, int page, int pageSize) throws Exception;

    public Oper findLoginNameId(String loginName) throws Exception;
    public List<Oper> findByOrgId(Long orgId) throws Exception;

    public List<Oper> agentOperList(String operName, String orgName, String operRelationNo, String roleRelation, String orgType, String orgId, int page, int pageSize) throws Exception;


}
