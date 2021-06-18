package com.turboic.cloud.websocket;
import com.turboic.cloud.util.FastJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;
import java.util.concurrent.ConcurrentHashMap;
public class SocketManager {
    private static ConcurrentHashMap<String, WebSocketSession> manager = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(SocketManager.class);
    public static void add(String key, WebSocketSession webSocketSession) {
        log.info("key = {},webSocketSession={}", key, FastJsonUtils.objectToJson(webSocketSession));
        manager.put(key, webSocketSession);
    }

    public static void remove(String key) {
        log.info("remove key = {}", key);
        manager.remove(key);
    }

    public static WebSocketSession get(String key) {
        log.info("get key = {}", key);
        return manager.get(key);
    }
}