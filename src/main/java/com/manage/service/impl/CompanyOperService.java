package com.manage.service.impl;

import com.manage.dao.CompanyOperMapping;
import com.manage.model.Company;
import com.manage.model.CompanyOper;
import com.manage.service.ICompanyOperService;
import com.manage.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyOperService implements ICompanyOperService {

    @Autowired
    private CompanyOperMapping companyOperMapping;

    @Autowired
    private ICompanyService companyService;

    @Override
    public CompanyOper findByLoginName(String login_name) throws Exception {
        return companyOperMapping.findByLoginName(login_name);
    }

    @Override
    public int addCompanyOper(String oper_name,  long company_id, String pwd, String login_name) throws Exception {
        CompanyOper companyOper = new CompanyOper();
        companyOper.setOper_name(oper_name);
        companyOper.setCompany_id(company_id);
        companyOper.setRole_id(2l);
        companyOper.setPwd(pwd);
        companyOper.setSstatus("0");
        companyOper.setLogin_name(login_name);
        return companyOperMapping.addCompanyOper(companyOper);
    }
}
