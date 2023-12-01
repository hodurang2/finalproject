package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    System.out.println("컨트롤러 서비스전");
    auctionService2.loadAuction(request, model);
    System.out.println("컨트롤러 서비스전후");
    return "auction/detail";
  }
  
}
