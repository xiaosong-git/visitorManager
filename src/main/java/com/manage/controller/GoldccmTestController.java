package com.manage.controller;

import com.manage.model.Button;
import com.manage.service.IButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/goldccm/springBoot")
@RestController//标注该类的所有请求方法返回json格式的不做视图解析
@Controller
public class GoldccmTestController {

	@Resource
	private IButtonService buttonService;

	//返回字符串转为json
	//@ResponseBody
	@RequestMapping("/test")
	public String test(){
		Button btn=null;
		try {
			//btn=buttonService.findById();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return btn.getActionUrl();
	}
	
	@RequestMapping("/testJsp")
	public ModelAndView testJsp(){
		HashMap<String,Object> test=null;
		return new ModelAndView("/index", test);
	}
	
	@RequestMapping("/testJspS")
	public String testJspS(){
		return "index";
	}
	
	/**
	 * 测试返回List json格式
	 * @return
	 */
	@RequestMapping("/testList")
	public List<String> testList(){
		List<String> list=new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		return list;
	}
	
	/**
	 * 异常捕获
	 * @param request
	 * @return
	 */
	@RequestMapping("/testError")
	public Integer testError(HttpServletRequest request){
		String id=request.getParameter("id");
		Integer idI=Integer.parseInt(id);
		return idI;
	}
	
}
