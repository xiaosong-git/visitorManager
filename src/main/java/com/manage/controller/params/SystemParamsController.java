package com.manage.controller.params;

import com.manage.controller.base.BaseController;
import com.manage.model.SystemParam;
import com.manage.service.ISystemParamsService;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/systemParam")
@RestController
public class SystemParamsController extends BaseController {

    @Autowired
    private ISystemParamsService systemParamsService;

    /**
     * 作用：获取所有系统参数的信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getAllSystemParam")
    public Result getAllSystemParam(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<SystemParam> systemParams = null;
        systemParams = systemParamsService.findAllSystemParam();
        return systemParams!=null ? ResultData.dataResult("success","读取系统参数成功",systemParams) : Result.unDataResult("fail","读取系统参数失败");
    }

    /**
     * 修改系统参数
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/updateSystemParam")
    public Result updateSystemParam(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        String paramText = request.getParameter("paramText");
        String paramName = request.getParameter("paramName");
        try{
            if (StringUtils.isBlank(id)){
                return ResultData.unDataResult("fail","参数不完整-ID");
            }
            if (StringUtils.isBlank(paramText)){
                return ResultData.unDataResult("fail","参数不完整-值");
            }
            if (StringUtils.isBlank(paramName)){
                return ResultData.unDataResult("fail","参数不完整-名");
            }
            SystemParam systemParam=new SystemParam();
            systemParam.setId(Long.valueOf(id));
            systemParam.setParam_text(paramText);
            systemParam.setParam_name(paramName);
            int result= systemParamsService.updateSystemParam(systemParam);
            if (result<0){
                return ResultData.unDataResult("fail","修改系统参数失败");
            }
            return ResultData.unDataResult("success","修改系统参数成功");
        }catch(Exception e){
            e.printStackTrace();
            return ResultData.unDataResult("fail","系统异常");
        }
    }
}
