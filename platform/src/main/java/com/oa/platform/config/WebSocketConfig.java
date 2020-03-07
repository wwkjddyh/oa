package com.oa.platform.config;

import com.oa.platform.web.handler.SystemWebSocketHandler;
import com.oa.platform.web.handler.WebSocketPushHandler;
import com.oa.platform.web.interceptor.PlatformWebSocketInterceptor;
import com.oa.platform.web.interceptor.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebMvc适配器配置
 * @author Feng
 * @date 2018/10/15
 */
/*@Configuration
@EnableWebMvc
@EnableWebSocket*/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        /**
         * registry.addHandler(systemWebSocketHandler(),"/api/socket/server").addInterceptors(new WebSocketHandshakeInterceptor()).setAllowedOrigins("*");
         * setAllowedOrigins(“*”)中的*代表合法的请求域名,该方法接受一个可变数组作为参数,一定要配置,不然会请求时会出现403
         */
        registry.addHandler(systemWebSocketHandler(),"/api/socket/server").addInterceptors(new WebSocketHandshakeInterceptor()).setAllowedOrigins("*");
        registry.addHandler(systemWebSocketHandler(), "/api/socket/sockjs/server").addInterceptors(new WebSocketHandshakeInterceptor()).setAllowedOrigins("*").withSockJS();

        registry.addHandler(wechatSocketHandler(),"/api/socket/wechat").addInterceptors(new PlatformWebSocketInterceptor()).setAllowedOrigins("*");
        registry.addHandler(wechatSocketHandler(), "/api/socket/sockjs/wechat").addInterceptors(new PlatformWebSocketInterceptor()).setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public WebSocketHandler systemWebSocketHandler(){
        return new SystemWebSocketHandler();
    }

    @Bean
    public WebSocketPushHandler wechatSocketHandler() {
        return new WebSocketPushHandler();
    }
}
