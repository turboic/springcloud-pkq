package com.turboic.cloud.websocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Component
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {
    private static final Logger log = LoggerFactory.getLogger(PrincipalHandshakeHandler.class);
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletServerHttpRequest.getServletRequest();
            final String token = httpRequest.getParameter("token");
            log.info("token={}",token);
            if (StringUtils.isEmpty(token)) {
                return null;
            }
            return () -> token;
        }
        return null;
    }
}