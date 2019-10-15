package com.jql.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class ObjectUtils {

    public static Map<String,Object> toMap(Object o){
        if(o instanceof Map){
            Map<String,Object> oMap = (Map<String,Object>)o;

            for (String key : oMap.keySet()) {
                oMap.put(key,JSON.toJSON(oMap.get(key)));
            }

            return oMap;
        }
        Map resultMap = JSONObject.parseObject(JSON.toJSONString(o), Map.class);
        return resultMap;
    }
}
