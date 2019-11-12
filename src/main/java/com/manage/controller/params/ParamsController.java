package com.manage.controller.params;

import com.manage.controller.base.BaseController;
import com.manage.model.Params;
import com.manage.service.IParamsService;
import com.manage.utils.RedisUtil;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/params")
@RestController
public class ParamsController extends BaseController {

    @Autowired
    private IParamsService paramsService;

    /**
     * 作用：获取所有业务参数的信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getAllParams")
    public Result getAllParams(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<Params> params = null;
        params = paramsService.findAllParams();
        return params!=null ? ResultData.dataResult("success","读取业务参数成功",params) : Result.unDataResult("fail","读取业务参数失败");
    }

    /**
     * 修改业务参数
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/updateParams")
    public Result updateParams(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
            Params params=new Params();
            params.setId(Long.valueOf(id));
            params.setParamText(paramText);
            params.setParamName(paramName);
            int result= paramsService.updateParams(params);
            if (result<0){
                return ResultData.unDataResult("fail","修改业务参数失败");
            }
            RedisUtil.setStr("params_"+params.getParamName(), params.getParamText(), 8, null);
            return ResultData.unDataResult("success","修改业务参数成功");
        }catch(Exception e){
            e.printStackTrace();
            return ResultData.unDataResult("fail","系统异常");
        }
    }

    /**
     * 修改业务参数
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/getRedis")
    public Result getRedis(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String key = request.getParameter("key");
        String dbNum = request.getParameter("dbNum");
        try{
            if (StringUtils.isBlank(key)){
                return ResultData.unDataResult("fail","参数不完整-key");
            }
            if (StringUtils.isBlank(dbNum)){
                return ResultData.unDataResult("fail","参数不完整-dbNum");
            }
            String redis= RedisUtil.getStrVal(key,Integer.valueOf(dbNum));
            return ResultData.dataResult("success","获取redis成功",redis);
        }catch(Exception e){
            e.printStackTrace();
            return ResultData.unDataResult("fail","系统异常");
        }
    }

}
