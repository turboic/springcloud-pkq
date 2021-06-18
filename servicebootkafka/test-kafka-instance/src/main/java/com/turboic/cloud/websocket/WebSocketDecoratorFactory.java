package com.turboic.cloud.websocket;

import com.turboic.cloud.KafkaInstanceApplication;
import com.turboic.cloud.util.FastJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import java.security.Principal;
@Component
public class WebSocketDecoratorFactory implements WebSocketHandlerDecoratorFactory {
    private static final Logger log = LoggerFactory.getLogger(KafkaInstanceApplication.class);
    @Override
    public WebSocketHandler decorate(WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                log.info("web socket 客户端主动建立连接  sessionId = {},session={}", session.getId(), FastJsonUtils.objectToJson(session));
                Principal principal = session.getPrincipal();
                if (principal != null) {
                    log.info("key = {} 存入,主体={}", principal.getName(),FastJsonUtils.objectToJson(principal));
                    SocketManager.add(principal.getName(), session);
                }
                super.afterConnectionEstablished(session);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                log.info("web socket 客户端主动断开连接  sessionId = {},session={}", session.getId(), FastJsonUtils.objectToJson(session));
                log.info("CloseStatus closeStatus  getCode = {},getReason={}", closeStatus.getCode(), closeStatus.getReason());
                Principal principal = session.getPrincipal();
                if (principal != null) {
                    SocketManager.remove(principal.getName());
                }
                super.afterConnectionClosed(session, closeStatus);
            }
        };
    }
}
