package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.drawauction.dto.BidDto;

public interface AuctionService2 {
  
  public Map<String, Object> getAuctionList(HttpServletRequest request);
  public void loadAuction(HttpServletRequest request, Model model); 
  public Map<String, Object> controlAuctionWishlist(HttpServletRequest request);
  public ResponseEntity<Map<String, Object>> hasAuctionWishlist(HttpServletRequest request);
  public Map<String, Object> getBidCount(int auctionNo);
  public int modifyAuction(HttpServletRequest request);
  public Map<String, Object> getImageList(HttpServletRequest request);
  public Map<String, Object> addImage(MultipartHttpServletRequest multipartRequest) throws Exception;
  public Map<String, Object> removeImage(HttpServletRequest request);
  public int removeAuction(int auctionNo);
  public void getEmoney(HttpServletRequest request, Model model);
  public int addMaxBid(HttpServletRequest request);
  public int addBid(HttpServletRequest request);
  
}
