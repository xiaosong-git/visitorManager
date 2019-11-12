package com.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.manage.dao.UserMapping;
import com.manage.model.User;
import com.manage.service.IUserAccountService;
import com.manage.service.IUserService;
import com.manage.utils.RedisUtil;
import com.manage.utils.StringUtil;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by L on 2017/8/8.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapping userMapping;

    @Autowired
    private ParamsService paramsService;

    @Autowired
    private IUserAccountService userAccountService;

    @Override
    public User findByLoginName(String loginName) throws Exception {
        return userMapping.selectByLoginName(loginName);
    }

    /**
     * 统计机构下用户数
     * @param relationNo
     * @param createDate
     * @return
     */
    @Override
    public Long countUserByOrg(String relationNo, String createDate) throws Exception {
        return userMapping.countUserByOrg(relationNo,createDate);
    }

    @Override
    public List<User> findByPageLeft(User user, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return userMapping.findByPageLeft(user);
    }

    @Override
    public User findByIdLeft(Long userId) throws Exception {
        return userMapping.findByIdLeft(userId);
    }
    /**
     * 统计机构下的用户数（只包含本级）
     * @param orgId 机构Id
     * @return
     */
    @Override
    public Long countUserNumByOrgId(Long orgId) throws Exception {
        if(orgId != null){
            return userMapping.countUserNumByOrgId(orgId);
        }
        return new Long(0);
    }

    /**
     * 根据用户id查询详情
     * @param userId
     */
    @Override
    public User findById(Long userId) throws Exception {
        return userMapping.findById(userId);
    }

    /**
     * 根据phone查询是否存在
     * @param phone
     * @return
     */
    @Override
    public User findByPhone(String phone) throws Exception {
        return userMapping.findByPhone(phone);
    }

    /**
     * 分页查询正在审核实名用户
     * @param org_name
     * @param realName
     * @param loginName
     * @param phone
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<User> findUserIsAudit(String companyName,String org_name, String realName, String loginName, String phone, int pageNo, int pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        return userMapping.findUserIsAudit(companyName,org_name,realName,loginName,phone);
    }

    /**
     * 实名审成功
     * @param id
     * @param isAuth
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Result txUpdateSuccess(Long id, String isAuth) throws Exception {
        /**
         * 1,购买保险
         */
        /*InsuranceLs insuranceLs = insuranceLsService.findByUserId(Long.valueOf(id));
        if(insuranceLs == null || !insuranceLsService.isSuccessInsuranceLs(insuranceLs)){
            //无保单流水 或者 不是成功的保单流水，购买保险
            User user = this.findById(id);
            String idNo = user.getIdNO();
            String realName = user.getRealName();
            Result buyInsurance = insuranceLsService.buyInsurance(idNo, user, realName);
            if (buyInsurance.getVerify().get("sign").equals("fail")){
                return buyInsurance;
            }
        }*/
        /**
         * 2,购买成功，修改状态
         */
        String authDate = StringUtil.getCurrentDateTime("yyyy-MM-dd");
        String authTime = StringUtil.getCurrentDateTime("HH:mm:ss");
        Integer update = userMapping.updateSuccess(id,isAuth,authDate,authTime);
        if (update>0) {
            String key=paramsService.findByParamName("apiAuthCheckRedisDbIndex");
            String verifyKey = id+"_isAuth";
            RedisUtil.setStr(verifyKey, isAuth, Integer.parseInt(key), null);
        }
        return update>0 ? ResultData.unDataResult("success","操作成功") : Result.unDataResult("fail","操作失败");
    }

    /**
     * 实名审核失败
     * @param id
     * @param isAuth
     * @param failReason
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Integer txUpdateFail(Long id, String isAuth, String failReason) throws Exception {
        String authDate = StringUtil.getCurrentDateTime("yyyy-MM-dd");
        String authTime = StringUtil.getCurrentDateTime("HH:mm:ss");
        Integer update = userMapping.updateFail(id,isAuth,failReason,authDate,authTime);
        if (update>0){
            String key=paramsService.findByParamName("apiAuthCheckRedisDbIndex");
            String verifyKey = id+"_isAuth";
            RedisUtil.setStr(verifyKey, isAuth, Integer.parseInt(key), null);
            //将该用户的身份证号置空
            updateIdNoByUserId(id);
        }
        return update;
    }

    /**
     * 根据用户id将用户身份证置空
     * @param userId
     * @return
     */
    @Override
    public Integer updateIdNoByUserId(Long userId) throws Exception {
        return userMapping.updateIdNoByUserId(userId);
    }

    /**
     * 查找详情
     * @param id
     * @return
     */
    @Override
    public User findDetailById(Long id) throws Exception {
        List<User>  users = userMapping.findDetailById(id);
        if (users!=null && users.size()>0){
            return users.get(0);
        }
        return null;
    }
    /**
     * 作用：更新实名 插卡和信息
     * @param idNO
     * @param userId
     * @param realName
     * @param phone
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Integer txUpInfoAndInsCardNo(Long userId, String idNO, String realName, String phone) throws Exception {
        User user =new User();
        user.setId(userId);
        user.setIdNO(idNO);
        user.setRealName(realName);
        user.setIdType("01");
        user.setIsAuth("T");
        user.setAuthDate(StringUtil.getCurrentDateTime("yyyy-MM-dd"));
        user.setAuthTime(StringUtil.getCurrentDateTime("HH:mm:ss"));
        Integer update = userMapping.updateInfo(user);
        if (update>0){
            String verifyKey = userId+"_isAuth";
            RedisUtil.setStr(verifyKey, "T", 11, null);
        }
        return userAccountService.updatePayPwd(userId,"e10adc3949ba59abbe56e057f20f883e");
    }

    /**
     * 作用：更改身份证正面
     * @param idFrontImgUrl
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateIdFrontImgUrl(Long userId, String idFrontImgUrl) throws Exception {
        return userMapping.updateIdFrontImgUrl(userId,idFrontImgUrl);
    }

    /**
     * 作用：更改身份证反面
     * @param idOppositeImgUrl
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateIdOppositeImgUrl(Long userId, String idOppositeImgUrl) throws Exception {
        return userMapping.updateIdOppositeImgUrl(userId,idOppositeImgUrl);
    }

    /**
     * 作用：更改手持身份证
     * @param idHandleImgUrl
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateIdHandleImgUrl(Long userId, String idHandleImgUrl) throws Exception {
        return userMapping.updateIdHandleImgUrl(userId,idHandleImgUrl);
    }

    /**
     * 作用：更改银行卡正面
     * @param bankCardImgUrl
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateBankCardImgUrl(Long userId, String bankCardImgUrl) throws Exception {
        return userMapping.updateBankCardImgUrl(userId,bankCardImgUrl);
    }

    @Override
    public Integer updatePhone(Long userId, String phone) throws Exception {
        return userMapping.updatePhone(userId,phone);
    }

    @Override
    public Integer updateCompanyIdAndRole(String orgId,String relationNo,String phone,String companyId, String role) throws Exception {
        return userMapping.updateCompanyIdAndRole(orgId,relationNo,phone,companyId,role);
    }

    @Override
    public Integer addUser(User user) throws Exception {
        return userMapping.addUser(user);
    }

    @Override
    public Integer updatePhoneByRole(String phone, String role) throws Exception {
        return userMapping.updatePhoneByRole(phone,role);
    }

    @Override
    public List<User> findCompanyId(Long companyId) throws Exception {
        return userMapping.findCompanyId(companyId);
    }
}
