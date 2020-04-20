package com.oa.platform.web.interceptor;

import com.oa.platform.common.WebSocketCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import static com.oa.platform.common.WebSocketCache.USER_SESSIONS;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * WebSocket握手拦截器
 * @author Feng
 * @date 2018/10/15
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private static Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);

    /**
     * 在握手之后
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object
            > attributes) throws Exception {
        logger.debug("beforeHandshake start.....");
        logger.debug(request.getClass().getName());
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
                String userName = (String) session.getAttribute(WebSocketCache.SESSION_USERNAME);
                logger.info(userName+" login");
                attributes.put(WebSocketCache.WEBSOCKET_USERNAME, userName);

                /*使用websocketSessionKey区分WebSocketHandler  modify by feng*/
                String websocketSessionKey = userName + ";" + session.getId();
                attributes.put(WebSocketCache.WEBSOCKET_SESSION_KEY, websocketSessionKey);

                System.err.println("connect success");
            }else{
                logger.debug("httpsession is null");
            }
        }
        return true;
    }

    /**
     * 在握手之前
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
