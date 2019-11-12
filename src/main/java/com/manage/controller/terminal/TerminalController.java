package com.manage.controller.terminal;

import com.manage.controller.base.BaseController;
import com.manage.model.Oper;
import com.manage.model.Terminal;
import com.manage.service.ITerminalService;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/terminal")
@RestController
public class TerminalController extends BaseController {
    @Autowired
    private ITerminalService terminalService;

    /**
     * 分页查询设备
     */
    @RequestMapping(value = "/findByPage")
    public Result findByPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        String orgName = request.getParameter("orgName");
        String terminalNo = request.getParameter("terminalNo");
        Oper oper = (Oper)request.getSession().getAttribute("oper");
        String relation_no = oper.getOrg_relation_no();
        List<Terminal> companys = terminalService.findByPage(relation_no,orgName,terminalNo, page, pageSize);
        PageInfo<Terminal> companyPageInfo = new PageInfo<Terminal>(companys);
        return companyPageInfo != null ? ResultData.dataResult("success", "读取公司信息成功", companyPageInfo) : Result.unDataResult("fail", "读取公司信息失败");
    }

    /**
     * 插入、修改设备数据
     */
    @RequestMapping(value = "/addOrExit")
    public Result addNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addOrEdit = request.getParameter("addOrEdit");
        String id = request.getParameter("id");
        String terminalNo = request.getParameter("terminalNo");
        String terminalCstatus = request.getParameter("terminalCstatus");
        String remark = request.getParameter("remark");
        String orgId = request.getParameter("orgId");

        if (StringUtils.isBlank(terminalNo) && StringUtils.isBlank(terminalCstatus)
                && StringUtils.isBlank(remark) && StringUtils.isBlank(orgId)) {
            return ResultData.unDataResult("fail", "缺少参数");
        }
        Terminal terminal = new Terminal();
        if (!StringUtils.isBlank(id)) {
            terminal.setId(Long.parseLong(id));
        }
        terminal.setTerminalNo(terminalNo);
        terminal.setTerminalCstatus(terminalCstatus);
        terminal.setRemark(remark);
        terminal.setOrgId(orgId);
        if ("add".equals(addOrEdit)) {
            int insert = terminalService.add(terminal);
            return insert > 0 ? ResultData.unDataResult("success", "插入成功") : Result.unDataResult("fail", "插入失败");
        } else if ("edit".equals(addOrEdit)) {
            int update = terminalService.update(terminal);
            return update > 0 ? ResultData.unDataResult("success", "修改成功") : Result.unDataResult("fail", "修改失败");
        } else {
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 删除设备数据
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
            Integer delete = terminalService.del(list);
            return delete > 0 ? ResultData.unDataResult("success", "删除成功") : Result.unDataResult("fail", "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 修改设备状态
     */
    @RequestMapping(value = "/updateStatus")
    public Result updateNewsStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id=request.getParameter("id");
            String newsStatus=request.getParameter("newsStatus");
            if(StringUtils.isBlank(id)&&StringUtils.isBlank(newsStatus)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            Terminal terminal = new Terminal();
            terminal.setId(Long.parseLong(id));
            terminal.setTerminalCstatus(newsStatus);
            Integer update = terminalService.updateStatus(terminal);
            return update > 0 ? ResultData.unDataResult("success", "修改状态成功") : Result.unDataResult("fail", "修改状态失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }
}
