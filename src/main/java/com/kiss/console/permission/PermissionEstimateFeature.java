package com.kiss.console.permission;

import com.alibaba.fastjson.JSONObject;
import com.kiss.console.regex.Regex;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PermissionEstimateFeature {

    public static boolean estimateValue (String value, String requestParam, String key, Map<String, Boolean> requestMap) {

        System.out.println("判断value" + value);
        if (requestParam == null) {
            return true;
        }

        System.out.println(Pattern.matches(Regex.NOT_PERMISSION_ARRAY, value));
        System.out.println(Pattern.matches(Regex.PERMISSION_ARRAY, value));
        System.out.println(Pattern.matches(Regex.NOT_SINGLE_PERMISSION, value));
        System.out.println(Pattern.matches(Regex.SINGLE_PERMISSION, value));


        if (Pattern.matches(Regex.NOT_PERMISSION_ARRAY, value)) {
            return PermissionValueEstimate.estimateNotPermissionArray(value,requestParam,key,requestMap);
        } else if (Pattern.matches(Regex.PERMISSION_ARRAY, value)) {
            System.out.println("==" + value);
            return PermissionValueEstimate.estimatePermissionArray(value,requestParam,key,requestMap);
        } else if (Pattern.matches(Regex.NOT_SINGLE_PERMISSION, value)) {
            System.out.println("---" + value);
            return PermissionValueEstimate.estimateNotSinglePermission(value,requestParam,key,requestMap);
        } else if (Pattern.matches(Regex.SINGLE_PERMISSION, value)) {
            return PermissionValueEstimate.estimateSinglePermission(value,requestParam,key,requestMap);
        }

        return true;
    }

    public static boolean estimateKey (String value, String key, JSONObject bodyObj, Map<String, Boolean> requestMap) {
        if (Pattern.matches(Regex.CHARACTER_POINT_BRACKET, key)) {
            return PermissionKeyEstimation.estimateCharacterPointBracket(value,key,bodyObj,requestMap);
        } else if (Pattern.matches(Regex.CHARACTER_POINT, key)) {
            return PermissionKeyEstimation.estimateCharacterPoint(value,key,bodyObj,requestMap);
        } else if (Pattern.matches(Regex.ONLY_CHARACTER, key)) {
            return PermissionKeyEstimation.estimateOnlyEstimate(value,key,bodyObj,requestMap);
        }

        return true;
    }
}
