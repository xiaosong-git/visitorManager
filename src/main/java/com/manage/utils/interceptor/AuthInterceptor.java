package com.manage.utils.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.manage.model.Auth;
import com.manage.model.Button;
import com.manage.model.Oper;
import com.manage.service.IAuthService;
import com.manage.service.IButtonService;
import com.manage.utils.ResponseUtil;
import com.manage.utils.VerifyUtil;
import com.manage.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by L on 2017/8/9.
 */
@Configurable
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private IAuthService authService;

    @Autowired
    private IButtonService buttonService;

    private static final String[] IGNORE_URI = {"/kaptcha","/login","/auth/getAuth"};

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        logger.info("requestUrl:"+request.getRequestURL());
        try {
            boolean flag = false;
            String servletPath = request.getServletPath();
            // 检测是否为需要拦截的请求
            for (String s : IGNORE_URI) {
                if (servletPath.contains(s)) {
                    //System.out.println("该请求不需要拦截");
                    flag = true;
                }

            }
            // 需要拦截处理的请求
            if (!flag) {
                String menuId = request.getParameter("InterceptorMenuId");
                String buttonId = request.getParameter("InterceptorButtonId");

                if(menuId==null || buttonId==null){
                    ResponseUtil.responseJson(response,JSONObject.toJSONString(Result.unDataResult("fail","参数异常")));
                    return false;
                }

                if(!VerifyUtil.isInteger(menuId) || !VerifyUtil.isInteger(buttonId)){
                    ResponseUtil.responseJson(response,JSONObject.toJSONString(Result.unDataResult("fail","数据异常")));
                    return false;
                }

                Oper oper = (Oper) request.getSession().getAttribute("oper");
                Long roleId = oper.getRole_id();
    //            System.out.println(menuId+"---"+buttonId+"---"+roleId);
    //            System.out.println(authService);
                //判断是否有权限
                /*if (buttonId == null) {
                    List<Auth> auths = authService.selectByRoleIdAndMenuId(roleId, Long.valueOf(menuId));
                    if (auths != null && !auths.isEmpty() && auths.size()>0) {
                        return true;
                    }
                    ResponseUtil.responseJson(response,JSONObject.toJSONString(Result.unDataResult("fail","您没有该菜单的权限!")));
                    return false;
                } else {*/
                Auth auth = authService.selectBybtnIdAndRoleIdAndMenuId(Long.valueOf(buttonId), roleId, Long.valueOf(menuId));
                if (auth != null) {
                    Button button = buttonService.findById(Long.valueOf(buttonId));
                    if(button!=null && servletPath.equals(button.getActionUrl())){
                        return true;
                    }
                }
                ResponseUtil.responseJson(response,JSONObject.toJSONString(Result.unDataResult("fail","您没有该按钮的权限！")));
                return false;
//                }
            }else{
                return flag;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception{
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
