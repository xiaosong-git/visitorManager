package com.manage.utils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Linyb
 * @Date 2017/1/10.
 */
public class BaseUtil {

    /**
     * 随机获取一串字符串
     * @Author Linyb
     * @Date 2017/1/10 16:16
     */
    public static String getRandomStr(int length){
        return UUID.randomUUID().toString().substring(0,4);
    }

    public static Integer objToInteger(Object obj, Integer def){
        if(obj == null) {
            return def;
        }
        return Integer.parseInt(obj.toString());
    }

    public static String objToStr(Object obj,String def){
        if(obj == null) return def;
        return obj.toString();
    }

    public static BigDecimal objToBigDecimal(Object obj, BigDecimal def){
        if(obj == null) return def;
        return new BigDecimal(obj.toString());
    }

    public static Map<String,Object> remove(Map<String,Object> beRemoveMap ,String ... keys){
        for (String key : keys) {
            beRemoveMap.remove(key);
        }
        return beRemoveMap;
    }
}
