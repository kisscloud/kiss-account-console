package com.kiss.accountconsole.Filter;

import com.kiss.accountconsole.utils.LangUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(filterName = "responseFilter", urlPatterns = "/*")
@Order(value = 2)
public class ConsoleFilter implements Filter {

    private LangUtil langUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        langUtil = context.getBean(LangUtil.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        chain.doFilter(request, responseWrapper);

        InnerFilterChain innerFilterChain = new InnerFilterChain();
        innerFilterChain.addFilter(new ResponseFilter(responseWrapper, langUtil));
        innerFilterChain.doFilter(httpServletRequest, httpServletResponse, innerFilterChain);
    }

    @Override
    public void destroy() {

    }
}
