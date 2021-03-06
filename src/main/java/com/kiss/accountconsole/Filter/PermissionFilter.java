package com.kiss.accountconsole.Filter;

import com.kiss.accountconsole.feign.account.AccountServiceFeign;
import com.kiss.accountconsole.permission.GetRequestPermission;
import com.kiss.accountconsole.permission.PostRequestPermission;
import exception.StatusException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import utils.JwtUtil;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
@Order(value = 1)
@WebFilter(filterName = "permissionFilter",urlPatterns = "/*")
public class PermissionFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ServletContext context = filterConfig.getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
        String token = httpServletRequest.getHeader("X-Access-Token");
        String method = httpServletRequest.getMethod();
        String uri = httpServletRequest.getRequestURI();
        String permissionCode = method + "@" + uri;
        System.out.println("permissionCode" + permissionCode);

        HttpServletRequest requestWrapper = null;
        boolean loginBoolean = false;
        if (!uri.contains("/service/account/login") && !uri.contains("/service/account/root/check") && !uri.contains("/service/account/root") && !uri.contains("swagger") && !uri.contains("api-docs")) {

            //除登录接口，都需要校验权限
            AccountServiceFeign accountServiceFeign = ac.getBean(AccountServiceFeign.class);
            Integer id = JwtUtil.getUserId(token);
            List<String>  permissions = accountServiceFeign.getAccountPermissions(id);

            boolean permissionFlag = true;

            if (permissions.size() == 1 && "*".equals(permissions.get(0))) {
                permissionFlag = false;
            }

            if (!permissions.contains(permissionCode) && permissionFlag) {
                setResponseParameter(response);
                return;
            }

            //数据权限
            if (!StringUtils.isEmpty(method) && !method.equals("OPTIONS") && permissionFlag) {
                loginBoolean = true;
                //获取权限列表
                List<String>  dataScopeObj = accountServiceFeign.getAccountPermissionDataScope(id,permissionCode);

                if (dataScopeObj == null ) {
                    setResponseParameter(response);
                    return;
                }

                List<String> dataScopes = (List<String>) dataScopeObj;

                if (dataScopes.size() == 0 || dataScopes.get(0) == null) {
                    setResponseParameter(response);
                    return;
                }

                if (method.equals("POST") || method.equals("PUT")) {

                    requestWrapper = PostRequestPermission.check(request,dataScopes);

                    if (requestWrapper == null) {
                        setResponseParameter(response);
                        return;
                    }
                } else if (method.equals("GET") || method.equals("DELETE")) {
                    boolean getCheck = GetRequestPermission.check(request,dataScopes);

                    if (!getCheck) {
                        setResponseParameter(response);
                        return;
                    }
                }


            }
        }

        if (loginBoolean && (method.equals("POST") || method.equals("PUT"))) {
            chain.doFilter(requestWrapper,response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    public void setResponseParameter (ServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        throw  new StatusException(HttpStatus.SC_FORBIDDEN, "权限不足");
    }
}
