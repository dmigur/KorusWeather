package com.corus.util;

import org.json.JSONObject;

public class JsonUtil {

    public static Object getValue(JSONObject obj, String key){
        try{
            return obj.get(key);
        }catch(Exception e){
            return null;
        }

    }


}
