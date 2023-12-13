package com.gdu.drawauction.chat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdu.drawauction.config.ServerEndpointConfigurator;
import com.gdu.drawauction.dto.BidDto;
import com.gdu.drawauction.service.AuctionService2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@ServerEndpoint(value="/auction/{auctionNo}",  configurator = ServerEndpointConfigurator.class)
public class WebSocketAuction {
  
  private static Map<String, Set<Session>> auctionRooms = Collections.synchronizedMap(new HashMap<>());
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final AuctionService2 auctionService2;  
  
  @OnOpen
  public void onOpen(Session s, @PathParam("auctionNo") String auctionNo) {
    System.out.println("open session : " + s.toString());
    
    auctionRooms.computeIfAbsent(auctionNo, k -> Collections.synchronizedSet(new HashSet<>())).add(s);
    int bidPrice = auctionService2.getBidPrice(Integer.parseInt(auctionNo));
    System.out.println("bidPric웹소켓" + bidPrice);
    sendBidPriceToSession(s, bidPrice);
    System.out.println("session open : " + s);
  }
  
  private void sendBidPriceToSession(Session session, int bidPrice) {
    try {
      // Create a BidDto to represent the bidPrice
      BidDto bidDto = new BidDto();
      bidDto.setBidPrice(bidPrice);

      // BidDto를 JSON 문자열로 변환
      String bidDtoJson = objectMapper.writeValueAsString(bidDto);

      // Send the message to the session
      session.getBasicRemote().sendText(bidDtoJson);

      System.out.println("Sent bidPrice to session : " + session);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @OnMessage
  public void onMessage(String msg, Session session) throws Exception {
    
    try {
      System.out.println("받은 메시지 : " + msg);
      BidDto bidDto = objectMapper.readValue(msg, BidDto.class);
      auctionService2.addBid(bidDto);
      int auctionNo1 = bidDto.getAuctionDto().getAuctionNo();
      System.out.println("auctionNo" + auctionNo1);
      int bidPrice = auctionService2.getBidPrice(auctionNo1);
      System.out.println("bidPric웹소켓" + bidPrice);
      bidDto.setBidPrice(bidPrice);

      // BidDto를 JSON 문자열로 변환
      String bidDtoJson = objectMapper.writeValueAsString(bidDto);
      // 같은 제품방에 있는 사람들모두에게 메세지를 보낸다.
      String auctionNo = "" + bidDto.getAuctionDto().getAuctionNo();
      Set<Session> roomSessions = auctionRooms.getOrDefault(auctionNo, Collections.emptySet());
      for (Session s : roomSessions) {
        System.out.println("보낸 메시지 : " + bidDtoJson);
        s.getBasicRemote().sendText(bidDtoJson);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  @OnClose
  public void onClose(Session s, @PathParam("auctionNo") String auctionNo) {
    System.out.println("session close : " + s);

    // 방의 세션 제거
    Set<Session> roomSessions = auctionRooms.getOrDefault(auctionNo, Collections.emptySet());
    roomSessions.remove(s);

    
  
  }
  
  @OnError
  public void error(Session session, Throwable t) {
     t.printStackTrace();
     
  }
  
}
