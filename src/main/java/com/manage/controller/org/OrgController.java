package com.manage.controller.org;

import com.manage.model.Oper;
import com.manage.model.Org;
import com.manage.service.IOperService;
import com.manage.service.IOrgService;
import com.manage.utils.Base64;
import com.manage.utils.MD5Util;
import com.manage.utils.QRCodeUtil;
import com.manage.utils.RefectionUtil;
import com.manage.utils.VerifyUtil;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import com.xdream.kernel.dao.jdbc.Ids;
import com.xdream.kernel.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * Created by L on 2017/8/13.
 */

@RequestMapping("/org")
@RestController
public class OrgController {

    @Value("${sendParams}")
    private String sendUrl;

    @Value("${qrcodeimgs}")
    private String qrcodeImgs;

    @Value("${imgPath}")
    private String imgPath;

    @Autowired
    private IOrgService orgService;

    @Autowired
    private IOperService operService;


    @RequestMapping(value="/getPageOrg")
    public Result getPageOrg(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=true,defaultValue="1") Integer page,
                             @RequestParam(required=false,defaultValue="10") Integer pageSize) throws Exception{
        Org org = RefectionUtil.getObjectFromRequest(request,Org.class);
        HttpSession session = request.getSession();
        Oper oper = (Oper)session.getAttribute("oper");
        org.setRelation_no(oper.getOrg_relation_no());
        List<Org> orgs = orgService.findByPageLeft(org,page,pageSize);
        PageInfo<Org> orgPageInfo=new PageInfo<Org>(orgs);
        return orgPageInfo!=null ? ResultData.dataResult("success","分页读取代理商成功",orgPageInfo) : Result.unDataResult("fail","分页读取代理商失败");
    }

    /**
     * 获取本机构下的大厦
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getAllOrg")
    public Result getAllOrg(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        Oper oper = (Oper)session.getAttribute("oper");
        String relation_no = oper.getOrg_relation_no();
        List<Org> orgs = orgService.getAllOrg(relation_no);
        return orgs!=null ? ResultData.dataResult("success","读取机构成功",orgs) : Result.unDataResult("fail","读取机构失败");
    }

    @RequestMapping(value="/addOrg")
    public Result addOrg(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Oper operSession = (Oper)request.getSession().getAttribute("oper");
        String org_name = request.getParameter("org_name");
        String realName = request.getParameter("realName");
        String phone = request.getParameter("phone");
        String addr = request.getParameter("addr");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String area = request.getParameter("area");
        String orgCode=request.getParameter("org_code");

        Org org = new Org();
        org.setOrg_name(org_name);
        org.setRealName(realName);
        org.setPhone(phone);
        org.setAddr(addr);
        org.setProvince(province);
        org.setCity(city);
        org.setArea(area);
        org.setOrg_code(orgCode);

        if(StringUtils.isBlank(org.getOrg_code())
                || StringUtils.isBlank(org.getOrg_name())
                || StringUtils.isBlank(org.getRealName())
                || StringUtils.isBlank(org.getPhone())){
            return ResultData.unDataResult("fail","缺少参数");
        }
        if (orgService.findByPhone(phone)!=null){
            return ResultData.unDataResult("fail","手机号码已经存在");
        }
        if(orgService.findByParam("org_code",org.getOrg_code())!=null){
            return ResultData.unDataResult("fail","机构编码已存在");
        }
        if(orgService.findByParam("org_name",org.getOrg_name())!=null){
            return ResultData.unDataResult("fail","机构名称已存在");
        }
        org.setRelation_no(operSession.getOrg_relation_no()+ VerifyUtil.getNowDate(new Date(), "yyyyMMddHHmmss")+".");
        org.setSid(operSession.getOrg_id());
        org.setSstatus("0");
        org.setIstop("F");
        org.setCreateDate(VerifyUtil.getNowDate(new Date(),"yyyy-MM-dd"));
        Oper oper = new Oper();
        //大楼:floor,物业:property，运营商:business;0:超级管理员
        Org orgSession = (Org) request.getSession().getAttribute("org");
        if ("0".equals(orgSession.getOrgType())){
            oper.setRole_id(2L);//运营商 2， 物业 3，大厦 4
            org.setOrgType("business");
        }else if ("business".equals(orgSession.getOrgType())){
            oper.setRole_id(3L);
            org.setOrgType("property");
        }else if ("property".equals(orgSession.getOrgType())){
            oper.setRole_id(4L);
            org.setOrgType("floor");
        }else{
            return Result.unDataResult("fail","你没有此权限!");
        }
        oper.setOper_code(org.getOrg_code());
        oper.setOper_name(org.getOrg_name());
        oper.setOrg_relation_no(org.getRelation_no());
        oper.setPwd(MD5Util.MD5("000000"));
        oper.setSstatus("0");
        oper.setLogin_name(org.getOrg_code());

        Boolean isInsert = orgService.addOrgAndOrgAccount(org,oper);

        return isInsert ? ResultData.unDataResult("success","添加机构成功") : Result.unDataResult("fail","添加机构失败");
    }

    @RequestMapping(value = "/chakanewm")
    public Result chakanewm(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        String s = Base64.encode(id.getBytes("UTF-8"));
        String sendParams=sendUrl+"?orgId="+s;
        // 生成的二维码的路径及名称
        String jpgName=new Random().nextInt(99999999)+".jpg";
        FileUtils.forceMkdir(new File(qrcodeImgs));
        QRCodeUtil.encode(sendParams, null, qrcodeImgs+jpgName, true);
        //图片地址
        String newUrl=imgPath+jpgName;
        return ResultData.dataResult("success","操作成功！",newUrl);
    }

    @RequestMapping(value="/updOrg")
    public Result updOrg(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String id = request.getParameter("id");
        String org_name = request.getParameter("org_name");
        String realName = request.getParameter("realName");
        String phone = request.getParameter("phone");
        String addr = request.getParameter("addr");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String area = request.getParameter("area");

        Org org = new Org();
        org.setId(Long.valueOf(id));
        org.setOrg_name(org_name);
        org.setRealName(realName);
        org.setPhone(phone);
        org.setAddr(addr);
        org.setProvince(province);
        org.setCity(city);
        org.setArea(area);
        if(
                org.getId()==null
                || StringUtils.isBlank(org.getOrg_name())
                || StringUtils.isBlank(org.getRealName())
                || StringUtils.isBlank(org.getPhone())
                || StringUtils.isBlank(org.getAddr())
                || StringUtils.isBlank(org.getProvince())
                || StringUtils.isBlank(org.getCity())
                        || StringUtils.isBlank(org.getArea())){
            return ResultData.unDataResult("fail","缺少参数");
        }

        Org verifyOrg = orgService.findById(org.getId());
        if(verifyOrg==null){
            return ResultData.unDataResult("fail","数据异常");
        }

        if(!org.getOrg_name().equals(verifyOrg.getOrg_name())) {
            if (orgService.findByParam("org_name", org.getOrg_name()) != null) {
                return ResultData.unDataResult("fail", "机构名称已存在");
            }
        }

        Integer isupd = orgService.updateOrgSelective(org);
        return isupd>0 ? ResultData.unDataResult("success","更新机构成功") : Result.unDataResult("fail","更新机构失败");
    }

    @RequestMapping(value="/detial")
    public Result detial(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String orgId = request.getParameter("orgId");
        if(StringUtils.isBlank(orgId)){
            return ResultData.unDataResult("fail","参数为空");
        }
        if(!VerifyUtil.isInteger(orgId)){
            return ResultData.unDataResult("fail","参数异常");
        }
        Org org =orgService.findByIdUnion(Long.valueOf(orgId));
        return ResultData.dataResult("success","查询机构成功",org);
    }

    @RequestMapping(value="/del")
    public Result del(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String ids = request.getParameter("ids");
        if (StringUtils.isBlank(ids)){
            return ResultData.unDataResult("fail","参数不完整");
        }
        try{
            List<Ids> idsList = StringUtil.parseIds(ids);
            for (Ids id : idsList){
                // 判断关联
                List<Org> orgs = orgService.findSubBySid(String.valueOf(id.getId()));
                if (!orgs.isEmpty() || orgs.size() > 0) {
                    return ResultData.unDataResult("fail", "有下级机构存在，不能删除");
                }
                List<Oper> opers = operService.findByOrgId(id.getId());
                if (!opers.isEmpty() || opers.size() > 0) {
                    return ResultData.unDataResult("fail", "有操作员存在，不能删除");
                }
            }
            orgService.batchDelete(ids);

            return ResultData.unDataResult("success", "操作成功");
        }catch(Exception e){
            e.printStackTrace();
            return ResultData.unDataResult("fail", "操作失败");
        }
    }

    /**
     * 修改机构状态
     */
    @RequestMapping(value = "/updateStatus")
    public Result updateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id=request.getParameter("id");
            String sstatus=request.getParameter("sstatus");
            if(StringUtils.isBlank(id)&&StringUtils.isBlank(sstatus)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            Org org = new Org();
            org.setId(Long.parseLong(id));
            org.setSstatus(sstatus);
            Integer update = orgService.updateStatus(org);
            return update > 0 ? ResultData.unDataResult("success", "修改状态成功") : Result.unDataResult("fail", "修改状态失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }
}
