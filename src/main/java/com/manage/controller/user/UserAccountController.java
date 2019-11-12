package com.manage.controller.user;

import com.manage.controller.base.BaseController;
import com.manage.model.Oper;
import com.manage.model.User;
import com.manage.service.IParamsService;
import com.manage.service.IUserAccountService;
import com.manage.service.IUserService;
import com.manage.utils.MD5Util;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/userAccount")
@RestController
public class UserAccountController extends BaseController {

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IParamsService paramsService;

    @Autowired
    private IUserService userService;

    /**
     * 修改用户账户冻结状态
     */
    @RequestMapping(value = "/updateCstatus")
    public Result updateCstatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id = request.getParameter("id");
            String handleCause = request.getParameter("handleCause");
            String cstatus = request.getParameter("cstatus");
            if(StringUtils.isBlank(id) && StringUtils.isBlank(handleCause) && StringUtils.isBlank(cstatus)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            Oper operSession = (Oper)request.getSession().getAttribute("oper");
            Integer update = userAccountService.updateCstatus(Long.valueOf(id),cstatus,handleCause,operSession.getId());
            return update > 0 ? ResultData.unDataResult("success", "修改账户冻结状态成功") : Result.unDataResult("fail", "修改账户冻结状态失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 重置用户登录密码
     */
    @RequestMapping(value = "/updateLoginPwd")
    public Result updateLoginPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id = request.getParameter("id");
            if(StringUtils.isBlank(id)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            String newLoginPwd = paramsService.findByParamName("defaultUserLoginPwd");
            newLoginPwd = MD5Util.MD5(newLoginPwd);
            Integer update = userAccountService.updateLoginPwd(Long.valueOf(id),newLoginPwd);
            return update > 0 ? ResultData.unDataResult("success", "重置用户登录密码成功") : Result.unDataResult("fail", "重置用户登录密码失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 重置支付密码
     */
    @RequestMapping(value = "/updatePayPwd")
    public Result updatePayPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id = request.getParameter("id");
            if(StringUtils.isBlank(id)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            String newPayPwd = paramsService.findByParamName("defaultUserPayPwd");
            newPayPwd = MD5Util.MD5(newPayPwd);
            Integer update = userAccountService.updatePayPwd(Long.valueOf(id),newPayPwd);
            return update > 0 ? ResultData.unDataResult("success", "重置支付密码成功") : Result.unDataResult("fail", "重置支付密码失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 修改登录账号
     */
    @RequestMapping(value = "/updatePhone")
    public Result updatePhone(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id = request.getParameter("id");
            String phone=request.getParameter("phone");
            String reason=request.getParameter("reason");
            if(StringUtils.isBlank(id) && StringUtils.isBlank(phone) && StringUtils.isBlank(reason)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            Oper operSession = (Oper)request.getSession().getAttribute("oper");
            User user=userService.findById(Long.valueOf(id));
            if (userService.findByPhone(phone)!=null){
                ResultData.unDataResult("fail", "登录账号已经存在");
            }
            int insert = userService.updatePhone(Long.valueOf(id),phone);
            return insert > 0 ? ResultData.unDataResult("success", "修改登录账号成功") : Result.unDataResult("fail", "修改登录账号失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }
}
