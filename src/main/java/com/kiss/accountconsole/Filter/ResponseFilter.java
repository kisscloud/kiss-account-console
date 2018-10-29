package com.kiss.accountconsole.Filter;

import com.alibaba.fastjson.JSONObject;
import com.kiss.accountconsole.enums.CodeEnums;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseFilter implements InnerFilter {

    private ResponseWrapper responseWrapper;

    private CodeEnums codeEnums;


    public ResponseFilter (ResponseWrapper responseWrapper,CodeEnums codeEnums) {
        this.responseWrapper = responseWrapper;
        this.codeEnums = codeEnums;
    }
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,InnerFilterChain filterChain) {
        filterChain.doFilter(request,response,filterChain);
        byte[] bytes = responseWrapper.getBytes();
        try {
            String responseMsg = new String(bytes,"utf-8");
            JSONObject jsonObject = JSONObject.parseObject(responseMsg);
            String lang = request.getHeader("X-LANGUAGE");
            if(!StringUtils.isEmpty(lang) && !StringUtils.isEmpty(jsonObject.getInteger("code")) && StringUtils.isEmpty(jsonObject.getString("message"))) {
                String message = codeEnums.getMessage(Integer.parseInt(jsonObject.get("code").toString()));
                jsonObject.put("message",message);
                bytes = jsonObject.toJSONString().getBytes();
            }
        } catch (Exception e) {

        } finally {
            try {
                response.getOutputStream().write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
