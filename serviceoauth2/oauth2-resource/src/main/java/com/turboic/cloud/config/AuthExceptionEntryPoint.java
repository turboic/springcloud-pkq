package com.turboic.cloud.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liebe
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint
{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException
    {
        final Map<String, Object> mapBodyException = new HashMap<>() ;

        mapBodyException.put("error"    , arg2.getCause()) ;
        mapBodyException.put("message"  , arg2.getMessage()) ;
        mapBodyException.put("exception", arg2.getStackTrace()) ;
        mapBodyException.put("path"     , request.getServletPath()) ;
        mapBodyException.put("timestamp", (new Date()).getTime()) ;

        response.setContentType("application/json") ;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;

        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), mapBodyException) ;
    }
}