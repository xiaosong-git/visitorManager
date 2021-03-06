package com.manage.controller.auth;

import com.alibaba.fastjson.JSONObject;
import com.manage.controller.base.BaseController;
import com.manage.model.*;
import com.manage.service.*;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 2017/8/9.
 */

@RequestMapping("/authRange")
@RestController
public class AuthRangeController extends BaseController {

    @Autowired
    private IAuthRangeService authRangeService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IButtonService buttonService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAuthService authService;
    /**
     * 作用：获取该角色的所有菜单
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/newGetAllMenu")
    public Result newGetAllMenu(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Oper oper=(Oper)request.getSession().getAttribute("oper");
        String roleId=String.valueOf(oper.getRole_id());
        if(StringUtils.isBlank(roleId)){
            return ResultData.unDataResult("fail","缺少参数");
        }
        List<Menu> menus=null;
        List<AuthRange> authAgentRanges=null;
        if (roleId.equals("1")){
            menus= menuService.findAll();
            return menus!=null ? ResultData.dataResult("success","读取菜单成功",menus) : Result.unDataResult("fail","读取菜单失败");
        }else{
            authAgentRanges = authRangeService.findAllByRoleId(roleId);
            return authAgentRanges!=null ? ResultData.dataResult("success","读取菜单成功",authAgentRanges) : Result.unDataResult("fail","读取菜单失败");
        }
    }

    /**
     * 作用：获取该角色的所有按钮
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/newGetMenuButton")
    public Result newGetMenuButton(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Oper oper=(Oper)request.getSession().getAttribute("oper");
        String roleId=String.valueOf(oper.getRole_id());
        String menuId=request.getParameter("menuId");
        if(StringUtils.isBlank(roleId) && StringUtils.isBlank(menuId)){
            return ResultData.unDataResult("fail","缺少参数");
        }
        if (roleId.equals("1")){
            List<Button> buttons = buttonService.findByMenuId(Long.valueOf(menuId));
            return buttons!=null ? ResultData.dataResult("success","读取按钮成功",buttons) : Result.unDataResult("fail","读取按钮失败");
        }else{
            List<AuthRange> authAgentRanges = authRangeService.findAllButton(roleId,menuId);
            return authAgentRanges!=null ? ResultData.dataResult("success","读取按钮成功",authAgentRanges) : Result.unDataResult("fail","读取按钮失败");
        }
    }

    @RequestMapping(value="/newAddAuthBatch")
    public Result newAddAuthBatch(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String addAuthList=request.getParameter("addAuthList");
        JSONObject jsonObject = JSONObject.parseObject(addAuthList);
        System.out.println(jsonObject.toString());
        if(StringUtils.isBlank(addAuthList)){
            return ResultData.unDataResult("fail","缺少参数");
        }
        String roleId = String.valueOf(jsonObject.getLong("roleId"));
        //判断修改的是不是他下级
        Oper oper=(Oper)request.getSession().getAttribute("oper");
        Role role = roleService.findById(oper.getRole_id());
        String org_relation_no = role.getRole_relation_no();//本级
        List<Role> roles = roleService.findByAllRelationNo(org_relation_no);

        boolean existence = false;
        for (int i=0;i<roles.size();i++){
            if (String.valueOf(roles.get(i).getId()).equals(roleId)){
                existence=true;
                break;
            }
        }
        if (!existence){
            System.out.println("existence:"+existence);
            return ResultData.unDataResult("fail","没有权限修改"+existence);
        }
        String menuData = jsonObject.getString("menuData");
        List<Menu> menus = JSONObject.parseArray(menuData, Menu.class);
        List<Auth> auths = new ArrayList<Auth>();
        List<AuthRange> authAgentRanges = new ArrayList<AuthRange>();
        if(menus==null){
            return Result.unDataResult("fail","菜单为空");
        }
        Long button_id;
        Long menu_id;
        List<Button> buttons;
        if (oper.getRole_id()!=1){
            for (Menu menu:menus) {
                buttons = menu.getButtons();
                menu_id = menu.getId();
                System.out.println("menu_id"+menu_id);
                if(buttons!=null && !buttons.isEmpty() && buttons.size()>0){
                    for (Button button:buttons) {
                        button_id = button.getId();
                        if (button_id==-10){
                            continue;
                        }
                        System.out.println("button_id"+button_id);
                        if (authRangeService.findByAuthAgentRange(menu_id,button_id,oper.getRole_id())==null){
                            return Result.unDataResult("fail","没有权限修改");
                        }
                    }
                }
            }
        }
        if (Long.valueOf(roleId)==oper.getRole_id() && oper.getRole_id()!=1){
            authService.deleteByRoleid(Long.valueOf(roleId));
            for (Menu menu:menus) {
                buttons = menu.getButtons();
                if(buttons!=null && !buttons.isEmpty() && buttons.size()>0){
                    for (Button button:buttons) {
                        //加入auths
                        Auth auth = new Auth();
                        auth.setRole_id(Long.valueOf(roleId));
                        auth.setMenu_id(menu.getId());
                        auth.setButton_id(button.getId());
                        auths.add(auth);
                    }
                }else{
                    Auth auth = new Auth();
                    auth.setRole_id(Long.valueOf(roleId));
                    auth.setMenu_id(menu.getId());
                    auth.setButton_id(-10L);
                    auths.add(auth);
                }
            }
        }else{
            authRangeService.deleteByRoleId(Long.valueOf(roleId));
            authService.deleteByRoleid(Long.valueOf(roleId));

            for (Menu menu:menus) {
                buttons = menu.getButtons();
                menu_id = menu.getId();
                if(buttons!=null && !buttons.isEmpty() && buttons.size()>0){
                    for (Button button:buttons) {
                        button_id = button.getId();
                        //加入auths
                        Auth auth = new Auth();
                        auth.setRole_id(Long.valueOf(roleId));
                        auth.setMenu_id(menu.getId());
                        auth.setButton_id(button.getId());
                        auths.add(auth);
                        //加入authAgentRanges
                        AuthRange authAgentRange = new AuthRange();
                        authAgentRange.setRole_id(Long.valueOf(roleId));
                        authAgentRange.setMenu_id(menu_id);
                        authAgentRange.setButton_id(button_id);
                        authAgentRanges.add(authAgentRange);
                    }
                }else{
                    Auth auth = new Auth();
                    auth.setRole_id(Long.valueOf(roleId));
                    auth.setMenu_id(menu.getId());
                    auth.setButton_id(-10L);
                    auths.add(auth);
                    AuthRange authAgentRange = new AuthRange();
                    authAgentRange.setRole_id(Long.valueOf(roleId));
                    authAgentRange.setMenu_id(menu_id);
                    authAgentRange.setButton_id(-10L);
                    authAgentRanges.add(authAgentRange);
                }
            }
        }
        //取出角色id
        //JSONObject.
        //取出菜单集合
        int addAuths = 0;
        int addAuthAgentRanges = 0;
        if(auths!=null && !auths.isEmpty() && auths.size()>0) {
            addAuths = authService.batchInsert(auths);
        }
        if(authAgentRanges!=null && !authAgentRanges.isEmpty() && authAgentRanges.size()>0) {
            addAuthAgentRanges = authRangeService.batchInsert(authAgentRanges);
        }
        return ResultData.unDataResult("success","批量插入权限成功");
    }
}
