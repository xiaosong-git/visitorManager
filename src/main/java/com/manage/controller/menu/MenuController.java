package com.manage.controller.menu;

import com.manage.controller.base.BaseController;
import com.manage.model.Menu;
import com.manage.service.IMenuService;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by L on 2017/8/9.
 */

@RequestMapping("/menu")
@RestController
public class MenuController extends BaseController{

    @Autowired
    private IMenuService menuService;

    @RequestMapping(value="/getAllMenu")
    public Result getAllMenu(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<Menu> menus = null;
        menus = menuService.findAll();
        return menus!=null ? ResultData.dataResult("success","读取角色成功",menus) : Result.unDataResult("fail","读取角色失败");
    }
}
