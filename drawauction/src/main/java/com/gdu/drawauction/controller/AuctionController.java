package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.drawauction.service.AuctionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auction")
@RequiredArgsConstructor
@Controller
public class AuctionController {
  private final AuctionService auctionService;
  
  @GetMapping("/list.do")
  public String list() {
    return "auction/list";
  }
  
  @GetMapping("/write.form")
  public String write() {
    return "auction/write";
  }
  
  @ResponseBody
  @GetMapping(value="/getList.do", produces="application/json")
  public Map<String, Object> getList(HttpServletRequest request){
    return auctionService.getAuctionList(request);
  }
  
  @ResponseBody
  @GetMapping(value="/addWish.do", produces="application/json")
  public void addAuctionWishlist (HttpServletRequest request){
    auctionService.addAuctionWishlist(request);
  }
  @ResponseBody
  @GetMapping(value="/removeWish.do", produces="application/json")
  public void removeAuctionWishlist (HttpServletRequest request){
    auctionService.removeAuctionWishlist(request);
  }
}
