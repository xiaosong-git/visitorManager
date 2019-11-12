package com.manage.service;

import com.manage.model.CompanyOper;

/**
 * 管理员
 * Created by L on 2019/3/11.
 */
public interface ICompanyOperService {

    public CompanyOper findByLoginName(String login_name) throws Exception;

    public int addCompanyOper(String oper_name, long company_id, String pwd, String login_name) throws Exception;
}
