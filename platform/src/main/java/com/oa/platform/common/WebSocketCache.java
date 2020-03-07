package com.oa.platform.common;

import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局:WebSocketCache
 * @author feng
 * @date 2018/10/15
 */
public class WebSocketCache {

    public static final String WEBSOCKET_USERNAME = "websocket_username";
    public static final String SESSION_USERNAME = "session_username";

    /**
     * 使用SESSION_KEY区分WebSocketHandler，以便定向发送消息
     */
    public static final String WEBSOCKET_SESSION_KEY = "websocket_key";

    /**
     * 用于保存用户会话信息
     * {key:登录名或登录名加sessionid；value：用户登录会话}
     */
    public static final ConcurrentHashMap<String, WebSocketSession> USER_SESSIONS = new ConcurrentHashMap<>();
}
