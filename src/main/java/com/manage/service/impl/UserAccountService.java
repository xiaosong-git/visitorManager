package com.manage.service.impl;

import com.manage.dao.UserAccountMapping;
import com.manage.model.UserAccount;
import com.manage.service.IUserAccountService;
import com.manage.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService implements IUserAccountService {

    @Autowired
    private UserAccountMapping userAccountMapping;

    /**
     * 修改用户账户冻结状态
     * @param id
     * @param cstatus
     * @param handleCause
     * @param operId
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateCstatus(Long id, String cstatus, String handleCause, Long operId) throws Exception {
        String handleTime = StringUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
        return userAccountMapping.updateCstatus(id,cstatus,handleCause,operId,handleTime);
    }

    /**
     * 重置登录密码
     * @param userId
     * @param sysPwd
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateLoginPwd(Long userId, String sysPwd) throws Exception {
        return userAccountMapping.updateLoginPwd(userId,sysPwd);
    }

    /**
     * 重置支付密码
     * @param userId
     * @param payPwd
     * @return
     * @throws Exception
     */
    @Override
    public Integer updatePayPwd(Long userId, String payPwd) throws Exception {
        return userAccountMapping.updatePayPwd(userId,payPwd);
    }

    @Override
    public Integer addUserAccount(UserAccount userAccount) throws Exception {
        return userAccountMapping.addUserAccount(userAccount);
    }
}
