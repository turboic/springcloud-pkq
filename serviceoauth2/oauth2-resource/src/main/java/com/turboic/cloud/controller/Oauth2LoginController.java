package com.turboic.cloud.controller;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 测试oauth-client的controller
 * @author liebe
 */
@Api(value="oauth-client的controller",tags={"oauth-client演示接口"})
@Controller
@RequestMapping("/login")
public class Oauth2LoginController {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2LoginController.class);
    public Oauth2LoginController() {
    }
    @RequestMapping(value = "/auth")
    public ModelAndView get(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        String code = request.getParameter("code");
        modelAndView.addObject("code",code);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        modelAndView.addObject("date",simpleDateFormat.format(new Date()));
        return modelAndView;
    }
}
