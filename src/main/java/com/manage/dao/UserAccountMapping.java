package com.manage.dao;

import com.manage.model.UserAccount;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAccountMapping extends MyMapper<UserAccount> {

    /**
     * 修改用户账户冻结状态
     * @param id
     * @param cstatus
     * @param handleCause
     * @param operId
     * @return
     * @throws Exception
     */
    Integer updateCstatus(@Param("id") Long id, @Param("cstatus") String cstatus, @Param("handleCause") String handleCause, @Param("operId") Long operId, @Param("handleTime") String handleTime);

    /**
     * 重置登录密码
     * @param userId
     * @param sysPwd
     * @return
     * @throws Exception
     */
    Integer updateLoginPwd(@Param("userId") Long userId, @Param("sysPwd") String sysPwd);

    /**
     * 重置支付密码
     * @param userId
     * @param payPwd
     * @return
     * @throws Exception
     */
    Integer updatePayPwd(@Param("userId") Long userId, @Param("payPwd") String payPwd);

    Integer addUserAccount(@Param("userAccount") UserAccount userAccount);
}