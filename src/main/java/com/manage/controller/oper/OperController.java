package com.manage.controller.oper;

import com.manage.controller.base.BaseController;
import com.manage.model.Oper;
import com.manage.model.Org;
import com.manage.model.Role;
import com.manage.service.IOperService;
import com.manage.service.IRoleService;
import com.manage.service.ISystemParamsService;
import com.manage.utils.MD5Util;
import com.manage.utils.RefectionUtil;
import com.manage.utils.StringUtil;
import com.manage.utils.mapper.SqlUtil;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import com.xdream.kernel.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by L on 2017/8/12.
 */

@RequestMapping("/oper")
@RestController
public class OperController extends BaseController {

    @Autowired
    private IOperService operService;

    @Autowired
    ISystemParamsService systemParamService;

    @Autowired
    IRoleService roleService;

    /**
     * 获取角色（只能获取本级以及子孙级的角色 LZ）
     * @param request
     * @param response
     * @param page
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllOper")
    public Result getAllOper(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "2") Integer pageSize) throws Exception {

        List<Oper> opers = null;
        opers = operService.findByPage(page, pageSize);
        PageInfo<Oper> p = new PageInfo<Oper>(opers);
        return opers != null ? ResultData.dataResult("success", "读取操作员成功", p) : Result.unDataResult("fail", "读取操作员失败");
    }

    /**
     * 获取操作员（只能是本机构的）
     *
     * @param request
     * @param response
     * @param page
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPageOper")
    public Result getPageOper(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        HttpSession session = request.getSession();
        //包含查询条件的对象
        String oper_name = request.getParameter("oper_name");
        //登录的操作员
        Oper oper = (Oper) session.getAttribute("oper");
        List<Oper> opers = null;
        opers = operService.findByPageLeftOrg(oper_name,oper.getOrg_id(), page, pageSize);
        Map<Long, Object> map = new HashMap<Long, Object>();
        for (Oper tempOper : opers) {
            UUID uuid = UUID.randomUUID();
            tempOper.setToken(String.valueOf(uuid));
            map.put(tempOper.getId(), uuid);
        }
        session.setAttribute("rePwdVerify", map);
        PageInfo<Oper> operPageInfo = new PageInfo<Oper>(opers);
        return operPageInfo != null ? ResultData.dataResult("success", "分页读取操作员成功", operPageInfo) : Result.unDataResult("fail", "分页读取操作员失败");
    }

    @RequestMapping(value = "/addOper")
    public Result addOper(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Oper oper = RefectionUtil.getObjectFromRequest(request, Oper.class);
        Oper loginOper = (Oper) request.getSession().getAttribute("oper");
        if (StringUtils.isBlank(oper.getOper_name())
                || oper.getRole_id() == null
                || StringUtils.isBlank(oper.getLogin_name())) {
            return ResultData.unDataResult("fail", "缺少参数");
        }
        if (operService.findByLoginName(oper.getLogin_name()) != null) {
            return ResultData.unDataResult("fail", "操作员登录名重复，请重新设置!");
        }
        oper.setSstatus("normal");
        oper.setPwd(MD5Util.MD5("000000"));
        oper.setOrg_id(loginOper.getOrg_id());
        oper.setOrg_relation_no(loginOper.getOrg_relation_no());
        int isInsert = operService.insert(oper);

        return isInsert > 0 ? ResultData.unDataResult("success", "添加操作员成功") : Result.unDataResult("fail", "添加操作员失败");
    }

    @RequestMapping(value = "/delOperBatch")
    public Result delOperBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ids = request.getParameter("ids");
        if (StringUtils.isBlank(ids)) {
            return ResultData.unDataResult("fail", "缺少参数");
        }
        Oper operSession = (Oper) request.getSession().getAttribute("oper");
        String operId = String.valueOf(operSession.getId());
        List<String> id =  SqlUtil.getNoIdsList(operId,ids);
        if (id.size()<1){
            return ResultData.unDataResult("fail", "不能删除自己");
        }
        operService.batchDelete(id);
        return ResultData.unDataResult("success", "批量删除操作员成功");
    }

    @RequestMapping(value = "/updOper")
    public Result updOper(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Oper oper = RefectionUtil.getObjectFromRequest(request, Oper.class);
        if (oper.getId() == null
                || StringUtils.isBlank(oper.getOper_name())
                || oper.getRole_id() == null
                || StringUtils.isBlank(oper.getLogin_name())) {
            return ResultData.unDataResult("fail", "缺少参数");
        }
        //查询原登录名
        String newName = oper.getLogin_name();
        Oper oldOper = operService.findById(oper.getId());
        String oldName = oldOper.getLogin_name();
        if (!newName.equals(oldName)) {
            if (operService.findByLoginName(newName) != null) {
                return ResultData.unDataResult("fail", "操作员登录名重复，请重新设置!");
            }
        }
        Integer isUpd = operService.updateByPrimaryKeySelective(oper);
        return isUpd > 0 ? ResultData.unDataResult("success", "修改操作员成功") : Result.unDataResult("fail", "修改操作员失败");
    }

    @RequestMapping(value = "/resetPwd")
    public Result resetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Oper oper = RefectionUtil.getObjectFromRequest(request, Oper.class);
        if (oper.getId() == null) {
            return ResultData.unDataResult("fail", "缺少参数");
        }
        HttpSession session = request.getSession();
        Map<Long, Object> verifyMap = (HashMap) session.getAttribute("rePwdVerify");
        String uuid = String.valueOf(verifyMap.get(oper.getId()));
        if (uuid == null || !uuid.equals(oper.getToken())) {
            return ResultData.unDataResult("fail", "非法参数");
        }
        String resetPwd = systemParamService.findByParamName("default_pwd").getParam_text();

        resetPwd = MD5Util.MD5(resetPwd);
        oper.setPwd(resetPwd);
        Integer isReset = operService.updateOperPwd(oper);
        return isReset > 0 ? ResultData.unDataResult("success", "重置密码成功") : Result.unDataResult("fail", "重置密码失败");
    }

    /**
     * 获取业务经理（只能获取本级的业务经理） LZ
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSalesmanOper")
    public Result getSalesmanOper(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Oper operSession = (Oper) request.getSession().getAttribute("oper");
        List<Oper> opers = null;
        opers = operService.findSalesmanByOrgId(Long.valueOf(operSession.getOrg_id()));

        return opers != null ? ResultData.dataResult("success", "读取业务经理成功", opers) : Result.unDataResult("fail", "读取业务经理失败");
    }
/********===============================新=====================**********/
    /***
     * 新增客服管理
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/saveOper")
    public Result saveOper(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Org orgSession = (Org) request.getSession().getAttribute("org");
        Long orgId = orgSession.getId();
        String org_relation_no = orgSession.getRelation_no();
        String role_id = request.getParameter("role_id");
        String oper_code = request.getParameter("oper_code");
        String oper_name = request.getParameter("oper_name");
        String login_name = request.getParameter("login_name");

        if (StringUtils.isBlank(role_id)) {
            return ResultData.unDataResult("fail", "必须选择角色");
        }
        if (StringUtils.isBlank(oper_name)) {
            return ResultData.unDataResult("fail", "名称不能为空");
        }
        if (StringUtils.isBlank(login_name)) {
            return ResultData.unDataResult("fail", "登录名不能为空");
        }
        if (operService.findByLoginName(login_name) != null) {
            return ResultData.unDataResult("fail", "登录名不能重复");
        } else if (!StringUtil.isNumAndLetterGroup(login_name)) {
            return ResultData.unDataResult("fail", "登录名只能是数字和字母的组合");
        }

        if (oper_code != null && !oper_code.trim().equals("")) {
            if (operService.findByCode(oper_code) != null) {
                return ResultData.unDataResult("fail", "编号不能重复");
            }
        }

        try {
            Oper oper = new Oper();
            oper.setOper_code(oper_code);
            oper.setOper_name(oper_name);
            oper.setLogin_name(login_name);
            oper.setOrg_id(orgId);
            oper.setRole_id(Long.parseLong(role_id));
            oper.setOrg_relation_no(org_relation_no);
            MD5 md5 = new MD5();
            String default_pwd = systemParamService.findByParamName("default_pwd").getParam_text();
            String pwd = md5.calcMD5(default_pwd);
            oper.setPwd(pwd);
            oper.setSstatus("0");

            operService.txInsOperAndorgNotice(oper);
            return ResultData.unDataResult("success", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "操作失败");
        }
    }


    @RequestMapping(value = "/updateOper")
    public Result updateOper(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String oper_code = request.getParameter("oper_code");
        String oper_name = request.getParameter("oper_name");
        String login_name = request.getParameter("login_name");
        String role_id = request.getParameter("role_id");

        if (StringUtils.isBlank(role_id)) {
            return ResultData.unDataResult("fail", "必须选择角色");
        }
        if (StringUtils.isBlank(id)) {
            return ResultData.unDataResult("fail", "参数不完整-id");
        }

        if (StringUtils.isBlank(oper_name)) {
            return ResultData.unDataResult("fail", "名称不能为空");
        }
        if (StringUtils.isBlank(login_name)) {
            return ResultData.unDataResult("fail", "登录名不能为空");
        } else if (!StringUtil.isNumAndLetterGroup(login_name)) {
            return ResultData.unDataResult("fail", "登录名只能是数字和字母的组合");
        }
        try {
            Oper oper = operService.findById(Long.parseLong(id));
            if (oper_code != null && !oper_code.trim().equals("")) {
                if (!StringUtils.isBlank(oper.getOper_code())) {
                    if (!oper_code.trim().equals(oper.getOper_code())) {
                        if (operService.findByCode(oper_code) != null) {
                            return ResultData.unDataResult("fail", "编号不能重复");
                        }
                    }
                }
            }
            if (!StringUtils.isBlank(oper.getLogin_name())) {
                if (!login_name.trim().equals(oper.getLogin_name())) {
                    if (operService.findByLoginName(login_name) != null) {
                        return ResultData.unDataResult("fail", "登录名不能重复");
                    }
                }
            }
            Oper insert = new Oper();
            insert.setId(Long.parseLong(id));
            insert.setOper_code(oper_code);
            insert.setOper_name(oper_name);
            insert.setLogin_name(login_name);
            insert.setRole_id(Long.parseLong(role_id));
            operService.updateById(insert);
            return ResultData.unDataResult("success", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "操作失败");
        }
    }
    /**
     * 根据机构ID找出所有的客服
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/operRoleList")
    public Result operRoleList(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        try {
            String orgId = request.getParameter("orgId");
            Oper o = (Oper) request.getSession().getAttribute("oper");
            long roleId = o.getRole_id();
            Role role = roleService.findById(roleId);
            String roleRelation = role.getRole_relation_no();

            List<Oper> opers = operService.findByOrgIdAndRoleId(Long.valueOf(orgId), roleRelation, page, pageSize);
            PageInfo<Oper> operPageInfo = new PageInfo<Oper>(opers);
            return operPageInfo != null ? ResultData.dataResult("success", "读取所有的客服成功", operPageInfo) : Result.unDataResult("fail", "读取所有的客服失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 重置密码
     */
    @RequestMapping(value = "/updatePwd")
    public Result updatePwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String old_pwd = request.getParameter("old_pwd");
        String new_pwd = request.getParameter("new_pwd");
        String new_pwd_confirm = request.getParameter("new_pwd_confirm");

        Oper operSession = (Oper) request.getSession().getAttribute("oper");

        if (StringUtils.isBlank(old_pwd)) {
            return ResultData.unDataResult("fail", "原密码不能为空");
        }
        if (StringUtils.isBlank(new_pwd)) {
            return ResultData.unDataResult("fail", "新密码不能为空");
        }
        if (!new_pwd.trim().equals(new_pwd_confirm)) {
            return ResultData.unDataResult("fail", "两次密码不一致");
        }
        try {
            //判断愿意密码
            Oper oper = operService.findById(operSession.getId());
            if (oper == null) {
                return ResultData.unDataResult("fail", "无此用户信息");
            }

            if (!old_pwd.trim().equals(oper.getPwd())) {
                return ResultData.unDataResult("fail", "原密码错误");
            }
            //修改
            Oper uo = new Oper();
            uo.setId(oper.getId());
            uo.setPwd(new_pwd);
            operService.updateById(uo);
            return ResultData.unDataResult("success", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "操作失败");
        }
    }

    @RequestMapping(value = "/resetPassWord")
    public Result resetPassWord(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id = request.getParameter("id");

        if (StringUtils.isBlank(id)) {
            return Result.unDataResult("fail", "参数不完整");
        }
        try {
            Oper uo = new Oper();
            uo.setId(Long.parseLong(id));
            MD5 md5 = new MD5();
            String default_pwd = systemParamService.findByParamName("default_pwd").getParam_text();
            String pwd = md5.calcMD5(default_pwd);
            uo.setPwd(pwd);

            Integer isReset = operService.updateOperPwd(uo);
            return isReset > 0 ? ResultData.unDataResult("success", "重置密码成功") : Result.unDataResult("fail", "重置密码失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.unDataResult("fail", "重置密码异常");
        }
    }

    /**
     * 获取操作员（只能是本机构的及其以下）
     *
     * @param request
     * @param response
     * @param page
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOrgPageOper")
    public Result getOrgPageOper(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        HttpSession session = request.getSession();
        //包含查询条件的对象
        Oper searchOper = RefectionUtil.getObjectFromRequest(request, Oper.class);
    System.out.println(searchOper.getOrg_name());
        //登录的操作员
        Oper oper = (Oper) request.getSession().getAttribute("oper");

        List<Oper> opers = null;
        opers = operService.getOrgPageOper(searchOper.getOper_name(), searchOper.getOrg_name(), oper.getOrg_relation_no(), searchOper.getOrgType(), oper.getOrg_id(), page, pageSize);
        Map<Long, Object> map = new HashMap<Long, Object>();
        for (Oper tempOper : opers) {
            UUID uuid = UUID.randomUUID();
            tempOper.setToken(String.valueOf(uuid));
            map.put(tempOper.getId(), uuid);
        }
        session.setAttribute("rePwdVerify", map);
        PageInfo<Oper> operPageInfo = new PageInfo<Oper>(opers);
        return operPageInfo != null ? ResultData.dataResult("success", "分页读取操作员成功", operPageInfo) : Result.unDataResult("fail", "分页读取操作员失败");
    }

    @RequestMapping(value = "/agentOperList")
    public Result agentOperList(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                                @RequestParam(required = false, defaultValue = "10") Integer pageSize)throws Exception {
        HttpSession session = request.getSession();
        try {

            //获取查询条件
            String oper_name = request.getParameter("oper_name");//操作员名称
            String org_name = request.getParameter("org_name");//机构名称
            String orgType = request.getParameter("orgType");//机构名称

            String show = request.getParameter("show");

            Map<String, Object> paramMap = new HashMap<String, Object>();
            // 权限
            Oper o = (Oper) request.getSession().getAttribute("oper");

            String operRelationNo = o.getOrg_relation_no();

            long roleId = o.getRole_id();

            Role role = roleService.findById(roleId);

            String roleRelation = role.getRole_relation_no();

            String orgId =o.getOrg_id().toString();

            List<Oper> pager = null;
            if (StringUtils.isBlank(show)) {
                pager = operService.agentOperList(oper_name, org_name, operRelationNo, roleRelation, orgType, orgId, page, pageSize);
            }
            Map<Long, Object> map1 = new HashMap<Long, Object>();
            for (Oper tempOper : pager) {
                UUID uuid = UUID.randomUUID();
                tempOper.setToken(String.valueOf(uuid));
                map1.put(tempOper.getId(), uuid);
            }
            session.setAttribute("rePwdVerify", map1);
            //设置搜索时返回界面的查询条件
            Oper oper = new Oper();
            oper.setOper_name(oper_name);
            oper.setOrg_name(org_name);

            PageInfo<Oper> map = new PageInfo<Oper>(pager);
        //    map.put("list", pager);
            //  map.put("oper", oper);
            return map != null ? ResultData.dataResult("success", "读取操作员成功", map) : Result.unDataResult("fail", "读取操作员失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.unDataResult("fail", "读取操作员异常");
        }
    }


}
