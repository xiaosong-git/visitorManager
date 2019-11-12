package com.manage.controller.news;

import com.manage.controller.base.BaseController;
import com.manage.model.News;
import com.manage.model.Oper;
import com.manage.service.INewsService;
import com.manage.service.IParamsService;
import com.manage.utils.ResponseObj;
import com.manage.utils.page.PageInfo;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import com.xdream.kernel.util.JsonUtil;
import com.xdream.kernel.util.ResponseUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

@RequestMapping("/news")
@RestController
public class NewsController extends BaseController {
    @Autowired
    private INewsService newsService;

    @Autowired
    private IParamsService paramsService;

    /**
     * 分页查询新闻
     */
    @RequestMapping(value = "/getPageNews")
    public Result findByPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        String newsName = request.getParameter("newsName");
        List<News> news = newsService.findByPage(newsName, page, pageSize);
        PageInfo<News> newsPageInfo = new PageInfo<News>(news);
        return newsPageInfo != null ? ResultData.dataResult("success", "读取新闻信息成功", newsPageInfo) : Result.unDataResult("fail", "读取新闻信息失败");
    }

    /**
     * 插入、修改新闻数据
     */
    @RequestMapping(value = "/addOrExitNews")
    public Result addNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String addOrEdit = request.getParameter("addOrEdit");
        String id = request.getParameter("id");
        String newsDate = request.getParameter("newsDate");
        String newsName = request.getParameter("newsName");
        String newsDetail = request.getParameter("newsDetail");
        String newsImageUrl = request.getParameter("newsImageUrl");
        String newsUrl = request.getParameter("newsUrl");
        String newsStatus = request.getParameter("newsStatus");

        if (StringUtils.isBlank(newsDate) && StringUtils.isBlank(newsName) && StringUtils.isBlank(newsDetail)
                && StringUtils.isBlank(newsImageUrl) && StringUtils.isBlank(newsUrl) && StringUtils.isBlank(newsStatus)) {
            return ResultData.unDataResult("fail", "缺少参数");
        }
        News news = new News();
        if (!StringUtils.isBlank(id)) {
            news.setId(Long.parseLong(id));
        }
        news.setNewsDate(newsDate);
        news.setNewsName(newsName);
        news.setNewsDetail(newsDetail);
        news.setNewsImageUrl(newsImageUrl);
        news.setNewsUrl(newsUrl);
        news.setNewsStatus(newsStatus);
        if ("add".equals(addOrEdit)) {
            int insert = newsService.addNews(news);
            return insert > 0 ? ResultData.unDataResult("success", "插入成功") : Result.unDataResult("fail", "插入失败");
        } else if ("edit".equals(addOrEdit)) {
            int update = newsService.updateNews(news);
            return update > 0 ? ResultData.unDataResult("success", "修改成功") : Result.unDataResult("fail", "修改失败");
        } else {
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 删除新闻数据
     */
    @RequestMapping(value = "/deleteNews")
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
            Integer delete = newsService.deleteNews(list);
            return delete > 0 ? ResultData.unDataResult("success", "删除成功") : Result.unDataResult("fail", "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.unDataResult("fail", "系统异常");
        }
    }

    /**
     * 修改新闻状态
     */
    @RequestMapping(value = "/updateNewsStatus")
    public Result updateNewsStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id=request.getParameter("id");
            String newsStatus=request.getParameter("newsStatus");
            if(StringUtils.isBlank(id)&&StringUtils.isBlank(newsStatus)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            News news = new News();
            news.setId(Long.parseLong(id));
            news.setNewsStatus(newsStatus);
            Integer update = newsService.updateNewsStatus(news);
            return update > 0 ? ResultData.unDataResult("success", "修改状态成功") : Result.unDataResult("fail", "修改状态失败");
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
        Oper oper = (Oper)request.getSession().getAttribute("oper");
        String userId =String.valueOf(oper.getId());
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
                    String serverUrl = paramsService.findByParamName("imageNewsApiUrl");
//                    serverUrl = serverUrl + "goldccm-imgServer/goldccm/news/uploadImage";
//                    String serverUrl = "http://106.15.93.116:8080/goldccm-imgServer/goldccm/news/uploadImage";//测试
                    File file = new File(files.getAbsolutePath());

                    String fileRquestParam = "file";
                    Map<String, String> dataMap = new HashMap<String, String>();
                    dataMap.put("type", "news");
                    Response res = doPostFileRequest(serverUrl, dataMap, file, fileRquestParam);
                    if (res != null) {
                        String resultJson = res.parse().getElementsByTag("body").text();
                        ResponseObj resObj= (ResponseObj) JsonUtil.toObj(resultJson, ResponseObj.class);

                        //-------------------------------
                        if (200 == res.statusCode()) {
                            if ("success".equals(resObj.getVerify().getSign())) {
                                //更新到数据库
                                /*updateImgUrl(userId, flag, resObj.getData().getImageFileName());*/
                                System.out.println(resObj.getData().getImageFileName());
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
    public static Response doPostFileRequest(String url, Map<String, String> paramMap, File file, String
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
            Response response = connection.execute();
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
