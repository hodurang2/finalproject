package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

public interface MypageService {

  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request);  // 회원 정보 수정
  
  public ResponseEntity<Map<String, Object>> modifyIntroduction(HttpServletRequest request);  // 소개글 수정
  
  public void modifyPw(HttpServletRequest request, HttpServletResponse response);   // 비밀번호 변경

}
