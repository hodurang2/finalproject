package com.gdu.drawauction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
      return new ServerEndpointExporter();
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    // TODO Auto-generated method stub
    
  }
  
}
