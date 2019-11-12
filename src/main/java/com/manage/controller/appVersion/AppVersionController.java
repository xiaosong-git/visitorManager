package com.manage.controller.appVersion;

import com.manage.controller.base.BaseController;
import com.manage.model.AppVersion;
import com.manage.service.IAppVersionService;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/appVersion")
@RestController
public class AppVersionController extends BaseController {
    @Autowired
    private IAppVersionService appVersionService;

    /**
     * 分页app版本
     */
    @RequestMapping(value = "/findByPage")
    public Result findByPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        String appType = request.getParameter("appType");
        String channel =  request.getParameter("channel");
        String versionName = request.getParameter("versionName");
        String versionNum = request.getParameter("versionNum");
        String startDate =  request.getParameter("startDate");
        String endDate =  request.getParameter("endDate");
        List<AppVersion> appVersions = appVersionService.findByPage(appType, channel, versionName, versionNum, startDate, endDate, page, pageSize);
        PageInfo<AppVersion> appVersionPageInfo = new PageInfo<AppVersion>(appVersions);
        return appVersionPageInfo != null ? ResultData.dataResult("success", "读取app版本信息成功", appVersionPageInfo) : Result.unDataResult("fail", "读取app版本信息失败");
    }

    /**
     * 插入、修改app版本
     */
    @RequestMapping(value = "/addOrExit")
    public Result addNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addOrEdit = request.getParameter("addOrEdit");
        String id =  request.getParameter("id");
        String appType =  request.getParameter("appType");
        String channel =  request.getParameter("channel");
        String versionName =  request.getParameter("versionName");
        String versionNum =  request.getParameter("versionNum");
        String updateUrl =  request.getParameter("updateUrl");
        String memo = request.getParameter("memo");
        String isImmediatelyUpdate = request.getParameter("isImmediatelyUpdate");

        if(StringUtils.isBlank(appType) || StringUtils.isBlank(channel)|| StringUtils.isBlank(versionName) || StringUtils.isBlank(versionNum)|| StringUtils.isBlank(updateUrl)|| StringUtils.isBlank(isImmediatelyUpdate)){
            return ResultData.unDataResult("fail", "缺少参数");
        }
        AppVersion appVersion = new AppVersion();
        if(!StringUtils.isBlank(id)){
            appVersion.setId(Long.parseLong(id));
        }
        appVersion.setAppType(appType);
        appVersion.setChannel(channel);
        appVersion.setIsImmediatelyUpdate(isImmediatelyUpdate);
        appVersion.setMemo(memo);
        appVersion.setUpdateUrl(updateUrl);
        appVersion.setVersionName(versionName);
        appVersion.setVersionNum(versionNum);
        Date date = new Date();
        appVersion.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
        if ("add".equals(addOrEdit)) {
            int insert = appVersionService.add(appVersion);
            return insert > 0 ? ResultData.unDataResult("success", "插入成功") : Result.unDataResult("fail", "插入失败");
        } else if ("edit".equals(addOrEdit)) {
            int update = appVersionService.update(appVersion);
            return update > 0 ? ResultData.unDataResult("success", "修改成功") : Result.unDataResult("fail", "修改失败");
        } else {
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 删除数据
     */
    @RequestMapping(value = "/delete")
    public Result deleteNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id=request.getParameter("id");
            if(StringUtils.isBlank(id)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            List<Long> list=new ArrayList<Long>();
            String[] ids = id.split(",");
            for(String s : ids){
                list.add(Long.valueOf(s));
            }
            Integer delete = appVersionService.del(list);
            return delete > 0 ? ResultData.unDataResult("success", "删除成功") : Result.unDataResult("fail", "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }
}
