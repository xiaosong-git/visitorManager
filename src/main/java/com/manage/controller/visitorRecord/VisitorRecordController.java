package com.manage.controller.visitorRecord;

import com.manage.controller.base.BaseController;
import com.manage.model.Oper;
import com.manage.model.VisitorRecord;
import com.manage.service.IVisitorRecordService;
import com.manage.utils.ExportExcel;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/visitorRecord")
@RestController
public class VisitorRecordController extends BaseController {
    @Autowired
    private IVisitorRecordService visitorRecordService;

    @Value("${imgPath}")
    private String imgPath;

    /**
     * 分页查询访客记录
     */
    @RequestMapping(value = "/findByPage")
    public Result findByPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String userName = request.getParameter("userName");
        String visitorName = request.getParameter("visitorName");

        HttpSession session = request.getSession();
        Oper oper = (Oper)session.getAttribute("oper");

        List<VisitorRecord> companys = visitorRecordService.findByPage
                (oper.getOper_code(),startDate,endDate,userName,visitorName,page, pageSize);
        PageInfo<VisitorRecord> companyPageInfo = new PageInfo<VisitorRecord>(companys);
        return companyPageInfo != null ? ResultData.dataResult("success", "读取公司信息成功", companyPageInfo) : Result.unDataResult("fail", "读取公司信息失败");
    }

    @RequestMapping(value = "/daochuVisitor")
    public Result daochuVisitor(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String userName = request.getParameter("userName");
            String visitorName = request.getParameter("visitorName");
            HttpSession session = request.getSession();
            Oper oper = (Oper)session.getAttribute("oper");
            List<VisitorRecord> records = visitorRecordService.findByPage2(
                    oper.getOper_code(),
                    startDate, endDate, userName, visitorName);
            String export = new ExportExcel().export(records);
            if(export!=null && export.length()>0 && export !=""){
                String newUrl=imgPath+export;
                return ResultData.dataResult("success","导出成功！",newUrl);
            }else {
                return Result.unDataResult("fail", "导出失败，文件名称错误！");
            }
        }catch (Exception e){
            return Result.unDataResult("fail", "读取公司信息失败");
        }

    }
}
