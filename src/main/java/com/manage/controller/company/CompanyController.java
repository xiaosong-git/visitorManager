package com.manage.controller.company;

import com.manage.controller.base.BaseController;
import com.manage.dao.CompanyOperMapping;
import com.manage.dao.loginInfoMapper;
import com.manage.model.*;
import com.manage.service.*;
import com.manage.utils.*;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/company")
@RestController
public class CompanyController extends BaseController {
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrgService orgService;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private ICompanyOperService companyOperService;

    @Autowired
    private loginInfoMapper loginInfoMapper;

    @Autowired
    private CompanyOperMapping operMapping;

    /**
     * 分页查询公司
     */
    @RequestMapping(value = "/findByPage")
    public Result findByPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        HttpSession session = request.getSession();
        Oper oper = (Oper)session.getAttribute("oper");
        String relation_no = oper.getOrg_relation_no();
        String companyName = request.getParameter("companyName");
        String name = request.getParameter("name");
        List<Company> companys = companyService.findByPage(relation_no, companyName, name, page, pageSize);
        PageInfo<Company> companyPageInfo = new PageInfo<Company>(companys);
        return companyPageInfo != null ? ResultData.dataResult("success", "读取公司信息成功", companyPageInfo) : Result.unDataResult("fail", "读取公司信息失败");
    }


    @RequestMapping("/test1")
    public String test1(Map<String,Object> map) throws Exception{
        return "111111";
    }


    /**
     * 插入、修改公司数据
     */
    /*@RequestMapping(value = "/addOrExit")
    public Result addNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addOrEdit = request.getParameter("addOrEdit");
        String id = request.getParameter("id");
        String companyCode = request.getParameter("companyCode");
        String companyName = request.getParameter("companyName");
        String phone = request.getParameter("phone");
        String oldPhone = request.getParameter("oldPhone");
        String name = request.getParameter("name");
        String applyType = request.getParameter("applyType");
        String corporationID = request.getParameter("corporationID");
        String licenceNo = request.getParameter("licenceNo");
        String addr = request.getParameter("addr");
        String orgId = request.getParameter("orgId");

        if (StringUtils.isBlank(companyCode) && StringUtils.isBlank(companyName) && StringUtils.isBlank(phone)
                && StringUtils.isBlank(name) && StringUtils.isBlank(applyType) && StringUtils.isBlank(corporationID)
                && StringUtils.isBlank(licenceNo) && StringUtils.isBlank(addr) && StringUtils.isBlank(orgId)) {
            return ResultData.unDataResult("fail", "缺少参数");
        }

        Company company = new Company();
        if (!StringUtils.isBlank(id)) {
            company.setId(Long.parseLong(id));
        }
        company.setCompanyCode(companyCode);
        company.setCompanyName(companyName);
        company.setPhone(phone);
        company.setName(name);
        company.setApplyType(applyType);
        company.setCorporationID(corporationID);
        company.setLicenceNo(licenceNo);
        company.setAddr(addr);
        company.setOrgId(Long.valueOf(orgId));

        Org org = orgService.findById(Long.valueOf(orgId));
        String relationNo = org.getRelation_no()+ VerifyUtil.getNowDate(new Date(), "yyyyMMddHHmmss")+".";

        if ("add".equals(addOrEdit)) {
            User user = userService.findByPhone(phone);
            if (user!=null && user.getCompanyId()!=null){
                return ResultData.unDataResult("fail", "该手机号已经被其他公司注册过");
            }
            if(companyService.findByParam("companyCode",companyCode)!=null){
                return ResultData.unDataResult("fail","公司编码已存在");
            }
            Date date = new Date();
            company.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
            company.setCreateTime(new SimpleDateFormat("HH:mm:ss").format(date));
            int insert = companyService.add(company);
            Company company1 = companyService.findByCompanyCode(companyCode);
            addAndExidUser(orgId,relationNo,name,phone,company1.getId().intValue());
            return insert > 0 ? ResultData.unDataResult("success", "插入成功") : Result.unDataResult("fail", "插入失败");
        } else if ("edit".equals(addOrEdit)) {
            User user = userService.findByPhone(phone);
            if (user!=null && user.getCompanyId()!=null && user.getCompanyId()!=Long.valueOf(id)){
                return ResultData.unDataResult("fail", "该手机号已经被其他公司注册过");
            }
            userService.updatePhoneByRole(oldPhone,"staff");
            addAndExidUser(orgId,relationNo,name,phone,Integer.valueOf(id));
            int update = companyService.update(company);
            return update > 0 ? ResultData.unDataResult("success", "修改成功") : Result.unDataResult("fail", "修改失败");
        } else {
            return ResultData.unDataResult("fail", "系统异常");
        }
    }*/

    @RequestMapping(value = "/addOrExit")
    public Result addNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addOrEdit = request.getParameter("addOrEdit");
        String id = request.getParameter("id");
        String companyName = request.getParameter("companyName");//公司名称
    //    String companyCode = request.getParameter("companyCode");//公司编码自动生成 根据公司名称
        String phone = request.getParameter("phone");//手机号
        String name = request.getParameter("name");//联系人
        String applyType = request.getParameter("applyType");//访问审核类型
        String corporationID = request.getParameter("corporationID");//法人身份证
        String licenceNo = request.getParameter("licenceNo");//营业执照号
        String addr = request.getParameter("addr");//公司地址
        String orgId = request.getParameter("orgId");//大楼ID

        String companyFloor = request.getParameter("companyfloor");  // 公司访问楼层

        String operName = request.getParameter("operName");//操作员姓名
        String pwd = request.getParameter("pwd");//登录人密码
        String loginName = request.getParameter("loginName");//登录人名称

        String yaoyue = request.getParameter("isyaoyue");//是否邀约权限

        if (StringUtils.isBlank(companyName) && StringUtils.isBlank(phone)
                && StringUtils.isBlank(name)
            ) {
            return ResultData.unDataResult("fail", "缺少参数");
        }

        Company company = new Company();
        if (!StringUtils.isBlank(id)) {
            company.setId(Long.parseLong(id));
        }

        company.setCompanyCode(ToFirstChar(companyName).toUpperCase());
        company.setCompanyName(companyName);
        company.setPhone(phone);
        company.setName(name);
        company.setApplyType(StringUtils.isBlank(applyType)? "staff":applyType);
        company.setCorporationID(StringUtils.isBlank(corporationID)?"" :corporationID);
        company.setLicenceNo(StringUtils.isBlank(licenceNo)?"":licenceNo);
        company.setAddr(StringUtils.isBlank(addr)?"":addr);
        company.setOrgId(Long.valueOf(orgId));
        company.setIsyaoyue(yaoyue);
        company.setCompanyfloor(companyFloor);


        if ("add".equals(addOrEdit)) {
            if (companyOperService.findByLoginName(loginName) != null){
                return ResultData.unDataResult("fail", "该操作员的登录名已经被注册过");
            }
            if(companyService.findByParam("companyCode",ToFirstChar(companyName).toUpperCase())!=null){
                return ResultData.unDataResult("fail","公司编码已存在");
            }
            Date date = new Date();
            company.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
            company.setCreateTime(new SimpleDateFormat("HH:mm:ss").format(date));
            int insert = companyService.add(company);
            Company company1 = companyService.findByCompanyCode(ToFirstChar(companyName).toUpperCase());
            companyOperService.addCompanyOper(operName, company1.getId(), pwd, loginName);
            return insert > 0 ? ResultData.unDataResult("success", "插入成功") : Result.unDataResult("fail", "插入失败");
        } else if ("edit".equals(addOrEdit)) {
            int update = companyService.update(company);
            if(!StringUtils.isBlank(loginName)){
                loginInfo info=new loginInfo();
                info.setLoginName(loginName);
                loginInfoExample example=new loginInfoExample();
                example.createCriteria().andCompanyIdEqualTo(Long.parseLong(id));
                int i = loginInfoMapper.updateByExampleSelective(info, example);
            }
            return update > 0 ? ResultData.unDataResult("success", "修改成功") : Result.unDataResult("fail", "修改失败");
        } else {
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    public void addAndExidUser(String orgId,String relationNo,String name, String phone,Integer companyId) throws Exception{
        User user = userService.findByPhone(phone);
        if (user==null){
            User addUser = new User();
            Date date = new Date();
            addUser.setOrgId(Long.valueOf(orgId));
            addUser.setRelationNo(relationNo);
            addUser.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
            addUser.setCreateTime(new SimpleDateFormat("HH:mm:ss").format(date));
            addUser.setToken(UUID.randomUUID().toString());
            addUser.setRealName(name);
            addUser.setLoginName(phone);
            addUser.setPhone(phone);
            addUser.setIsAuth("F");
            addUser.setWorkKey(NumberUtil.getRandomWorkKey(10));
            addUser.setIsSetTransPwd("F");
            addUser.setCompanyId(Long.valueOf(companyId));
            addUser.setRole("manage");
            addUser.setSoleCode(OrderNoUtil.genOrderNo("C" , 12));
            userService.addUser(addUser);
            //添加用户账户表
            User user1 = userService.findByPhone(phone);
            UserAccount userAccount = new UserAccount();
            userAccount.setUserId(user1.getId());
            userAccount.setSysPwd(MD5Util.MD5("000000"));
            userAccount.setCstatus("normal");
            userAccountService.addUserAccount(userAccount);
        }else{
            userService.updateCompanyIdAndRole(orgId,relationNo,phone,String.valueOf(companyId),"manage");
        }
    }

    /**
     * 删除公司数据
     */
    @RequestMapping(value = "/delete")
    public Result deleteNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id=request.getParameter("id");
            if(StringUtils.isBlank(id)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            List<Long> list=new ArrayList<Long>();
            String[] ids = id.split(",");
            for(String s : ids){
                List<User> users = userService.findCompanyId(Long.valueOf(s));
                if (users.size()>0){
                    return ResultData.unDataResult("fail","此公司有用户存在，不能操作！");
                }
                list.add(Long.valueOf(s));
            }
            operMapping.delCompanyOperAll(list);
            Integer delete = companyService.del(list);
            return delete > 0 ? ResultData.unDataResult("success", "删除成功") : Result.unDataResult("fail", "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }


    public static String ToFirstChar(String chinese) {
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

    /**
          * 汉字转为拼音
          * @param chinese
          * @return
          */
    public static String ToPinyin(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                }catch (BadHanyuPinyinOutputFormatCombination e){
                    e.printStackTrace();
                }
            }else{
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;

    }

}
