package com.kiss.accountconsole.permission;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.Map;

public class PermissionType {

    public static boolean paramCheck (ServletRequest request,Map<String, String> paramsMap, Map<String, Boolean> requestMap) throws IOException {

        for (Map.Entry<String, String> param : paramsMap.entrySet()) {
            //根据key获取请求参数
            String key = param.getKey();
            String requestParam = request.getParameter(key);

            if (StringUtils.isEmpty(requestParam)) {
                //表明请求地址中没有这个参数,就不需要对其做校验
                continue;
            }

            if (requestMap.containsKey(key) && requestMap.get(key)) {
                //表明此请求参数已经通过校验
                continue;
            }

            requestMap.put(key, false);
            String value = param.getValue();

            boolean estimate = PermissionEstimateFeature.estimateValue(value,requestParam,key,requestMap);

            if (estimate) {
                continue;
            } else {
//                setResponseMessage(response);
                return false;
            }

        }

        return true;
    }

    public static boolean dataCheck (Map<String, String> dataMap,Map<String, Boolean> requestMap,String userMessage) throws IOException {

        if (!StringUtils.isEmpty(userMessage) && dataMap != null && dataMap.size() != 0) {

            JSONObject bodyObj = JSONObject.parseObject(userMessage);

            for (Map.Entry<String, String> dataEntry : dataMap.entrySet()) {

                String key = dataEntry.getKey();
                String value = dataEntry.getValue();

                if (requestMap.containsKey(key) && requestMap.get(key)) {
                    //表明此请求参数已经通过校验
                    continue;
                }

                requestMap.put(key, false);

                boolean estimate = PermissionEstimateFeature.estimateKey(value,key,bodyObj,requestMap);

                if (estimate) {
                    continue;
                } else {
//                    setResponseMessage(response);
                    return false;
                }
            }
        }

        return true;
    }

//    public static void setResponseMessage (ServletResponse response) throws IOException {
//        response.setContentType("application/json; charset=utf-8");
//        response.setCharacterEncoding("UTF-8");
//        ResultOutput resultOutput1 = ResultOutputUtil.error(HttpStatus.SC_FORBIDDEN, "权限不足", null);
//        response.getWriter().write(JSONObject.toJSONString(resultOutput1));
//    }
}
