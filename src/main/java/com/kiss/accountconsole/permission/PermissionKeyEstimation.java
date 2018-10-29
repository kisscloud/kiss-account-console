package com.kiss.accountconsole.permission;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;

public class PermissionKeyEstimation {

    public static boolean estimateCharacterPointBracket (String value, String key, JSONObject bodyObj,Map<String, Boolean> requestMap) {
        String prefix = key.substring(0, key.indexOf("["));
        String suffix = key.substring(key.indexOf(".") + 1);
        JSONArray jsonArray = bodyObj.getJSONArray(prefix);

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(jsonArray.getString(i));
            String requestValue = jsonObject.getString(suffix);
            //类似于params里面param的值
            return PermissionEstimateFeature.estimateValue(value,requestValue,key,requestMap);
        }

        return true;
    }

    public static boolean estimateCharacterPoint (String value, String key, JSONObject bodyObj,Map<String, Boolean> requestMap) {
        String prefix = key.substring(0, key.indexOf("["));
        String suffix = key.substring(key.indexOf(".") + 1);
        JSONObject jsonObject = bodyObj.getJSONObject(prefix);
        String requestValue = jsonObject.getString(suffix);
        return PermissionEstimateFeature.estimateValue(value,requestValue,key,requestMap);
    }

    public static boolean estimateOnlyEstimate (String value, String key, JSONObject bodyObj,Map<String, Boolean> requestMap) {
        String requestValue = bodyObj.getString(key);
        return PermissionEstimateFeature.estimateValue(value,requestValue,key,requestMap);
    }
}
