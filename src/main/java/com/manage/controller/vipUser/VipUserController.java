package com.manage.controller.vipUser;

import com.manage.controller.base.BaseController;
import com.manage.dao.TblOrgVipUserMapper;
import com.manage.dao.TblUserMapper;
import com.manage.model.Oper;
import com.manage.model.TblOrgVipUser;
import com.manage.model.TblUser;
import com.manage.model.TblUserExample;
import com.manage.service.UserVipService;
import com.manage.utils.DESUtil;
import com.manage.utils.ResponseObj;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import com.xdream.kernel.util.JsonUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/vipManage")
@RestController
public class VipUserController extends BaseController {

    @Autowired
    private TblUserMapper userMapper;

    @Autowired
    private UserVipService userVipService;

    @Autowired
    private TblOrgVipUserMapper orgVipUserMapper;


    @Value("${imageSaveDir}")
    private String imageSaveDir;


    @RequestMapping(value = "/getVip")
    public Result findByPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        String realName = request.getParameter("newsName");
        List<TblUser> news = userVipService.findByPage(realName, page, pageSize);
        PageInfo<TblUser> newsPageInfo = new PageInfo<TblUser>(news);
        return newsPageInfo != null ? ResultData.dataResult("success", "读取vip用户列表成功", newsPageInfo) : Result.unDataResult("fail", "读取vip用户列表失败");
    }

    @RequestMapping(value = "/delVip")
    public Result deleteNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id = request.getParameter("id");
            if (StringUtils.isBlank(id)) {
                return ResultData.unDataResult("fail", "缺少参数");
            }
            List<Long> list = new ArrayList<Long>();
            String[] ids = id.split(",");
            for (String s : ids) {
                list.add(Long.parseLong(s));
            }
            Integer delete = orgVipUserMapper.updateVipUser(list);
            delete = orgVipUserMapper.updateDelStatus(list);
            return delete > 0 ? ResultData.unDataResult("success", "删除成功") : Result.unDataResult("fail", "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    public String getFileImgName(String userId,String newsImageUrl) throws Exception{
        String serverUrl = "http://47.99.209.40:8081/goldccm-imgServer/goldccm/image/gainData";
        File file = new File(imageSaveDir,newsImageUrl);
        String fileRquestParam = "file";
        String imageFileName="";
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("userId", userId);
        dataMap.put("type", "3");
        Connection.Response res = doPostFileRequest(serverUrl, dataMap, file, fileRquestParam);
        if (res != null) {
            String resultJson = res.parse().getElementsByTag("body").text();
            ResponseObj resObj= (ResponseObj) JsonUtil.toObj(resultJson, ResponseObj.class);
            if (200 == res.statusCode()) {
                //更新到数据库
                /*updateImgUrl(userId, flag, resObj.getData().getImageFileName());*/
                System.out.println(resObj.getData().getImageFileName());
                //删除本地文件
                file.delete();
                imageFileName=resObj.getData().getImageFileName();
                System.out.println("上传服务器成功！");
            }
        }else {
            System.out.println("上传服务器失败！");
        }
        return imageFileName;
    }

    public int inserCount(Long userId,String authDate,String authTime,Long orgid,String realName){
        TblOrgVipUser vipUser=new TblOrgVipUser();
        vipUser.setCreatedate(authDate);
        vipUser.setCreatetime(authTime);
        vipUser.setOrgid(orgid);
        vipUser.setUserid(userId);
        vipUser.setRoletype("manage");
        vipUser.setUsername(realName);
        vipUser.setStatus("applySuc");
        vipUser.setCurrentstatus("normal");
        int i = orgVipUserMapper.insertSelective(vipUser);
        return i;
    }


    @RequestMapping(value = "/addOrUpdVip")
    public Result addNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addOrEdit = request.getParameter("addOrEdit");
        String id = request.getParameter("id");
        String newsName = request.getParameter("newsName");//姓名
        String mobilePhone = request.getParameter("mobilePhone");//手机号
        String idCard = request.getParameter("idCard");//身份证号
        String newsImageUrl = request.getParameter("newsImageUrl");
        Oper operSession = (Oper)request.getSession().getAttribute("oper");

        if (StringUtils.isBlank(newsName)
                && StringUtils.isBlank(mobilePhone) && StringUtils.isBlank(idCard)&& StringUtils.isEmpty(newsImageUrl)) {
            return ResultData.unDataResult("fail", "缺少参数");
        }

    //即是公司员工 又是VIP


        String iB4drRzSrCIdCard = DESUtil.encode("iB4drRzSrC", idCard);
        if ("add".equals(addOrEdit)) {
            Date date = new Date();
            String authDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            String authTime = new SimpleDateFormat("HH:mm:ss").format(date);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, 1);
            String validityDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

            //姓名+手机号 查询tbl_user表
            TblUserExample example=new TblUserExample();
            example.createCriteria().andRealnameEqualTo(newsName).andPhoneEqualTo(mobilePhone);
            List<TblUser> tblUsers = userMapper.selectByExample(example);
            int i=0;
            if(tblUsers.size()>0){
                String isauth = tblUsers.get(0).getIsauth();
                if(isauth.equals("T")){
                    //已实名认证  VIP表添加记录
                    int inserCount = inserCount(tblUsers.get(0).getId(), authDate, authTime, operSession.getOrg_id(), newsName);
                    String fileImgName = getFileImgName(String.valueOf(tblUsers.get(0).getId()), newsImageUrl);
                    TblUser u=new TblUser();
                    u.setId(tblUsers.get(0).getId());
                    u.setIdhandleimgurl(fileImgName);
                    inserCount = userMapper.updateByPrimaryKeySelective(u);
                    return inserCount > 0 ? ResultData.unDataResult("success", "插入成功") : Result.unDataResult("fail", "插入失败");
                }else {
                    //未实名认证F  isauth 变更为T tbl_user添加记录 并在VIP表添加记录
                    TblUser user=new TblUser();
                    user.setIsauth("T");
                    user.setRealname(newsName);
                    user.setCreatedate(authDate);
                    user.setCreatetime(authTime);
                    user.setIdno(iB4drRzSrCIdCard);
                    user.setPhone(mobilePhone);
                    user.setOrgid(operSession.getOrg_id());
                    user.setValiditydate(validityDate);
                    user.setLoginname(mobilePhone);
                    user.setId(tblUsers.get(0).getId());
                    i = userMapper.updateByPrimaryKeySelective(user);
                    String fileImgName = getFileImgName(String.valueOf(tblUsers.get(0).getId()), newsImageUrl);
                    TblUser u=new TblUser();
                    u.setId(tblUsers.get(0).getId());
                    u.setIdhandleimgurl(fileImgName);
                    i = userMapper.updateByPrimaryKeySelective(u);
                    //VIP表添加记录
                    i= inserCount(tblUsers.get(0).getId(), authDate, authTime, operSession.getOrg_id(), newsName);
                    return i > 0 ? ResultData.unDataResult("success", "插入成功") : Result.unDataResult("fail", "插入失败");
                }
            }

            TblUser user=new TblUser();
            user.setIsauth("T");
            user.setRealname(newsName);
            user.setCreatedate(authDate);
            user.setCreatetime(authTime);
            user.setIdno(iB4drRzSrCIdCard);
            user.setPhone(mobilePhone);
            user.setOrgid(operSession.getOrg_id());
            user.setLoginname(mobilePhone);
            user.setValiditydate(validityDate);
            int insert = userMapper.insertSelective(user);
            if (insert>0){
                Long userId = user.getId();
                String fileImgName = getFileImgName(String.valueOf(userId), newsImageUrl);
                TblUser u=new TblUser();
                u.setId(userId);
                u.setIdhandleimgurl(fileImgName);
                insert = userMapper.updateByPrimaryKeySelective(u);
                insert = inserCount(userId, authDate, authTime, operSession.getOrg_id(), newsName);

            }
            return insert > 0 ? ResultData.unDataResult("success", "插入成功") : Result.unDataResult("fail", "插入失败");
        } else if ("edit".equals(addOrEdit)) {
            TblUser user=new TblUser();
            user.setId(Long.parseLong(id));
            user.setRealname(newsName);
            user.setIdno(iB4drRzSrCIdCard);
            user.setPhone(mobilePhone);
            String fileImgName = getFileImgName(String.valueOf(id), newsImageUrl);
            user.setIdhandleimgurl(fileImgName);
            int update = userMapper.updateByPrimaryKeySelective(user);
            return update > 0 ? ResultData.unDataResult("success", "修改成功") : Result.unDataResult("fail", "修改失败");
        } else {
            return ResultData.unDataResult("fail", "系统异常");
        }
    }


    @RequestMapping(value = "/upNewsImg", method = RequestMethod.POST,consumes = "multipart/form-data")
    public Result upImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String flag = request.getParameter("flag");
        boolean isSucc = true;
        MultipartHttpServletRequest multipartRequest = null;
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multipartRequest.getFileNames();
            MultipartFile myfile = null;
            String json = "";
            String realFileName = "";
            File files = null;
            try {
                if (iter.hasNext()) {
                    String key = iter.next();
                    myfile = multipartRequest.getFile(key);
                    if (myfile.isEmpty()) {

                    } else {
                        String originalFilename = myfile.getOriginalFilename();
                        if (!originalFilename.matches(".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$")) {
                            isSucc = false;
                        } else {
                            File file = new File(imageSaveDir);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
                            String newFileName = System.currentTimeMillis() + "";
                            int index = originalFilename.lastIndexOf(".");

                            String fileNameType = originalFilename.substring(index, originalFilename.length());
                            realFileName = newFileName + fileNameType;
                            files = new File(imageSaveDir, realFileName);
                            FileUtils.copyInputStreamToFile(myfile.getInputStream(), files);
                            Map<String, Object> map=new HashMap<>();
                            map.put("imageFileName",realFileName);
                            return ResultData.dataResult("success", "上传成功", map );
                        }
                    }
                }else {
                    return ResultData.unDataResult("fail", "没有文件上传");
                }

            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.unDataResult("fail", "系统异常");
            }
        }
        return ResultData.unDataResult("fail", "系统异常");
    }




    public static Connection.Response doPostFileRequest(String url, Map<String, String> paramMap, File file, String
            fileRequestParam) throws Exception {
        if (StringUtils.isBlank(url)) {
            throw new Exception("The request URL is blank.");
        }
        // Https请求
        if (StringUtils.startsWith(url, "https")) {
            trustEveryone();
        }
        Connection connection = Jsoup.connect(url);
        connection.method(Connection.Method.POST);
        connection.timeout(12000);
        connection.header("Content-Type", "multipart/form-data");
        connection.ignoreHttpErrors(true);
        connection.ignoreContentType(true);
        if (paramMap != null && !paramMap.isEmpty()) {
            connection.data(paramMap);
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            //FileInputStream fiss = new FileInputStream(new File(""));
            //connection.data(fileRequestParam, file.getName(), fiss);
            connection.data(fileRequestParam, file.getName(), fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection.Response response = connection.execute();
            if (response.statusCode() != HttpStatus.SC_OK) {
                throw new Exception("http请求响应码:" + response.statusCode() + "");
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解决Https请求,返回404错误
     */
    private static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}