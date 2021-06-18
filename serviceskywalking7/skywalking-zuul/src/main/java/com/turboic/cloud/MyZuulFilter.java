package com.turboic.cloud;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author liebe
 * 自定义zuul路由的过滤器
 */
@Slf4j
@Component
public class MyZuulFilter extends ZuulFilter {

    /**
     * 该函数返回一个字符串代表过滤器的类型，这个类型就是在http请求过程中定义的各个阶段
     *
     * @return pre:可以在请求被路由之前调用；routing：路由请求时被调用；post：在routing和error过滤器之后被调用；error：处理请求时发送错误时被调用
     */
    @Override
    public String filterType() {
        log.info("in RibbonZuulFilter filterType");
        return "pre";
    }

    /**
     * 通过int值来定义过滤器的执行顺序，数值越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        log.info("in RibbonZuulFilter filterOrder");
        return 0;
    }

    /**
     * 返回一个bool值来判断此过滤器的run()方法是否执行：true执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        log.info("in RibbonZuulFilter shouldFilter");
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        return StringUtils.hasText(request.getServletPath()) && request.getServletPath().startsWith("/api/ribbon/");
    }

    /**
     * 可以进行一些逻辑判断控制程序是否继续执行
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        log.info("in RibbonZuulFilter run");
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String serviceId = request.getParameter("serviceId");
        if(!StringUtils.hasText(serviceId)){
            try {
                RequestContext.getCurrentContext().getResponse().getWriter().write("serviceId参数不能为空");
                RequestContext.getCurrentContext().getResponse().getWriter().flush();
            } catch (IOException e) {
                log.error("自定义ZuulFilter的run方法执行出现异常:{}",e);
            }
        }
        return null;
    }
}