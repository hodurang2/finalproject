package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface AuctionService2 {
  public Map<String, Object> getAuctionList(HttpServletRequest request);
  public void loadAuction(HttpServletRequest request, Model model); 
  public Map<String, Object> controlAuctionWishlist(HttpServletRequest request);
  public ResponseEntity<Map<String, Object>> hasAuctionWishlist(HttpServletRequest request);
  public Map<String, Object> getBidCount(int auctionNo);
  
  
}
