package com.turboic.cloud.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liebe
 */
@WebServlet(urlPatterns = "/my",name="spring-boot集成servlet",
        loadOnStartup=1,asyncSupported=true,displayName="liebe",largeIcon="largeIcon")
public class MyServlet extends HttpServlet {

    private static final Log log = LogFactory.getLog(StandardServlet.class);
    public MyServlet(){
        log.info("构造方法初始化类MyServlet");
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("text/html;charset=utf-8");
        httpServletResponse.getWriter().println("spring-boot集成servlet方法测试");
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
        log.info("doGet方法");

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        log.info("doPost方法");
        doGet(httpServletRequest,httpServletResponse);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("service方法");
        super.service(req, resp);
    }
}