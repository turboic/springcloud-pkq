package com.turboic.cloud.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liebe
 */
public class CustomHandlerInterceptor implements HandlerInterceptor {
    private final static Log log = LogFactory.getLog(CustomHandlerInterceptor.class);
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        String code = request.getParameter("code");
        log.info("执行自定义拦截方法postHandle："+code);
        if(StringUtils.isEmpty(code)){
            //modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            //modelAndView.addObject("auth_message","请求参数异常");
        }
    }
}
