package com.example.getway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        // pre：路由之前
        // routing：路由之时
        // post： 路由之后
        // error：发送错误调用
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
//        return RequestContext.getCurrentContext().containsKey("throwable");
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.debug(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));


        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.OK.value());
        try {
            ctx.getResponse().getWriter().write("接口错误");
        }catch (Exception e){
            e.printStackTrace();
            log.error("error:", e);
        }

        return null;
    }
}
