package com.manage.controller.user;

import com.manage.model.Key;
import com.manage.model.Oper;
import com.manage.model.User;
import com.manage.service.IKeyService;
import com.manage.service.IParamsService;
import com.manage.service.IUserService;
import com.manage.utils.*;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import com.xdream.kernel.util.JsonUtil;
import com.xdream.kernel.util.ResponseUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 2017/8/18.
 */

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IParamsService paramsService;

    @Autowired
    private IKeyService keyService;

    @RequestMapping(value="/getPageUser")
    public Result getPageUser(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=true,defaultValue="1") Integer page,
                              @RequestParam(required=false,defaultValue="10") Integer pageSize) throws Exception{
        User user = RefectionUtil.getObjectFromRequest(request,User.class);
        HttpSession session = request.getSession();
        Oper oper = (Oper)session.getAttribute("oper");
        user.setRelationNo(oper.getOrg_relation_no());
        List<User> users = userService.findByPageLeft(user,page,pageSize);

        for (User tempUser:users) {
            String tempPhone = tempUser.getPhone();
            String tempLoginName=tempUser.getLoginName();
            tempPhone = VerifyUtil.subPhone(tempPhone);
            tempLoginName= VerifyUtil.subPhone(tempLoginName);
            tempUser.setPhone(tempPhone);
            tempUser.setLoginName(tempLoginName);
        }
        PageInfo<User> usersPageInfo=new PageInfo<User>(users);
        return usersPageInfo!=null ? ResultData.dataResult("success","读取app用户成功",usersPageInfo) : Result.unDataResult("fail","读取app用户失败");
    }

    @RequestMapping(value = "/getUserById")
    public Result getUserById(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String userId = request.getParameter("userId");
        if(StringUtils.isBlank(userId) || !VerifyUtil.isInteger(userId)){
            return ResultData.unDataResult("fail","参数异常");
        }
        User  user = userService.findByIdLeft(Long.valueOf(userId));

        String tempPhone = user.getPhone();
        String tempLoginName=user.getLoginName();
        if(tempPhone!=null && tempLoginName!=null){
            tempPhone = VerifyUtil.subPhone(tempPhone);
            tempLoginName= VerifyUtil.subPhone(tempLoginName);
        }
        user.setPhone(tempPhone);
        user.setLoginName(tempLoginName);
        return user!=null ? ResultData.dataResult("success","读取用户详情成功",user) : Result.unDataResult("fail","读取用户详情失败");
    }

    /**
     * 正在审核用户信息
     */
    @RequestMapping(value="/userIsAudit")
    public Result userIsAudit(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=true,defaultValue="1") Integer page,
                              @RequestParam(required=false,defaultValue="10") Integer pageSize) throws Exception{
        String companyName = request.getParameter("companyName");//公司名称
        String org_name = request.getParameter("org_ame");//机构名称
        String realName = request.getParameter("realName");//姓名
        String loginName = request.getParameter("loginName");//登录账号
        String phone = request.getParameter("phone");//手机号
        List<User> users = userService.findUserIsAudit(companyName,org_name, realName , loginName, phone, page,pageSize);
        for(int i=0; i<users.size(); i++){
            User tempUser = users.get(i);
            String tempPhone = tempUser.getPhone();
            String tempLoginName=tempUser.getLoginName();
            tempPhone = PhoneUtil.subPhone(tempPhone);
            tempLoginName= PhoneUtil.subPhone(tempLoginName);
            tempUser.setPhone(tempPhone);
            tempUser.setLoginName(tempLoginName);
        }
        PageInfo<User> usersPageInfo=new PageInfo<User>(users);

        return usersPageInfo!=null ? ResultData.dataResult("success","读取实名审核用户成功",usersPageInfo) : Result.unDataResult("fail","读取app用户失败");
    }

    /**
     * 判断是否有用户员在操作
     */
    @RequestMapping(value="/judge")
    public Result judge(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String verifyKey = id+"_userIsAuditImg";
        String identification= RedisUtil.getStrVal(verifyKey, 14);
        if (!StringUtils.isBlank(identification)) {
            return ResultData.unDataResult("fail", "已有操作员审核此用户！");
        }
        RedisUtil.setStr(verifyKey, "T", 14, 300);
        User user = userService.findById(Long.valueOf(id));
        String userUrl=paramsService.findByParamName("imageServerUrl");
        return ResultData.extraDataResult("success", "没有用户员在操作",user,userUrl);
    }

    /**
     * 判审核失败、成功
     */
    @RequestMapping(value="/updateSuccessOrFail")
    public Result updateSuccessOrFail(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        String content=request.getParameter("content");
        String isAuth=request.getParameter("isAuth");
        if (StringUtils.isBlank(id)||StringUtils.isBlank(isAuth)){
            return ResultData.unDataResult("fail", "参数不完整");
        }
        Integer update = null;
        try {
            if (isAuth.equals("T")) {
                return userService.txUpdateSuccess(Long.valueOf(id),isAuth);
            }else if (isAuth.equals("E")) {
                 update = userService.txUpdateFail(Long.valueOf(id), isAuth, content);
            }
            return update>0 ? ResultData.unDataResult("success","操作成功") : Result.unDataResult("fail","操作失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 删除Redis Value
     */
    @RequestMapping(value="/delUserAudit")
    public Result delUserAudit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String id = request.getParameter("id");
            String verifyKey = id+"_userIsAuditImg";
            RedisUtil.del(14, verifyKey);
            return ResultData.unDataResult("success", "删除Redis值成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 实名认证信息获取
     */
    @RequestMapping(value="/fingImg")
    public Result fingImg(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            User user = userService.findDetailById(Long.parseLong(id));
            String imageServerUrl=paramsService.findByParamName("imageServerUrl");
            map.put("imageServerUrl", imageServerUrl);
            //判断是否是运营商
            Oper oper = (Oper)request.getSession().getAttribute("oper");
            map.put("role",oper.getRole_id() );
            map.put("user", user);
            return ResultData.dataResult("success","读取实名信息成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 修改实名认证
     */
    @RequestMapping(value="/updateIdNOAndRealName")
    public Result updateIdNOAndRealName(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String id = request.getParameter("id");
            String realName = request.getParameter("realName").trim();
            String idNO = request.getParameter("idNO").trim();
            String cardNo = request.getParameter("cardNo").trim();
            String phone = request.getParameter("phone").trim();
            if (StringUtils.isBlank(id)||StringUtils.isBlank(realName)||StringUtils.isBlank(idNO)||StringUtils.isBlank(cardNo)||StringUtils.isBlank(phone)){
                return ResultData.unDataResult("fail", "参数不完整");
            }
            Key workKey=keyService.findByStatus("normal");
            cardNo= DESUtil.decode(workKey.getWorkKey(),cardNo);
            //去cardBin表查询银行卡号和银行名称
            //截取银行卡前6位
            if(cardNo!=null && cardNo.length()<8){
                return ResultData.unDataResult("fail", "卡号输入有误");
            }
            String cardBin6=cardNo.substring(0, 6);
            Integer update = userService.txUpInfoAndInsCardNo(Long.valueOf(id), idNO, realName, phone);
            return update>0 ? ResultData.unDataResult("success","实名成功") : Result.unDataResult("fail","实名失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 图片上传
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/upImg", method = RequestMethod.POST,consumes = "multipart/form-data")
    public Result upImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId =request.getParameter("userId");
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
                            String realPath = paramsService.findByParamName("imageClientPath");

                            File file = new File(realPath);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
                            String newFileName = System.currentTimeMillis() + "";
                            int index = originalFilename.lastIndexOf(".");

                            String fileNameType = originalFilename.substring(index, originalFilename.length());
                            realFileName = newFileName + fileNameType;
                            files = new File(realPath, realFileName);
                            FileUtils.copyInputStreamToFile(myfile.getInputStream(), files);
                        }
                    }
                }
                if (isSucc) {
                    //String backJson=pictureService.getCardData(files.getAbsolutePath(),userId);

                    String serverUrl = paramsService.findByParamName("imageServerApiUrl");
//                    serverUrl = serverUrl +"goldccm-imgServer/goldccm/news/uploadImage";
//                    String serverUrl = "http://106.15.93.116:8080/goldccm-imgServer/goldccm/news/uploadImage";//测试

                    File file = new File(files.getAbsolutePath());

                    String fileRquestParam = "file";
                    Map<String, String> dataMap = new HashMap<String, String>();
                    dataMap.put("userId", userId);
                    dataMap.put("type", "3");
                    Connection.Response res = doPostFileRequest(serverUrl, dataMap, file, fileRquestParam);
                    if (res != null) {
                        String resultJson = res.parse().getElementsByTag("body").text();
                        ResponseObj resObj= (ResponseObj) JsonUtil.toObj(resultJson, ResponseObj.class);

                        //-------------------------------
                        if (200 == res.statusCode()) {
                            if ("success".equals(resObj.getVerify().getSign())) {
                                //更新到数据库
                                updateImgUrl(userId, flag, resObj.getData().getImageFileName());
                                //删除本地文件
                                files.delete();
                                Map<String, Object> map = ResponseUtil.makeSuccessJson();
                                map.put("imageFileName", resObj.getData().getImageFileName());
                                return ResultData.dataResult("success", "上传成功", map );
                            } else {
                                return ResultData.unDataResult("fail", "上传失败");
                            }
                        } else {
                            return ResultData.unDataResult("fail", "没有正常返回");
                        }
                    } else {
                        return ResultData.unDataResult("fail", "上传地址有误");
                    }
                } else {
                    return ResultData.unDataResult("fail", "图片格式不正确");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.unDataResult("fail", "系统异常");
            }
        } else {
            return ResultData.unDataResult("fail", "没有文件上传");
        }
    }

    /**
     * @param url              请求的Url
     * @param paramMap         参数
     * @param file             文件
     * @param fileRequestParam form表单对应的文件name属性名
     * @return
     * @throws Exception
     */
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

    public void updateImgUrl(String userId,String flag,String imageFileName) throws Exception{
        try {
            if (!StringUtils.isBlank(userId)&&!StringUtils.isBlank(imageFileName)){
                if("idFrontImgUrl".equals(flag)){
                    userService.updateIdFrontImgUrl(Long.valueOf(userId), imageFileName);
                }
                if("idOppositeImgUrl".equals(flag)){
                    userService.updateIdOppositeImgUrl(Long.valueOf(userId), imageFileName);
                }
                if("idHandleImgUrl".equals(flag)){
                    userService.updateIdHandleImgUrl(Long.valueOf(userId), imageFileName);
                }
                if("bankCardImgUrl".equals(flag)){
                    userService.updateBankCardImgUrl(Long.valueOf(userId), imageFileName);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
