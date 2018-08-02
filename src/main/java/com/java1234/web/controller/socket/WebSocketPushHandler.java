package com.java1234.web.controller.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息处理类
 *
 * @author lt
 */
public class WebSocketPushHandler implements WebSocketHandler {

    private static final List<WebSocketSession> users = new ArrayList<>();

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(WebSocketPushHandler.class);

    /**
     * 字符串分隔符
     */
    private static final String SPLIT_SIGN = ",";

    /**
     * 当前webSocketUID
     */
    private static final String CURRENT_WEBSOCKET_USER = "uid";

    /**
     * 用户进入系统监听（建立webSocket连接时调用该方法）
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        Long uid = (Long) session.getAttributes().get(CURRENT_WEBSOCKET_USER);
        logger.info("【" + uid + "】成功进入了系统。。。");
        users.add(session);
    }

    /**
     * 发送信息前的处理
     * 将消息进行转化，因为是消息是json数据，可能里面包含了发送给某个人的信息，所以需要用json相关的工具类处理之后再封装成TextMessage
     * 消息的封装格式一般有{from:xxxx,to:xxxxx,msg:xxxxx}，来自哪里，发送给谁，什么消息等等
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        TextMessage msg = new TextMessage(message.getPayload().toString());
        //给所有用户群发消息
        sendMessagesToUsers(msg);
        //给指定用户群发消息
//        sendMessageToUser("2", msg);
    }

    /**
     * 后台错误信息处理方法
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * 用户退出后的处理，不如退出之后，要将用户信息从webSocket的session中remove掉，这样用户就处于离线状态了，也不会占用系统资源
     *
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        users.remove(session);
        logger.info(session.getAttributes().get(CURRENT_WEBSOCKET_USER) + "安全退出了系统");

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
                //isOpen()在线就发送
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
            if (user.getAttributes().get(CURRENT_WEBSOCKET_USER).toString().equals(userId)) {
                try {
                    //isOpen()在线就发送
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