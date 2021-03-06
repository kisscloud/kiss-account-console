package com.kiss.accountconsole.permission;

import com.alibaba.fastjson.JSONObject;
import com.kiss.accountconsole.Filter.RequestWrapper;
import com.kiss.accountconsole.utils.RequestUtil;
import com.kiss.accountconsole.utils.ThreadLocalUtil;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRequestPermission {

    public static HttpServletRequest check (ServletRequest request,List<String> dataScopes) throws IOException {

        String userMessage = RequestUtil.getRequestBody(request);
        Map<String, Boolean> requestMap = new HashMap<>();

        for (String dataScope : dataScopes) {

            JSONObject outerObject = JSONObject.parseObject(dataScope);
            Map<String, String> paramsMap = (Map<String, String>) outerObject.get("params");

            if (paramsMap == null) {
                return null;
            }

            boolean paramCheck = PermissionType.paramCheck(request,paramsMap,requestMap);
            if (!paramCheck) {
                return null;
            }

            Map<String, String> dataMap = (Map<String, String>) outerObject.get("data");

            boolean dataCheck = PermissionType.dataCheck(dataMap,requestMap,userMessage);
            if (!dataCheck) {
                return null;
            }
        }

        for (Map.Entry<String, Boolean> requestEntry : requestMap.entrySet()) {

            if (!requestEntry.getValue()) {
                //权限位没有找到对应的权限
                //权限不足
//                PermissionType.setResponseMessage(response);
                return null;
            }
        }

        //这里需要将requestbody回写回去
        return new RequestWrapper((HttpServletRequest) request,ThreadLocalUtil.getByte("requestBody"));
    }
}
