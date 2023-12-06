package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.service.AuctionService2;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auction2")
@RequiredArgsConstructor
@Controller
public class AuctionController2 {
  private final AuctionService2 auctionService2;
  
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
    return auctionService2.getAuctionList(request);
  }
  
  @GetMapping("/detail.do")
  public String detail(HttpServletRequest request, Model model) {
    auctionService2.loadAuction(request, model);
    return "auction/detail";
  } 
  
  @GetMapping("/edit.form")
  public String edit(HttpServletRequest request, Model model) {
    auctionService2.loadAuction(request, model);
    return "auction/edit";
  }
  
  @ResponseBody
  @PostMapping(value="/controlWishlist.do", produces="application/json")
  public Map<String, Object> controlAuctionWishlist (HttpServletRequest request){
    return auctionService2.controlAuctionWishlist(request);
  }
  
  @GetMapping(value="/wishCheck.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> wishCheck(HttpServletRequest request) {
    return auctionService2.hasAuctionWishlist(request);
  }
  
  @GetMapping(value="/checkBidCount.do", produces="application/json")
  public Map<String, Object> getBidCount (int auctionNo) {
    return auctionService2.getBidCount(auctionNo);
  }
  
}
