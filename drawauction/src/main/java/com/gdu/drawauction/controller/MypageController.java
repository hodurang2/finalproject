package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  
  @GetMapping("/detail.do")
  public String detail() {
    return "mypage/detail";
  }
  
  @GetMapping("/modify.form")
  public String modifyUser() {
    return "mypage/modify";
  }
  
  @PostMapping(value="/modify.do", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request) {
    return mypageService.modify(request);
  }
  
  @PostMapping(value="/modifyIntroduction.do", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> modifyIntroduction(HttpServletRequest request) {
    return mypageService.modifyIntroduction(request);
  }
  
  @GetMapping("/auctionBidList.do")
  public String auctionBidList(HttpServletRequest request, Model model) {
    mypageService.loadAuctionBidList(request, model);
    return "mypage/auctionList";
  }
  
  @GetMapping("/myDrawList.do")
  public String myDrawLsit() {
    return "mypage/myDrawList";
  }
  
  @GetMapping("/modifyPw.form")
  public String modifyPw() {
    return "mypage/pw";
  }
  
  @PostMapping("/modifyPw.do")
  public void modifyPw(HttpServletRequest request, HttpServletResponse response) {
    mypageService.modifyPw(request, response);
  }
  
}
