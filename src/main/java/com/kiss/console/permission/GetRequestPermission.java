package com.kiss.console.permission;

import com.alibaba.fastjson.JSONObject;
import com.kiss.console.utils.RequestUtil;
import com.kiss.console.utils.ResultOutputUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import output.ResultOutput;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
