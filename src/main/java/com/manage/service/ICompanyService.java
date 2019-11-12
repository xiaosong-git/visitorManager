package com.manage.service;

import com.manage.model.Company;
import com.manage.model.News;

import java.util.List;

public interface ICompanyService {

    /**
     * 分页查询公司
     * @param companyName
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Company> findByPage(String relation_no, String companyName, String name, int pageNo, int pageSize)throws Exception;

    /**
     * 插入公司数据
     * @param company
     * @return
     * @throws Exception
     */
    public Integer add(Company company)throws Exception;

    /**
     * 修改公司数据
     * @param company
     * @return
     * @throws Exception
     */
    public Integer update(Company company)throws Exception;

    /**
     * 删除公司数据
     * @param ids
     * @return
     * @throws Exception
     */
    public Integer del(List<Long> ids)throws Exception;

    /**
     * 根据单个参数名查找
     * @param paramName
     * @param paramText
     * @return
     */
    public Company findByParam(String paramName, String paramText) throws Exception;

    /**
     * 根据编码查询公司
     * @param companyCode
     * @return
     * @throws Exception
     */
    public Company findByCompanyCode(String companyCode) throws Exception;
}
