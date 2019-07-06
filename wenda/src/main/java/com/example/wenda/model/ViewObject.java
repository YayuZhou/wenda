package com.example.wenda.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zyy
 * @date 2019/7/2 22:57
 */
public class ViewObject {
    private Map<String,Object> objs = new HashMap<>();
    public void set(String key,Object value){
        objs.put(key,value);
    }

    public Object get(String key){
        return objs.get(key);
    }
}
