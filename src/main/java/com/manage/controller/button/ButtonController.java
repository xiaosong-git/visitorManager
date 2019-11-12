package com.manage.controller.button;

import com.manage.controller.base.BaseController;
import com.manage.model.Button;
import com.manage.service.IButtonService;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by L on 2017/8/9.
 */

@RequestMapping("/button")
@RestController
public class ButtonController extends BaseController {

    @Autowired
    private IButtonService buttonService;

    @RequestMapping(value="/getMenuButton")
    public Result getMenuButton(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String menuId=request.getParameter("menuId");
        if(StringUtils.isBlank(menuId)){
            //System.out.println(menuId+"里面的");
            return ResultData.dataResult("fail","缺少参数",menuId);
        }
        List<Button> buttons = null;
        buttons = buttonService.findByMenuId(Long.valueOf(menuId));

        return buttons!=null ? ResultData.dataResult("success","读取按钮成功",buttons) : Result.unDataResult("fail","读取按钮失败");
    }
}
