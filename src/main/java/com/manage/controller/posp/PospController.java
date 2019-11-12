package com.manage.controller.posp;

import com.manage.controller.base.BaseController;
import com.manage.model.Oper;
import com.manage.model.Posp;
import com.manage.service.IPospService;
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
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/posp")
@RestController
public class PospController extends BaseController {
    @Autowired
    private IPospService pospService;

    /**
     * 分页查询上位机
     */
    @RequestMapping(value = "/findByPage")
    public Result findByPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        String orgName = request.getParameter("orgName");
        String pospName = request.getParameter("pospName");
        Oper oper = (Oper)request.getSession().getAttribute("oper");
        String relation_no = oper.getOrg_relation_no();
        List<Posp> posps = pospService.findByPage(relation_no,orgName,pospName, page, pageSize);
        PageInfo<Posp> pospPageInfo = new PageInfo<Posp>(posps);
        return pospPageInfo != null ? ResultData.dataResult("success", "读取上位机信息成功", pospPageInfo) : Result.unDataResult("fail", "读取上位机信息失败");
    }

    /**
     * 插入、修改上位机数据
     */
    @RequestMapping(value = "/addOrExit")
    public Result addNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addOrEdit = request.getParameter("addOrEdit");
        String id = request.getParameter("id");
        String orgId = request.getParameter("orgId");
        String pospCode = request.getParameter("pospCode");
        String pospName = request.getParameter("pospName");
        String cstatus = request.getParameter("cstatus");

        if (StringUtils.isBlank(pospCode) && StringUtils.isBlank(pospName)
                && StringUtils.isBlank(cstatus) && StringUtils.isBlank(orgId)) {
            return ResultData.unDataResult("fail", "缺少参数");
        }
        Posp posp = new Posp();
        if (!StringUtils.isBlank(id)) {
            posp.setId(Long.parseLong(id));
        }
        posp.setPospCode(pospCode);
        posp.setPospName(pospName);
        posp.setCstatus(cstatus);
        if ("add".equals(addOrEdit)) {
            if (pospService.findByParam("pospCode",pospCode)!=null){
                return ResultData.unDataResult("fail","上位机编码已存在");
            }
            posp.setOrgId(Long.valueOf(orgId));
            int insert = pospService.add(posp);
            return insert > 0 ? ResultData.unDataResult("success", "插入成功") : Result.unDataResult("fail", "插入失败");
        } else if ("edit".equals(addOrEdit)) {
            int update = pospService.update(posp);
            return update > 0 ? ResultData.unDataResult("success", "修改成功") : Result.unDataResult("fail", "修改失败");
        } else {
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 删除上位机数据
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
            Integer delete = pospService.del(list);
            return delete > 0 ? ResultData.unDataResult("success", "删除成功") : Result.unDataResult("fail", "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 修改上位机状态
     */
    @RequestMapping(value = "/updateStatus")
    public Result updateNewsStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id=request.getParameter("id");
            String cstatus=request.getParameter("cstatus");
            if(StringUtils.isBlank(id)&&StringUtils.isBlank(cstatus)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            Posp posp = new Posp();
            posp.setId(Long.parseLong(id));
            posp.setCstatus(cstatus);
            Integer update = pospService.updateStatus(posp);
            return update > 0 ? ResultData.unDataResult("success", "修改状态成功") : Result.unDataResult("fail", "修改状态失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }
}
