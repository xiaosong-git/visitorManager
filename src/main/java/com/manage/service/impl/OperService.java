package com.manage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.manage.dao.OperMapping;
import com.manage.model.Oper;
import com.manage.service.IOperService;
import com.manage.utils.mapper.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
@Service
//@Transactional(readOnly = true)
public class OperService implements IOperService {

    @Autowired
    private OperMapping operMapping;
    @Override
    public Oper findByLoginName(String loginName) throws Exception {
        return operMapping.selectByLoginName(loginName);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return operMapping.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Oper record) {
        return operMapping.insert(record);
    }

    @Override
    public int insertSelective(Oper record) {
        return operMapping.insertSelective(record);
    }

    @Override
    public Oper selectByPrimaryKey(Long id) {
        return operMapping.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Oper record) {
        return operMapping.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Oper record) {
        return operMapping.updateByPrimaryKey(record);
    }

    @Override
    public Oper selectByLoginName(String loginName) {
        return operMapping.selectByLoginName(loginName);
    }

    @Override
    public List<Oper> selectAll() {
        return operMapping.selectAll();
    }

    @Override
    public List<Oper> selectByOrgRelationNo(String orgRelationNo) {
        return operMapping.selectByOrgRelationNo(orgRelationNo);
    }

    /**
     * 分页查询
     * @param pageNo 页号
     * @param pageSize 每页显示记录数
     * @return
     */
    @Override
    public Page<Oper> findByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return operMapping.findByPage();
    }

    /**
     * 分页查询数据,连表
     * @return
     */
    @Override
    public Page<Oper> findByPageLeftOrg(String operName, Long org_id, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return operMapping.findByPageLeftOrg(operName,org_id);
    }

    public Page<Oper> getOrgPageOper(String operName, String orgName, String relationNo, String orgType , Long orgId, int pageNo, int pageSize){
        PageHelper.startPage(pageNo, pageSize);
        return operMapping.getOrgPageOper(operName,orgName,relationNo,orgType,orgId);
    }

    @Override
    public void batchDelete(List<String> ids) {
        operMapping.batchDelete(ids);
    }

    @Override
    public List<Oper> findSalesmanByOrgId(Long orgId) throws Exception {
        return operMapping.findSalesmanByOrgId(orgId);
    }

    /**
     * 修改操作员密码 LZ
     * @param oper
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateOperPwd(Oper oper) throws Exception {
       if(oper != null){
           return operMapping.updateOperPwd(oper);
       }
       return 0;
    }

    /**
     * 通过Id查询
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Oper findById(Long id) throws Exception {
        if(id != null){
            return operMapping.findById(id);
        }
        return null;
    }
    public Oper findByCode(String operCode)throws  Exception{
        return operMapping.findByCode(operCode);
    }

    /**
     * 插入操作员时插入公告记录
     * @throws Exception
     */
    @Override
    public void txInsOperAndorgNotice(Oper oper) throws Exception{
        Integer id=operMapping.save(oper);
    }
    public Integer updateById(Oper oper) throws Exception{
     return   operMapping.updateById(oper);
    };
    public List<Oper> findByOrgIdAndRoleId(Long orgId, String roleRelation, int page, int pageSize) throws Exception{
        PageHelper.startPage(page, pageSize);
        return operMapping.findByOrgIdAndRoleId(orgId,roleRelation);
    }

    /**
     * 根据LoginName查询id
     * @throws Exception
     */
    @Override
    public Oper findLoginNameId(String loginName) throws Exception {
        return operMapping.findLoginNameId(loginName);
    }
    public List<Oper> findByOrgId(Long orgId) throws Exception{
        return operMapping.findByOrgId(orgId);
    }
    public List<Oper> agentOperList(String operName, String orgName, String operRelationNo, String roleRelation, String orgType, String orgId, int page, int pageSize) throws Exception{
        PageHelper.startPage(page, pageSize);
        return operMapping.agentOperList( operName, orgName, operRelationNo, roleRelation, orgType, orgId);
    }

}
