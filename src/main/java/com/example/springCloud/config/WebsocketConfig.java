package com.example.springCloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Component
//@EnableWebSocket
public class WebsocketConfig  {
//    @Autowired
//    private WebSocketServer webSocketServer;
//    @Autowired
//    private WebsocketInterceptor websocketInterceptor;

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketServer, "/ws").setAllowedOrigins("*").addInterceptors(websocketInterceptor);
//    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

