package com.kiss.accountconsole.permission;

import java.util.Map;

public class PermissionValueEstimate {

    public static boolean estimateNotPermissionArray (String value, String requestParam, String key, Map<String, Boolean> requestMap) {

        String realParamObj = value.substring(2, value.length() - 1);
        String[] realParams = realParamObj.split(",");
        return paramPollEstimate(false,realParams,requestParam,key,requestMap);
    }

    public static boolean estimatePermissionArray (String value, String requestParam, String key, Map<String, Boolean> requestMap) {

        String realParamObj = value.substring(2, value.length() - 1);
        String[] realParams = realParamObj.split(",");
        return paramPollEstimate(true,realParams,requestParam,key,requestMap);
    }

    public static boolean estimateNotSinglePermission (String value, String requestParam, String key, Map<String, Boolean> requestMap) {

        String[] realParams = value.split(",");
        return paramPollEstimate(false,realParams,requestParam,key,requestMap);

    }

    public static boolean estimateSinglePermission (String value, String requestParam, String key, Map<String, Boolean> requestMap) {

        String[] realParams = value.split(",");
        return paramPollEstimate(true,realParams,requestParam,key,requestMap);
    }

    public static boolean paramPollEstimate(boolean sign, String[] realParams, String requestParam, String key, Map<String, Boolean> requestMap) {
        for (String realParam : realParams) {
            if (realParam.startsWith("!") && requestParam.equals(realParam.substring(1))) {
                //sign 为 true 表示参数前面没有!
                if (sign) {
                    //与权限违背
                    //权限不足
                    return false;
                } else {
                    //权限匹配
                    requestMap.put(key, true);
                    return true;
                }

            }

            if (requestParam.equals(realParam)) {

                if (sign) {
                    //匹配成功
                    requestMap.put(key, true);
                    return true;
                } else {
                    //与权限违背
                    //权限不足
                    return false;
                }
            }
        }

        return true;
    }
}
