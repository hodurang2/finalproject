package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface AuctionService {
  public Map<String, Object> getAuctionList(HttpServletRequest request);
  public Map<String, Object> controlAuctionWishlist(HttpServletRequest request);
  public Map<String, Object> getSearchAuctionList(HttpServletRequest request);
  public boolean addAuction(MultipartHttpServletRequest multipartRequest) throws Exception;
}
