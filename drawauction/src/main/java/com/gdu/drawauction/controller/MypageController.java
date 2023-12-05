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
import org.springframework.web.bind.annotation.ResponseBody;

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

  @GetMapping("/modifyPw.form")
  public String modifyPw() {
    return "mypage/pw";
  }
  
  @PostMapping("/modifyPw.do")
  public void modifyPw(HttpServletRequest request, HttpServletResponse response) {
    mypageService.modifyPw(request, response);
  }
  
  @PostMapping(value="/modifyIntroduction.do", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> modifyIntroduction(HttpServletRequest request) {
    return mypageService.modifyIntroduction(request);
  }
  
  
  @GetMapping("/getAuctionBidList.do")
  public String auctionBidList(HttpServletRequest request, Model model) {
    mypageService.getAuctionBidList(request, model);
    return "mypage/auctionBidList";
  }
  
  @GetMapping("/getAuctionSalesList.do")
  public String auctionSalesList(HttpServletRequest request, Model model) {
    mypageService.getAuctionSalesList(request, model);
    return "mypage/auctionSalesList";
  }
  
  @ResponseBody
  @GetMapping(value="/getMyDrawList.do", produces="application/json")
  public Map<String, Object> myDrawList(HttpServletRequest request){
    return mypageService.getMyDrawList(request);
  }
  
  @ResponseBody
  @GetMapping(value="/getDrawImageList.do", produces="application/json")
  public Map<String, Object> myDrawImageList(HttpServletRequest request) {
    return mypageService.getMyDrawImageList(request);
  }
  
  
}
