package com.kiss.accountconsole.permission;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetRequestPermission {

    public static boolean check (ServletRequest request,List<String> dataScopes) throws IOException {

        Map<String, Boolean> requestMap = new HashMap<>();

        for (String dataScope : dataScopes) {

            JSONObject outerObject = JSONObject.parseObject(dataScope);
            Map<String, String> paramsMap = (Map<String, String>) outerObject.get("params");

            if (paramsMap == null) {
                return false;
            }

            return PermissionType.paramCheck(request,paramsMap,requestMap);

        }

        return true;
    }
}
