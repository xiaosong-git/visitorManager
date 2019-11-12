package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.CompanyMapping;
import com.manage.model.Company;
import com.manage.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private CompanyMapping companyMapping;

    @Override
    public List<Company> findByPage(String relation_no, String companyName , String name, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return companyMapping.findByPage(relation_no,companyName,name);
    }

    @Override
    public Integer add(Company company) throws Exception {
        return companyMapping.add(company);
    }

    @Override
    public Integer update(Company company) throws Exception {
        return companyMapping.update(company);
    }

    @Override
    public Integer del(List<Long> ids) throws Exception {
        return companyMapping.del(ids);
    }

    @Override
    public Company findByParam(String paramName, String paramText) throws Exception {
        return companyMapping.findByParam(paramName,paramText);
    }

    @Override
    public Company findByCompanyCode(String companyCode) throws Exception {
        return companyMapping.findByCompanyCode(companyCode);
    }
}
