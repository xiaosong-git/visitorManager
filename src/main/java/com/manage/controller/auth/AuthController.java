package com.manage.controller.auth;

import com.alibaba.fastjson.JSONObject;
import com.manage.model.*;
import com.manage.service.IAuthService;
import com.manage.utils.VerifyUtil;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import com.xdream.kernel.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

@RequestMapping("/auth")
@RestController
public class AuthController extends BaseController {

    @Autowired
    private IAuthService authService;

    @RequestMapping(value="/getAuth")
    public Result getAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        Oper oper=(Oper)session.getAttribute("oper");

        if(oper==null){
            return Result.unDataResult("fail","读取权限失败");
        }
        Role role= null;
        role = authService.findRole(oper.getRole_id());

        return role!=null ? ResultData.dataResult("success","读取权限成功",role) : Result.unDataResult("fail","读取权限失败");
    }

    @RequestMapping(value="/getRoleAuth")
    public Result getRoleAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{

       String roleId = request.getParameter("roleId");

        if(roleId!=null && !VerifyUtil.isInteger(roleId)){
            return Result.unDataResult("fail","参数异常");
        }

        Role role= null;
        role = authService.findRole(Long.valueOf(roleId));

        return ResultData.dataResult("success","读取角色权限成功",role);
    }

   @RequestMapping(value="/addAuth")
    public Result addAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String menuId=request.getParameter("menuId");
        String buttonId=request.getParameter("buttonId");
        String roleId=request.getParameter("roleId");
        if(StringUtils.isBlank(menuId) && StringUtils.isBlank(buttonId) && StringUtils.isBlank(roleId)){
            return ResultData.unDataResult("fail","缺少参数");
        }
        Auth auth = new Auth();
        auth.setButton_id(Long.valueOf(buttonId));
        auth.setMenu_id(Long.valueOf(menuId));
        auth.setRole_id(Long.valueOf(roleId));
        int isInsert = authService.insert(auth);
        return isInsert>0 ? ResultData.unDataResult("success","插入单权限成功") : Result.unDataResult("fail","插入单权限失败");

    }

    @RequestMapping(value="/addAuthBatch")
    public Result addAuthBatch(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String addAuthList=request.getParameter("addAuthList");
        if(StringUtils.isBlank(addAuthList)){
            return ResultData.unDataResult("fail","缺少参数");
        }
        System.out.println(addAuthList);

        JSONObject jsonObject = JSONObject.parseObject(addAuthList);
        Long roleId = jsonObject.getLong("roleId");

        authService.deleteByRoleid(roleId);

        String menuData = jsonObject.getString("menuData");
        List<Menu> menus = JSONObject.parseArray(menuData, Menu.class);
        List<Auth> auths = new ArrayList<Auth>();
        if(menus==null){
            return Result.unDataResult("fail","菜单为空");
        }
        for (Menu menu:menus) {
            //System.out.println(menu.getId()+"---"+roleId);
            List<Button> buttons = menu.getButtons();
            if(buttons!=null && !buttons.isEmpty() && buttons.size()>0){
                for (Button button:buttons) {
                    Auth auth = new Auth();
                    auth.setRole_id(roleId);
                    auth.setMenu_id(menu.getId());
                    auth.setButton_id(button.getId());
                    auths.add(auth);
                }
            }else{
                Auth auth = new Auth();
                auth.setRole_id(roleId);
                auth.setMenu_id(menu.getId());
                auth.setButton_id(-10L);
                auths.add(auth);
            }
        }
        //取出角色id
        //JSONObject.
        //取出菜单集合
        System.out.println(auths!=null?auths.size():auths);
        int isInsert = 0;
        if(auths!=null && !auths.isEmpty() && auths.size()>0) {
            isInsert = authService.batchInsert(auths);
        }
        return ResultData.unDataResult("success","批量插入权限成功");
    }

}
