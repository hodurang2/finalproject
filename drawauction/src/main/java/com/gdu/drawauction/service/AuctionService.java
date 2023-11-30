package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AuctionService {
  public Map<String, Object> getAuctionList(HttpServletRequest request);
  public void addAuctionWishlist(HttpServletRequest request);
  public void removeAuctionWishlist(HttpServletRequest request);
}
