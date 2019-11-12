package com.manage.service;

import com.manage.model.TblCompanyUser;

import java.util.List;

public interface CompanyUserService {
    List<TblCompanyUser> findByPage( String createDate,
                                     String userName,
                                     String status,
                                     String companyName,Long orgId,int pageNo, int pageSize
                                    );
}
