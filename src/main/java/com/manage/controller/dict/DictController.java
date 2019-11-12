package com.manage.controller.dict;

import com.manage.controller.base.BaseController;
import com.manage.model.Dict;
import com.manage.service.IDictService;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/dict")
@RestController
public class DictController extends BaseController {
    @Autowired
    private IDictService dictService;

    /**
     * 作用：获取所有大类参数的信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getAllDict")
    public Result getAllDict(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<Dict> dicts = null;
        dicts = dictService.findAllDict();
        return dicts!=null ? ResultData.dataResult("success","读取大类参数成功",dicts) : Result.unDataResult("fail","读取大类参数失败");
    }
}
