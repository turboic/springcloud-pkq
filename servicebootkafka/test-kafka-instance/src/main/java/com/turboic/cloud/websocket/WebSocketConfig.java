package com.turboic.cloud.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final String [] DEFAULT_SOCKET_URL = { "/server", "/client", "/browser", };


    private final PrincipalHandshakeHandler principalHandshakeHandler;

    public WebSocketConfig(PrincipalHandshakeHandler principalHandshakeHandler) {
        this.principalHandshakeHandler = principalHandshakeHandler;
    }

    /***
     *
     * 注册消息传输的终端
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(DEFAULT_SOCKET_URL).setAllowedOrigins("*").
                setHandshakeHandler(principalHandshakeHandler).addInterceptors().withSockJS()
                .setSupressCors(true)
                .setDisconnectDelay(3000l)
                .setWebSocketEnabled(true);

    }

    /**
     * webSocket传输配置
     * @param registry
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setSendTimeLimit(600000)
                .setSendBufferSizeLimit(102400000)
                .setMessageSizeLimit(102400000);
    }

    /**
     * 配置到达通道
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        ThreadPoolTaskExecutor InboundTaskExecutor = new ThreadPoolTaskExecutor();
        InboundTaskExecutor.setAllowCoreThreadTimeOut(true);
        InboundTaskExecutor.setCorePoolSize(10);
        InboundTaskExecutor.setKeepAliveSeconds(3000);
        InboundTaskExecutor.setMaxPoolSize(20);
        InboundTaskExecutor.setQueueCapacity(100);
        InboundTaskExecutor.setBeanName("-taskExecutor-InboundTaskExecutor");
        InboundTaskExecutor.setThreadPriority(1);
        InboundTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        InboundTaskExecutor.setThreadGroupName("InboundTaskExecutor");
        registration.taskExecutor(InboundTaskExecutor);
    }


    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        ThreadPoolTaskExecutor outboundTaskExecutor = new ThreadPoolTaskExecutor();
        outboundTaskExecutor.setAllowCoreThreadTimeOut(true);
        outboundTaskExecutor.setCorePoolSize(10);
        outboundTaskExecutor.setKeepAliveSeconds(3000);
        outboundTaskExecutor.setMaxPoolSize(20);
        outboundTaskExecutor.setQueueCapacity(100);
        outboundTaskExecutor.setBeanName("-taskExecutor-Outbound");
        outboundTaskExecutor.setThreadPriority(1);
        outboundTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        outboundTaskExecutor.setThreadGroupName("Outbound");
        registration.taskExecutor(outboundTaskExecutor);

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return true;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.configureBrokerChannel();
    }
}
