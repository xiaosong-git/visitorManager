package com.manage.controller.login;

import com.google.code.kaptcha.Constants;
import com.manage.controller.base.BaseController;
import com.manage.model.Oper;
import com.manage.model.Org;
import com.manage.service.*;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by L on 2017/8/4.
 */
//@Controller
@RequestMapping("/login")
@RestController
public class LoginController extends BaseController {

    @Autowired
    private IOperService operService;

    @Autowired
    private IParamsService paramsService;

    @Autowired
    private IKeyService keyService;

    @Autowired
    private IOrgService orgService;

    @Autowired
    private IPasswordService passwordService;

    @RequestMapping(value="/login")
    public Result verificationUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        String code = request.getParameter("code");
        //System.out.println(name+"---"+pwd+"---"+code);
        if(StringUtils.isBlank(name) || StringUtils.isBlank(pwd) || StringUtils.isBlank(code)){
            return ResultData.unDataResult("fail","缺少参数");
        }
        HttpSession session=request.getSession();
        String verificationCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(verificationCode==null){
            return Result.unDataResult("fail","非法访问");
        }
        //判断密码输入次数是否超出限制，超出无法登录
        if(passwordService.isErrInputOutOfLimit(name)){
            String limitTime = paramsService.findByParamName("errorInputSyspwdWaitTime");
            return  Result.unDataResult("fail","由于您多次输入错误密码，为保证您的账户与资金安全，"+limitTime+"分钟内无法登录");
        }
        if(verificationCode.equals(code)){
            Oper oper = operService.findByLoginName(name);
            if (oper==null){
                return Result.unDataResult("fail","用户不存在");
            }else{
                Org org = orgService.findById(oper.getOrg_id());
                if(oper.getPwd().equals(pwd)){
                    //重置允许用户输入错误密码次数
                    passwordService.resetPwdInputNum(name);
                    UUID uuid = UUID.randomUUID();
                    oper.setToken(String.valueOf(uuid));
                    session.setAttribute("oper",oper);
                    session.setAttribute("org",org);

                    String imageServerUrl= paramsService.findByParamName("imageServerUrl");
                    oper.setImageServerUrl(imageServerUrl);

                    String workKey = keyService.findByStatusString("normal");
                    Map map=new HashMap();
                    map.put("workKey",workKey);
                    map.put("imageServerUrl", imageServerUrl);
                    map.put("auth","/auth/getAuth");
                    map.put("password","/login/changePassword");
                    return ResultData.extraDataResult("success","登录成功",oper,map);
                }else {
                    Long leftInputNum = passwordService.addErrInputNum(name);
                    return  Result.unDataResult("fail","密码错误:剩余" + leftInputNum + "次输入机会");
                }
            }
        }else {
            return Result.unDataResult("fail","验证码错误");
        }
    }

    @RequestMapping(value="/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();
        //System.out.println("登录中的："+session.getId());
        Enumeration enums = session.getAttributeNames();
        while(enums.hasMoreElements()){
            String name = enums.nextElement().toString();
            //System.out.println("sessionName:"+name);
            session.removeAttribute(name);
        }
        session.invalidate();
        return Result.unDataResult("success","注销成功");
    }

    @RequestMapping(value="/changePassword")
    public Result changePassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pwd = request.getParameter("pwd");
        String oldPwd = request.getParameter("oldPwd");

        if(StringUtils.isBlank(pwd) || StringUtils.isBlank(oldPwd)){
            return ResultData.unDataResult("fail","缺少参数");
        }
        HttpSession session=request.getSession();
        Oper operSession = (Oper)session.getAttribute("oper");

        Oper operVerify = operService.selectByPrimaryKey(operSession.getId());

        Integer isUpd=0;
        if(oldPwd.equals(operVerify.getPwd())){
            Oper oper = new Oper();
            oper.setId((operSession.getId()));
            oper.setPwd(pwd);
            isUpd=operService.updateByPrimaryKeySelective(oper);
        }
        return isUpd>0 ? Result.unDataResult("success","修改密码成功") : Result.unDataResult("fail","修改密码成功");
    }
}
