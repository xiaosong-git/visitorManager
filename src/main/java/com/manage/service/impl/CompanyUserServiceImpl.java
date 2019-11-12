package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.TblCompanyUserMapper;
import com.manage.model.TblCompanyUser;
import com.manage.service.CompanyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {

    @Autowired
    private TblCompanyUserMapper companyUserMapper;

    @Override
    public List<TblCompanyUser> findByPage(String createDate, String userName, String status, String companyName,Long orgId,int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return companyUserMapper.findByPage(createDate,userName,status,companyName,orgId);
    }
}
