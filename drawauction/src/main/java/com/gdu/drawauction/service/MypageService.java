package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface MypageService {

  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request);  // 회원 정보 수정
  
  public ResponseEntity<Map<String, Object>> modifyIntroduction(HttpServletRequest request);  // 소개글 수정
  
  public void modifyPw(HttpServletRequest request, HttpServletResponse response);   // 비밀번호 변경

  public void getCount(HttpServletRequest request, Model model);   // 갯수
  
  public void getAuctionBidList(HttpServletRequest request, Model model);    // 입찰 목록
  
  public void getAuctionSalesList(HttpServletRequest request, Model model);  // 출품 목록
  
  public Map<String, Object> getMyDrawList(HttpServletRequest request);    // 나의 그려드림 목록

}
