package com.manage.service;

import com.manage.model.Oper;
import com.manage.model.Org;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/8/13.
 */
public interface IOrgService {

    /**
     * 分页获取机构
     * @param org
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Org> findByPageLeft(@Param("org") Org org, int pageNo, int pageSize) throws Exception;

    public Integer addOrg(Org org) throws Exception;

    /**
     * 统计其下机构数
     * @param relationNo
     * @return
     */
    public Long countOrgByOrg(String relationNo, String createDate) throws Exception;

    /**
     * 添加代理商同时添加代理商账户
     * @param org
     * @return
     * @throws Exception
     */
    public Boolean addOrgAndOrgAccount(Org org, Oper oper) throws Exception;

    public Org findById(Long orgId) throws Exception;

    /**
     * 机构详情
     * @param orgId
     * @return
     */
    public Org findByIdUnion(Long orgId) throws Exception;

    /**
     * 根据单个参数名查找
     * @param paramName
     * @param paramText
     * @return
     */
    public Org findByParam(String paramName, String paramText) throws Exception;

    public Integer updateOrgSelective(Org org) throws Exception;

    /**
     * 查询所有下级代理商
     * @param id
     * @return
     * @throws Exception
     */
    public List<Org> findSubordinateOrg(Long id)throws Exception;

    /**
     * 分页查询当前代理商的所有下级代理商
     * @param id
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     * @Author jaysonQ
     */
    public List<Org> findOrgSid(Long id, int pageNo, int pageSize)throws Exception;

    /**
     * 查询操作员下的所有机构
     * @param operId
     * @param orgName
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Org> findByOperId(Long operId, String orgName, int pageNo, int pageSize) throws Exception;

    /**
     * 通过relation_no查询操作员
     * @param relation_no
     * @return
     */
    public Org findByRelationNo(String relation_no) throws Exception;

    public List<Org> findSubBySid(String sid) throws Exception;

    public void batchDelete(String ids) throws Exception;

    public Org findByPhone(String phone) throws Exception;

    /**
     * 修改机构状态
     * @param org
     * @return
     * @throws Exception
     */
    public Integer updateStatus(Org org)throws Exception;

    /**
     * 获取本机构下的大厦
     * @param relation_no
     * @return
     * @throws Exception
     */
    public List<Org> getAllOrg(String relation_no) throws Exception;
}
