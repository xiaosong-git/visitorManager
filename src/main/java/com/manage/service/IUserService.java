package com.manage.service;

import com.manage.model.User;
import com.manage.utils.result.Result;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
public interface IUserService {

    public User findByLoginName(String loginName) throws Exception;

    /**
     * 统计机构下用户数（本级以及子孙级）
     * @param relationNo
     * @param createDate
     * @return
     */
    public Long countUserByOrg(String relationNo, String createDate) throws Exception;

    public List<User> findByPageLeft(User user, int pageNo, int pageSize) throws Exception;

    /**
     * @param userId
     * @return
     * @throws Exception
     */
    public User findByIdLeft(Long userId) throws Exception;

    /**
     * 统计机构下的用户数（只包含本级）
     * @param orgId 机构Id
     * @return
     */
    public Long countUserNumByOrgId(Long orgId) throws Exception;

    /**
     * 根据用户id查询详情
     * @param userId
     * @return
     */
    public User findById(Long userId)throws Exception;

    /**
     * 根据phone查询是否存在
     * @param phone
     * @return
     */
    public User findByPhone(String phone)throws Exception;

    /**
     * 分页查询正在审核实名用户
     * @param companyName
     * @param org_name
     * @param realName
     * @param loginName
     * @param phone
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<User> findUserIsAudit(String companyName,String org_name, String realName, String loginName, String phone, int pageNo, int pageSize) throws Exception;

    /**
     * 实名审成功
     * @param id
     * @param isAuth
     * @return
     */
    public Result txUpdateSuccess(Long id, String isAuth)throws Exception;

    /**
     * 实名审核失败
     * @param id
     * @param isAuth
     * @param failReason
     * @return
     */
    public Integer txUpdateFail(Long id, String isAuth, String failReason)throws Exception;

    /**
     * 根据用户id将用户身份证置空
     * @param userId
     * @return
     */
    public Integer updateIdNoByUserId(Long userId) throws Exception;

    /**
     * 查找详情
     * @param id
     * @return
     */
    public User findDetailById(Long id) throws Exception;

    /**
     * 作用：更新实名 插卡和信息
     * @param idNO
     * @param userId
     * @param realName
     * @param phone
     * @return
     * @throws Exception
     */
    public Integer txUpInfoAndInsCardNo(Long userId, String idNO, String realName, String phone) throws Exception;

    /**
     * 作用：更改身份证正面
     * @param idFrontImgUrl
     * @param userId
     * @return
     * @throws Exception
     */
    public Integer updateIdFrontImgUrl(Long userId, String idFrontImgUrl) throws Exception;

    /**
     * 作用：更改身份证反面
     * @param idOppositeImgUrl
     * @param userId
     * @return
     * @throws Exception
     */
    public Integer updateIdOppositeImgUrl(Long userId, String idOppositeImgUrl) throws Exception;

    /**
     * 作用：更改手持身份证
     * @param idHandleImgUrl
     * @param userId
     * @return
     * @throws Exception
     */
    public Integer updateIdHandleImgUrl(Long userId, String idHandleImgUrl) throws Exception;

    /**
     * 作用：更改银行卡正面
     * @param bankCardImgUrl
     * @param userId
     * @return
     * @throws Exception
     */
    public Integer updateBankCardImgUrl(Long userId, String bankCardImgUrl) throws Exception;

    /**
     * 修改登录账号
     * @param userId
     * @param phone
     * @return
     */
    public Integer updatePhone(Long userId, String phone) throws Exception;

    /**
     * 修改用户公司，角色
     * @param phone
     * @param companyId
     * @param role
     * @return
     * @throws Exception
     */
    public Integer updateCompanyIdAndRole(String orgId,String relationNo,String phone,String companyId,String role) throws Exception;

    /**
     * 添加用户
     * @param user
     * @return
     * @throws Exception
     */
    public Integer addUser(User user) throws Exception;

    /**
     * 根据手机号修改用户角色
     * @param phone
     * @param role
     * @return
     * @throws Exception
     */
    public Integer updatePhoneByRole(String phone,String role) throws Exception;

    /**
     * 公司id
     * @param companyId
     * @return
     * @throws Exception
     */
    public List<User> findCompanyId(Long companyId) throws Exception;
}
