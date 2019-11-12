package com.manage.controller.dict;

import com.manage.controller.base.BaseController;
import com.manage.model.Dict;
import com.manage.model.DictItem;
import com.manage.service.IDictItemService;
import com.manage.utils.result.Result;
import com.manage.utils.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/dictItem")
@RestController
public class DictItemController extends BaseController {
    @Autowired
    private IDictItemService dictItemService;

    /**
     * 根据大类dict_code查找小类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getAllDictItem")
    public Result getAllDictItem(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String dict_code = request.getParameter("dict_code");
        String dict_text = request.getParameter("dict_text");
        try{
            if (StringUtils.isBlank(dict_code)){
                return ResultData.unDataResult("fail","参数不完整-dict_code");
            }
            if (StringUtils.isBlank(dict_text)){
                return ResultData.unDataResult("fail","参数不完整-dict_text");
            }
            List<DictItem> dictItems = null;
            dictItems = dictItemService.findDictItemByDictCode(dict_code);
            Dict dict = new Dict();
            dict.setDict_code(dict_code);
            dict.setDict_text(dict_text);
            return dictItems!=null ? ResultData.extraDataResult("success","读取小类参数成功",dictItems,dict) : Result.unDataResult("fail","读取小类参数失败");
        }catch(Exception e){
            e.printStackTrace();
            return ResultData.unDataResult("fail","系统异常");
        }
    }

    /**
     * 根据id修改item_text
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/editDictItem")
    public Result editIDictItem(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        String item_text = request.getParameter("item_text");
        try{
            if (StringUtils.isBlank(id)){
                return ResultData.unDataResult("fail","参数不完整-id");
            }
            if (StringUtils.isBlank(item_text)){
                return ResultData.unDataResult("fail","参数不完整-item_text");
            }
            int result = dictItemService.editDictItem(id,item_text);
            if (result<0){
                return ResultData.unDataResult("fail","修改小类字典参数失败");
            }
            return ResultData.unDataResult("success","修改小类字典参数成功");
        }catch(Exception e){
            e.printStackTrace();
            return ResultData.unDataResult("fail","系统异常");
        }
    }

    /**
     * 添加小类字典参数
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/addDictItem")
    public Result addIDictItem(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String dict_code = request.getParameter("dict_code");
        String item_code = request.getParameter("item_code");
        String item_text = request.getParameter("item_text");
        try{
            if (StringUtils.isBlank(dict_code)){
                return ResultData.unDataResult("fail","参数不完整-dict_code");
            }
            if (StringUtils.isBlank(item_code)){
                return ResultData.unDataResult("fail","参数不完整-item_code");
            }
            if (StringUtils.isBlank(item_text)){
                return ResultData.unDataResult("fail","参数不完整-item_text");
            }
            DictItem dictItem = new DictItem();
            dictItem.setDict_code(dict_code);
            dictItem.setItem_code(item_code);
            dictItem.setItem_text(item_text);
            int result = dictItemService.addDictItem(dictItem);
            if (result<0){
                return ResultData.unDataResult("fail","添加小类字典参数失败");
            }
            return ResultData.unDataResult("success","添加小类字典参数成功");
        }catch(Exception e){
            e.printStackTrace();
            return ResultData.unDataResult("fail","系统异常");
        }
    }

    /**
     * 根据id修改字典信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/deleteDictItem")
    public Result deleteDictItem(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
            String id=request.getParameter("id");
            if(StringUtils.isBlank(id)){
                return ResultData.unDataResult("fail","缺少参数");
            }
            List<Long> list=new ArrayList<Long>();
            String[] ids = id.split(",");
            for(String s : ids){
                list.add(Long.valueOf(s));
            }
            int result = dictItemService.deleteDictItem(list);
            if (result<0){
                return ResultData.unDataResult("fail","删除小类字典参数失败");
            }
            return ResultData.unDataResult("success","删除小类字典参数成功");
        }catch(Exception e){
            e.printStackTrace();
            return ResultData.unDataResult("fail","系统异常");
        }
    }
}
