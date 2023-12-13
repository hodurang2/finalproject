package com.gdu.drawauction.chat;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdu.drawauction.config.ServerEndpointConfigurator;
import com.gdu.drawauction.dto.BidDto;
import com.gdu.drawauction.service.AuctionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@ServerEndpoint(value="/auctionList",  configurator = ServerEndpointConfigurator.class)
public class WebSocketAuctionList {
  
  private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final AuctionService auctionService;  
  
  @OnOpen
  public void onOpen(Session s) {
    System.out.println("open session : " + s.toString());
    if(!clients.contains(s)) {
      clients.add(s);
      System.out.println("session open : " + s);
    } else {
        System.out.println("이미 연결된 session 입니다.");
    }

    System.out.println("session open : " + s);
  }
 
  
  @OnMessage
  public void onMessage(String msg, Session session) throws Exception {
    System.out.println("List받은 메시지 : " + msg);
    JsonNode jsonNode = objectMapper.readTree(msg);
    if(jsonNode.get("page") == null) {
      for(Session s : clients) {
        System.out.println("보낸 메시지 : " + msg);
        s.getBasicRemote().sendText(msg);
      }
    } else {
      int page = jsonNode.get("page").asInt(); 
      List<BidDto> bidList = auctionService.getBidList(page); 
      String bidListJson = objectMapper.writeValueAsString(bidList);
      for(Session s : clients) {
        System.out.println("보낸 메시지 : " + bidListJson);
        s.getBasicRemote().sendText(bidListJson);
      }
    }
    
  }
  
  
  @OnClose
  public void onClose(Session s) {
    System.out.println("session close : " + s);

    // 방의 세션 제거
    System.out.println("session close : " + s);
    clients.remove(s);
  }
  
  @OnError
  public void error(Session session, Throwable t) {
     t.printStackTrace();
     
  }
  
}
