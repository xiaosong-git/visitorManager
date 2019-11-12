package com.manage.controller.reviewManage;

import com.manage.controller.base.BaseController;
import com.manage.dao.TblCompanyUserMapper;
import com.manage.dao.TblUserMapper;
import com.manage.model.Oper;
import com.manage.model.TblCompanyUser;
import com.manage.model.TblUser;
import com.manage.service.CompanyUserService;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/reviewManage")
@RestController
public class ReViewManageController extends BaseController {

    @Autowired
    private CompanyUserService companyUserService;

    @Autowired
    private TblCompanyUserMapper userMapper;

    @Autowired
    private TblUserMapper tblUserMapper;

    @RequestMapping(value = "/getCompanyUser")
    public Result findByPage(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(required = true, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {

        HttpSession session = request.getSession();
        Oper oper = (Oper)session.getAttribute("oper");

        String createDate = request.getParameter("createDate");
        String userName = request.getParameter("userName");
        String status = request.getParameter("status");
        String companyName = request.getParameter("companyName");
        List<TblCompanyUser> companys = companyUserService.findByPage(createDate,userName,status,companyName,oper.getOrg_id(), page, pageSize);
        PageInfo<TblCompanyUser> companyPageInfo = new PageInfo<TblCompanyUser>(companys);
        return companyPageInfo != null ? ResultData.dataResult("success", "读取公司员工信息成功", companyPageInfo) : Result.unDataResult("fail", "读取公司员工信息失败");

    }

    @RequestMapping(value = "/comfrimUser")
    public Result comfrimUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id = request.getParameter("id");
            String status = request.getParameter("status");
            String newsDatail = request.getParameter("newsDatail");


            /**
             * user1.setCompanyId(companyId);
             * user1.setRole(roleType);
             */

            if (id.length()==0 ||id==null || status.length()==0 || status==null) {
                return ResultData.unDataResult("fail", "缺少参数");
            }
            List<TblCompanyUser> list = new ArrayList<>();
            String[] ids = id.split(",");
            for (String s : ids) {
                TblCompanyUser companyUser=new TblCompanyUser();
                companyUser.setId(Long.parseLong(s));
                companyUser.setStatus(status);
                companyUser.setApplyfailansaesn(newsDatail.equals("undefined")?"":newsDatail);
                list.add(companyUser);

                if("applySuc".equals(status)){
                    TblCompanyUser tblCompanyUser = userMapper.selectByPrimaryKey(Long.parseLong(s));
                    TblUser tblUser=new TblUser();
                    tblUser.setCompanyid(tblCompanyUser.getCompanyid());
                    tblUser.setRole(tblCompanyUser.getRoletype());
                    tblUser.setId(tblCompanyUser.getUserid());
                    tblUserMapper.updateByPrimaryKeySelective(tblUser);
                }

            }
            int i = userMapper.updateCheckStatusByPersonIds(list);
            return i > 0 ? ResultData.unDataResult("success", "审核成功") : Result.unDataResult("fail", "审核失败");

        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }

    }

}
