package com.oa.platform.web.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebSocket推送处理器
 * @author Feng
 * @date 2018/10/15
 */
public class WebSocketPushHandler implements WebSocketHandler {

    private static final Gson gson = new Gson();

    private static final List<WebSocketSession> users = new ArrayList<>();

    // 用户退出后的处理，不如退出之后，要将用户信息从websocket的session中remove掉，这样用户就处于离线状态了，也不会占用系统资源
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        users.remove(session);
        System.err.println("安全退出了系统");
    }

    /**
     * 用户进入系统监听
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功进入了系统。。。");
        users.add(session);
        sendMessagesToUsers(new TextMessage("今天晚上服务器维护,请注意"));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 将消息进行转化，因为是消息是json数据，可能里面包含了发送给某个人的信息，所以需要用json相关的工具类处理之后再封装成TextMessage，
        // 我这儿并没有做处理，消息的封装格式一般有{from:xxxx,to:xxxxx,msg:xxxxx}，来自哪里，发送给谁，什么消息等等
        // TextMessage msg = (TextMessage)message.getPayload();
        // 给所有用户群发消息
        //sendMessagesToUsers(msg);
        // 给指定用户群发消息
        //sendMessageToUser(userId, msg);

        System.err.println("消息内容："+message.getPayload());

        String content = message.getPayload().toString();
        JSONObject json = JSON.parseObject(content);
        if(json.containsKey("name")) {
            String uName = json.getString("name");
            Map<String,Object> info = new HashMap<>();
            info.put("code", 200);
            info.put("err", "");
            info.put("msg", "你好,'"+uName+"'！");
            sendMessagesToUsers(new TextMessage(JSON.toJSONString(info)));
        }
        else if(json.containsKey("from")) {	/*解析收到的消息*/
            String uName = json.getString("from");
            String to = json.getString("to");
            String msg = json.getString("msg");
            Map<String,Object> info = new HashMap<>();
            info.put("code", 200);
            info.put("err", "");
            info.put("msg", msg);
            sendMessagesToUsers(new TextMessage(JSON.toJSONString(info)));
        }
    }

    // 后台错误信息处理方法
    @Override
    public void handleTransportError(WebSocketSession arg0, Throwable arg1) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有的用户发送消息
     */
    public void sendMessagesToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                // isOpen()在线就发送
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息给指定的用户
     */
    public void sendMessageToUser(String userId, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("").equals(userId)) {
                try {
                    // isOpen()在线就发送
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
