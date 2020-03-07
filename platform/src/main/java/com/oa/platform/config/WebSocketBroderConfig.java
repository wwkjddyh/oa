package com.oa.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * STOMP协议
 * @author Feng
 * @date 2018/10/15
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBroderConfig implements WebSocketMessageBrokerConfigurer {

    /***
     * 注册 Stomp的端点
     * addEndpoint：添加STOMP协议的端点。提供WebSocket或SockJS客户端访问的地址
     * withSockJS：使用SockJS协议
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/v1/socket")
                .setAllowedOrigins("*")//添加允许跨域访问
                .withSockJS() ;
    }

    /**
     * 配置消息代理
     * 启动Broker，消息的发送的地址符合配置的前缀来的消息才发送到这个broker
     */@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/api/v1/socket/send","/user/");//推送消息前缀 /api/v1/socket/send 或 /user/
        registry.setApplicationDestinationPrefixes("/api/v1/socket/req");//应用请求前缀
        registry.setUserDestinationPrefix("/user");//推送用户前缀

//        registry.enableSimpleBroker("/topic","/user");
//        registry.setUserDestinationPrefix("/user/");
//        registry.setApplicationDestinationPrefixes("/app");//走@messageMapping
    }

}

