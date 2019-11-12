package com.manage.dao;

import com.manage.model.User;
import com.manage.utils.mapper.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapping extends MyMapper<User> {

    User selectByLoginName(String loginName);

    /**
     * 统计机构下用户数（本级以及子孙级）
     * @param relationNo
     * @param createDate
     * @return
     */
    Long countUserByOrg(@Param("relationNo") String relationNo, @Param("createDate") String createDate);

    /**
     * 分页查询用户数据
     * @param user
     * @return
     */
    List<User> findByPageLeft(@Param("user") User user);

    /**
     * 用户详情
     * @param userId
     * @return
     */
    User findByIdLeft(Long userId);

    /**
     * 统计机构下的用户数（只包含本级）
     * @param orgId 机构Id
     * @return
     */
    Long countUserNumByOrgId(@Param("orgId") Long orgId);

    User findById(@Param("id") Long userId);

    User findByPhone(@Param("phone") String phone);

    List<User> findUserIsAudit(@Param("companyName") String companyName,@Param("org_name") String org_name, @Param("realName") String realName, @Param("loginName") String loginName, @Param("phone") String phone);

    Integer updateSuccess(@Param("id") Long id, @Param("isAuth") String isAuth, @Param("authDate") String authDate, @Param("authTime") String authTime);

    Integer updateFail(@Param("id") Long id, @Param("isAuth") String isAuth, @Param("failReason") String failReason, @Param("authDate") String authDate, @Param("authTime") String authTime);

    Integer updateIdNoByUserId(@Param("id") Long userId);

    List<User> findDetailById(@Param("id") Long id);

    Integer updateInfo(@Param("user") User user);

    Integer updateIdFrontImgUrl(@Param("id") Long userId, @Param("idFrontImgUrl") String idFrontImgUrl);

    Integer updateIdOppositeImgUrl(@Param("id") Long userId, @Param("idOppositeImgUrl") String idOppositeImgUrl);

    Integer updateIdHandleImgUrl(@Param("id") Long userId, @Param("idHandleImgUrl") String idHandleImgUrl);

    Integer updateBankCardImgUrl(@Param("id") Long userId, @Param("bankCardImgUrl") String bankCardImgUrl);

    Integer updatePhone(@Param("id") Long userId, @Param("phone") String phone);

    Integer updateCompanyIdAndRole(@Param("orgId")String orgId,@Param("relationNo")String relationNo,@Param("phone") String phone,@Param("companyId") String companyId,@Param("role") String role);

    Integer addUser(@Param("user") User user);

    Integer updatePhoneByRole(@Param("phone") String phone,@Param("role") String role);

    List<User> findCompanyId(@Param("companyId") Long companyId);
}