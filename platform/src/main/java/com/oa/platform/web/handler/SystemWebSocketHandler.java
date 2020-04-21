package com.oa.platform.web.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oa.platform.biz.MessageBiz;
import com.oa.platform.common.Constants;
import com.oa.platform.common.WebSocketCache;
import com.oa.platform.entity.Message;
import com.oa.platform.entity.UserMessage;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;

import static com.oa.platform.common.Constants.*;
import static com.oa.platform.common.WebSocketCache.USER_SESSIONS;

/**
 * WebSocket具体处理细节
 * @author Feng
 * @date 2018/10/15
 */
public class SystemWebSocketHandler implements WebSocketHandler {

    private static final Logger logger;

    private static final ArrayList<WebSocketSession> users;

    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    @Autowired
    private MessageBiz messageBiz;

    static {
        users = new ArrayList<>();
        logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
    }

    /**
     * 发送心跳信息
     */
    public static void sendHeartMsg() {
        Map<String,Object> info = new HashMap<>();
        info.put("code", 200);
        info.put("userCount", users.size());
        info.put("isHeart", true);  // 是否为心跳信息
        info.put("date", DateUtil.currDateFormat(null));
        info.put("msg", "你好,看看是不是还活着");
        TextMessage message = new TextMessage(JSON.toJSONString(info));
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
     * 断开连接之后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1)
            throws Exception {
        String userName = (String) session.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME);
        System.err.println("websocket ('" + userName + "') connection closed......");
        logger.debug("websocket ('" + userName + "') connection closed......");
        users.remove(session);

        USER_SESSIONS.remove(userName);
    }

    /**
     * 连接成功之后
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        String userName = (String) session.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME);
        logger.debug(session.getAttributes().toString());
        logger.debug(userName + " connect to the websocket success......");
        System.err.println(userName + " connect to the websocket success......");
        users.add(session);
        USER_SESSIONS.put(userName, session);
        if(userName!= null){
            //查询未读消息
//            int count = webSocketService.getUnReadNews((String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME));
//            sendMessageToUsers(session,new TextMessage("欢迎，"+userName + " 进入房间 "),true);
//            session.sendMessage(new TextMessage("共有<b style=\"color:blue\">"+users.size() + "</b>个用户在线"));

            // 查询系统中所有未收到信息，并逐条发送给用户；如果用户没有未收到信息，则读取最近10条信息，逐条发送给用户
        }
    }

    /**
     * 消息处理
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
//        sendMessageToUsers(session,message,false);
        String msgPayload = StringUtil.trim(message.getPayload().toString());
        try {
            if (!"".equals(msgPayload)) {
                Map<String, String> msg = gson.fromJson(msgPayload, Map.class);
                String type = StringUtil.trim(msg.get("t"), CHAT_MESSAGE_P2P); // 类型(1: 用户对用户)
                String to = StringUtil.trim(msg.get("to"));     // 接收者
                String content = StringUtil.trim(msg.get("msg"));   //内容

                if (CHAT_MESSAGE_P2P.equals(type)) {
                    if (!"".equals(to)) {
                        String userName = (String) session.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME);
                        sendMessageToUser(userName, to, content);
                    }
                }

            }
        }
        catch (Exception e) {
            logger.error("消息接收异常...... =>" + msgPayload);
        }
    }

    /**
     * 消息传输异常处理
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable arg1)
            throws Exception {
        String userName = (String) session.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME);
        if(session.isOpen()){
            session.close();
        }
        logger.error("websocket connection  error and closed...... ("+userName+"被断开了)",arg1);
        users.remove(session);
    }

    /**
     * 支持部分消息处理
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     * @param message
     */
    public void sendMessageToUsers(WebSocketMessage<?> message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                logger.error("send message to "+user.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME)+" error!",e);
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     * @param session
     * @param message
     * @param isJoin 是否为首次进入聊天室
     */
    public void sendMessageToUsers(WebSocketSession session, WebSocketMessage<?> message, boolean isJoin) {
        String userName = (String) session.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME);
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
//                	user.sendMessage(new TextMessage("用户'"+userName+"'说："));
//                    user.sendMessage(message);

                    /*消息内容*/
                    String msgPayload = message.getPayload().toString();

                    boolean isMe = false;
                    String toName = "",fromName="";
                    String toUserName = (String) user.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME);
                    String dateTime = DateUtil.currDateFormat(DateUtil.DEFAULT_FORMAT);
                    String msgContent = msgPayload+"<br/>&nbsp;&nbsp;"+ dateTime;
                    if(!userName.equals(toUserName)) {	//如果不是同一个人发言
                        msgContent = "<b style='color:red'>"+userName+"</b>："+msgContent;
                        toName = toUserName;
                        fromName = userName;
                    }
                    else {
                        msgContent = "<b style='color:red'>我</b>："+msgContent;
                        isMe = true;
                        toName = userName;
                        fromName = userName;
                    }
                    Map<String,String> info = new LinkedHashMap<>();
                    info.put("userCount", users.size()+"");
                    info.put("msg", msgPayload);
                    info.put("date", dateTime);
//                	info.put("isMe", (isMe) ? "1" : "0");
                    info.put("toName", toName);		/*消息接受者*/
                    info.put("fromName", fromName);	/*消息发送者*/
                    info.put("isJoin", (isJoin)? "1" : "0");
                    System.out.println(gson.toJson(info));
//                	user.sendMessage(new TextMessage(msgContent));
                    user.sendMessage(new TextMessage(gson.toJson(info)));
                }
            } catch (IOException e) {
                logger.error("send message to "+user.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME)+" error!",e);
            }
        }
    }

    /**
     * 给某个用户发送消息
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    logger.error("send message to "+user.getAttributes().get(WebSocketCache.WEBSOCKET_USERNAME)+" error!",e);
                }
                break;
            }
        }
    }

    /**
     * 发送消息(P2P)
     * @param from 消息发送者
     * @param to 消息接收者
     * @param message 消息体
     */
    public void sendMessageToUser(String from, String to, String message) {
        from = StringUtil.trim(from);
        to = StringUtil.trim(to);
        if (!"".equals(from) && !"".equals(to)) {
            String dateTime = DateUtil.currDateFormat(DateUtil.DEFAULT_FORMAT);
            Map<String,String> info = Maps.newLinkedHashMap();
            info.put("t", CHAT_MESSAGE_P2P);
            info.put("msg", message);
            info.put("date", dateTime);
            info.put("to", to);		/*消息接受者*/
            info.put("from", from);	/*消息发送者*/
            info.put("isMe", "1");  /*是自己发送的消息*/
            String msgGson = gson.toJson(info);

            String messageId = StringUtil.getRandomUUID();
            messageBiz.saveMessage("msg-platform-msg", from, to, msgGson, dateTime, messageId);

            List<UserMessage> userMessages = Lists.newArrayList();
            if (USER_SESSIONS.containsKey(from)) {
                WebSocketSession senderSession = USER_SESSIONS.get(from);
                if (senderSession.isOpen()) {   // 在线
                    try {
                        senderSession.sendMessage(new TextMessage(msgGson));
                        userMessages.add(new UserMessage(StringUtil.getRandomUUID(), from, messageId, CHAT_MESSAGE_STATUS_SEND_SUCC));
                        System.err.println("发收者'"+from+"' 在线......会将数据保存到数据库表中，记录信息为发送--->senderSession");
                    }
                    catch (Exception e) {
                        logger.error("send message to ' "+ from +"' error!",e);
                        userMessages.add(new UserMessage(StringUtil.getRandomUUID(), from, messageId, CHAT_MESSAGE_STATUS_SEND_FAIL));
                        System.err.println("发收者'"+from+"' 不在线......会将数据保存到数据库表中，记录信息为未发送--->1(发送异常)");
                    }
                }
                else {
                    userMessages.add(new UserMessage(StringUtil.getRandomUUID(), from, messageId, CHAT_MESSAGE_STATUS_SEND_FAIL));
                    System.err.println("发收者'"+from+"' 不在线......会将数据保存到数据库表中，记录信息为未发送--->1");
                }
            }
            else {
                userMessages.add(new UserMessage(StringUtil.getRandomUUID(), from, messageId, CHAT_MESSAGE_STATUS_SEND_FAIL));
                System.err.println("发收者'"+from+"' 不在线......会将数据保存到数据库表中，记录信息为未发送--->2");
            }

            info.put("isMe", "0");  /*不是自己发送的消息*/
            msgGson = gson.toJson(info);
            if (USER_SESSIONS.containsKey(to)) {   // 如果接收者在线，则发送
                WebSocketSession receiverSession = USER_SESSIONS.get(to);
                if (receiverSession.isOpen()) { // 在线
                    try {
                        receiverSession.sendMessage(new TextMessage(msgGson));
                        userMessages.add(new UserMessage(StringUtil.getRandomUUID(), from, messageId, CHAT_MESSAGE_STATUS_RECEIVE_SUCC));
                        System.err.println("接收者'"+to+"' 在线......会将数据保存到数据库表中，记录信息为发送---->receiverSession");
                    } catch (Exception e) {
                        logger.error("send message to '"+ to +"' error!",e);
                        userMessages.add(new UserMessage(StringUtil.getRandomUUID(), from, messageId, CHAT_MESSAGE_STATUS_RECEIVE_FAIL));
                        System.err.println("接收者'"+to+"' 不在线......会将数据保存到数据库表中，记录信息为未发送--->2(发送异常)");
                    }
                }
                else {
                    userMessages.add(new UserMessage(StringUtil.getRandomUUID(), from, messageId, CHAT_MESSAGE_STATUS_RECEIVE_FAIL));
                    System.err.println("接收者'"+to+"' 不在线......会将数据保存到数据库表中，记录信息为未发送--->002");
                }
            }
            else {  // 如果接收者不在线，则将信息保存
                userMessages.add(new UserMessage(StringUtil.getRandomUUID(), from, messageId, CHAT_MESSAGE_STATUS_RECEIVE_FAIL));
                System.err.println("接收者'"+to+"' 不在线......会将数据保存到数据库表中，记录信息为未发送--->002");
            }

            if (!userMessages.isEmpty()) {
                messageBiz.batchSaveUserMessage(userMessages);
            }
        }

    }
}
