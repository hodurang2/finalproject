package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface MypageService {

  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request);  // 회원 정보 수정
  
  public void modifyPw(HttpServletRequest request, HttpServletResponse response);   // 비밀번호 변경
  
  public ResponseEntity<Map<String, Object>> modifyIntroduction(HttpServletRequest request);  // 소개글 수정

  public void leave(HttpServletRequest request, HttpServletResponse response);    // 회원 탈퇴
  
  //public Map<String, Object> addUserImage(MultipartHttpServletRequest multipartRequest) throws Exception;
  
 // public Map<String, Object> getProfileImage(HttpServletRequest request); // 프로필 이미지 가져오기
  
  
  public void getCount(HttpServletRequest request, Model model);   // 갯수
  
  public void getAuctionBidList(HttpServletRequest request, Model model);    // 입찰 목록
  
  public void getAuctionSalesList(HttpServletRequest request, Model model);  // 출품 목록
  
  public Map<String, Object> getMyDrawList(HttpServletRequest request);    // 나의 그려드림 목록

  public Map<String, Object> getDrawOrderList(HttpServletRequest request);    // 주문한 그려드림 목록
  
  public Map<String, Object> getDrawReceivedOrderList(HttpServletRequest request);    // 주문받은 그려드림 목록
 
  public void getEmoneyList(HttpServletRequest request, Model model);   // E-MONEY 내역
  
  public Map<String, Object> getAuctionWishList(HttpServletRequest request);    // 경매 찜 목록
  
  public Map<String, Object> controlAuctionWish(HttpServletRequest request);    // 경매 찜
  
  public Map<String, Object> getDrawWishList(HttpServletRequest request);    // 그려드림 찜 목록
  
  public Map<String, Object> controlDrawWish(HttpServletRequest request);    // 그려드림 찜
  
}
