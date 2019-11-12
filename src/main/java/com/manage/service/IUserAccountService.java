package com.manage.service;

import com.manage.model.UserAccount;

public interface IUserAccountService {
    /**
     * 修改用户账户冻结状态
     * @param id
     * @param cstatus
     * @param handleCause
     * @param operId
     * @return
     * @throws Exception
     */
    public Integer updateCstatus(Long id, String cstatus, String handleCause, Long operId)throws Exception;

    /**
     * 重置登录密码
     * @param userId
     * @param sysPwd
     * @return
     * @throws Exception
     */
    public Integer updateLoginPwd(Long userId, String sysPwd)throws Exception;

    /**
     * 重置支付密码
     * @param userId
     * @param payPwd
     * @return
     * @throws Exception
     */
    public Integer updatePayPwd(Long userId, String payPwd)throws Exception;

    /**
     * 插入用户账户信息
     * @param userAccount
     * @return
     * @throws Exception
     */
    public Integer addUserAccount(UserAccount userAccount) throws Exception;
}
