package com.manage.controller.notice;

import com.manage.controller.base.BaseController;
import com.manage.model.Notice;
import com.manage.model.Oper;
import com.manage.service.INoticeService;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import com.xdream.kernel.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/9.
 */
@RequestMapping("/notice")
@RestController
public class NoticeController extends BaseController {

    @Autowired
    INoticeService noticeService;

    /**
     * 20180609
     * 公告
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public Result completeList(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        try {
            String noticeTitle = request.getParameter("noticeTitle");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            List<Notice> notices = noticeService.findByPager(startDate, endDate, noticeTitle, page, pageSize);
            PageInfo<Notice> noticePageInfo = new PageInfo<Notice>(notices);
            return noticePageInfo != null ? ResultData.dataResult("success", "读取公告信息成功", noticePageInfo) : Result.unDataResult("fail", "读取公告信息失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.unDataResult("fail", "读取公告信息异常");
        }
    }

    /**
     * 作用：添加或修改
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/addOrEdit")
    public Result addOrEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addOrEdit = request.getParameter("addOrEdit");
        String id = request.getParameter("id");
        String noticeTitle = request.getParameter("noticeTitle");
        String content = request.getParameter("content");
        try {
            //判断参数完整
            if (StringUtils.isBlank(noticeTitle) || StringUtils.isBlank(content)) {
                return ResultData.unDataResult("fail", "参数不完整");
            }
            Notice notice = new Notice();
            if (!StringUtils.isBlank(id)){
                notice.setId(Long.parseLong(id));
            }
            //设置标题和内容
            notice.setNoticeTitle(noticeTitle);
            notice.setContent(content);
            //设置机构信息
            Oper oper = (Oper) request.getSession().getAttribute("oper");
            notice.setOrgId(oper.getOrg_id());
            notice.setRelationNo(oper.getOrg_relation_no());
            //设置时间信息 日期为YYYY-MM-DD 时间为HH:mm:ss
            String time = StringUtil.getCurrentTime();
            notice.setCreateDate(time.split(" ")[0]);
            notice.setCreateTime(time.split(" ")[1]);
            //设置状态，默认设置为“normal”状态
            notice.setCstatus("normal");
            //判断是添加还是修改请求
            if ("add".equals(addOrEdit)) {
                //请求为添加请求，进行添加
                Integer result = noticeService.insert(notice);
                //判断是否添加成功
                if (result < 0) {
                    //添加失败
                    return ResultData.unDataResult("fail", "添加失败");
                }
            } else if ("edit".equals(addOrEdit)) {
                //请求为修改请求
                Integer editResult = noticeService.updateById(notice);
                if (editResult < 0) {
                    //修改失败
                    return ResultData.unDataResult("fail", "修改失败");
                }
            }
            return ResultData.unDataResult("success", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 作用：删除
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/del")
    public Result del(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            Integer delete = noticeService.deleteById(list);
            return ResultData.unDataResult("success", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail","系统异常");
        }
    }

    @RequestMapping(value = "/showNotice")
    public Result showNotice(HttpServletRequest request, HttpServletResponse response){
        try {
            String id = request.getParameter("id");
            Notice notice = new Notice();
            if(!StringUtils.isBlank(id)){
                notice = noticeService.findById(Long.parseLong(id));
                notice.setId(Long.valueOf(id));
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("notice", notice);
            return   ResultData.dataResult("success", "操作成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return   Result.unDataResult("fail", "操作异常");
        }
    }

}
