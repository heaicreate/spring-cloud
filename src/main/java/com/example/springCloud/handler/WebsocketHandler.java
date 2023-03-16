//package com.example.springCloud.handler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.*;
//import org.springframework.web.socket.client.WebSocketClient;
//import org.springframework.web.socket.client.WebSocketConnectionManager;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//@Slf4j
//public class WebsocketHandler implements WebSocketHandler {
//
//    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info("连接成功{}", session.getId());
//        SESSIONS.put(session.getId(), session);
//        log.info("当前在线人数{}", SESSIONS.size());
////        WebSocketClient client = new StandardWebSocketClient();
////        WebSocketHandler handler = new ServletWebSocketClientHandler();
////        WebSocketConnectionManager manager = new WebSocketConnectionManager(client, handler, uri);
////        manager.start();
//
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        log.info("接收消息{}", session.getId());
//        String msg = message.getPayload().toString();
//        log.info("消息{}", msg);
////        session.sendMessage(message);
//        sendMessageToAllUsers(session.getId(),session.getId() + ":" + msg);
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        log.error("连接出错{}", session.getId());
//        if (!session.isOpen()) {
//            SESSIONS.remove(session.getId());
//            session.close();
//        }
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//        log.error("关闭连接{}", session.getId());
//        if (!session.isOpen()) {
//            SESSIONS.remove(session.getId());
//            log.error("当前在线人数{}", SESSIONS.size());
//        }
//    }
//
//    @Override
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//
//    /**
//     * sendMessageToUser:发给指定用户
//     */
//    public void sendMessageToUser(String userId, String contents) {
//        WebSocketSession session = SESSIONS.get(userId);
//        if (session != null && session.isOpen()) {
//            try {
//                TextMessage message = new TextMessage(contents);
//                session.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * sendMessageToAllUsers:发给所有的用户
//     */
//    public void sendMessageToAllUsers(String userIdTo,String contents) {
//        Set<String> userIds = SESSIONS.keySet();
//        for (String userId : userIds) {
//            this.sendMessageToUser(userId, contents);
//            break;
//        }
//        this.sendMessageToUser(userIdTo, contents.replaceAll(userIdTo,""));
//    }
//}
