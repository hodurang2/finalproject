package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.drawauction.service.MypageService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class MypageController {

  private final MypageService mypageService;
  
  @GetMapping("/info.do")
  public String info() {
    return "mypage/info";
  }
  
  @GetMapping("/modify.form")
  public String modifyUser() {
    return "mypage/modify";
  }
  
  @PostMapping(value="/modify.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request) {
    return mypageService.modify(request);
  }
  
  @GetMapping("/auctionBidList.do")
  public String auctionBidList() {
    return "mypage/auctionBidList";
  }
  
  @GetMapping("/auctionSaleList.do")
  public String auctionSaleList() {
    return "mypage/auctionSaleList";
  }
  
  @GetMapping("/myDrawList.do")
  public String myDrawLsit() {
    return "/mypage/myDrawList";
  }
  
  @GetMapping("/menu.do")
  public String menu() {
    return "mypage/menu";
  }
  
  
}
