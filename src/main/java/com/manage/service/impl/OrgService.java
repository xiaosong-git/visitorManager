package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.OrgMapping;
import com.manage.model.Oper;
import com.manage.model.Org;
import com.manage.service.IOperService;
import com.manage.service.IOrgService;
import com.manage.utils.mapper.SqlUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by L on 2017/8/13.
 */
@Service
public class OrgService implements IOrgService {

    @Autowired
    private OrgMapping orgMapping;

    @Autowired
    private IOperService operService;

    @Override
    public List<Org> findByPageLeft(@Param("org") Org org, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return orgMapping.findByPageLeft(org);
    }

    @Override
    public Integer addOrg(Org org) throws Exception {
        return orgMapping.insertSelectiveGetKey(org);
    }

    /**
     * 统计其下机构数
     * @param relationNo
     * @return
     */
    @Override
    public Long countOrgByOrg(String relationNo,String createDate) throws Exception {
        return orgMapping.countOrgByOrg(relationNo,createDate);
    }

    /**
     * 添加代理商同时添加代理商账户
     * @param org
     * @return 成功返回1 失败返回0
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Boolean addOrgAndOrgAccount(Org org, Oper oper) throws Exception {
        //添加机构
        Integer isAddOrg = addOrg(org);

        //添加操作员
        oper.setOrg_id(org.getId());
        Integer isAddOper = operService.insert(oper);

        return (isAddOrg>0 && isAddOper>=0) ? true : false;
    }

    @Override
    public Org findById(Long orgId) throws Exception {
        return orgMapping.selectByPrimaryKey(orgId);
    }

    /**
     * 机构详情
     * @param orgId
     * @return
     */
    @Override
    public Org findByIdUnion(Long orgId) throws Exception {
        return orgMapping.findByIdUnion(orgId);
    }

    /**
     * 根据单个参数名查找
     * @param paramName
     * @param paramText
     * @return
     */
    public Org findByParam(String paramName, String paramText) throws Exception{
        return orgMapping.findByParam(paramName,paramText);
    }

    @Override
    public Integer updateOrgSelective(Org org) throws Exception {
        return orgMapping.updateByPrimaryKeySelective(org);
    }

    @Override
    public List<Org> findSubordinateOrg(Long id) throws Exception {
        return orgMapping.findSubordinateOrg(id);
    }

    @Override
    public List<Org> findOrgSid(Long id, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return orgMapping.findSubordinateOrg(id);
    }
    /**
     * 查询操作员下的所有机构
     * @param operId
     * @param orgName
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<Org> findByOperId(Long operId, String orgName, int pageNo, int pageSize) throws Exception {
        if(operId != null){
            PageHelper.startPage(pageNo, pageSize);
            return orgMapping.findByOperId(operId, orgName);
        }
        return null;
    }

    /**
     * 通过relation_no查询操作员
     * @param relation_no
     * @return
     */
    @Override
    public Org findByRelationNo(String relation_no) throws Exception {
        return orgMapping.findByRelationNo(relation_no);
    }

    @Override
    public List<Org> findSubBySid(String  sid) throws Exception{

        return orgMapping.findOrgSid(sid);
    }
    @Override
    public void batchDelete(String ids) throws Exception{
        orgMapping.batchDelete(SqlUtil.getIdsList(ids));
    }

    @Override
    public Org findByPhone(String phone) throws Exception {
        return orgMapping.findByPhone(phone);
    }

    @Override
    public Integer updateStatus(Org org) throws Exception {
        return orgMapping.updateStatus(org);
    }

    @Override
    public List<Org> getAllOrg(String relation_no) throws Exception {
        return orgMapping.getAllOrg(relation_no);
    }

}
