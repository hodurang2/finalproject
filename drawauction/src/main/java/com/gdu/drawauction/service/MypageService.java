package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface MypageService {

  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request);  // 회원 정보 수정
  
}
