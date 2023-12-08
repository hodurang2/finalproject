package com.gdu.drawauction.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.gdu.drawauction.dto.UserDto;

public interface UserService {

	  public void login(HttpServletRequest request, HttpServletResponse response) throws Exception;
	  public void logout(HttpServletRequest request, HttpServletResponse response);
	  public UserDto getUser(String email);
	  
	  // 아이디/비밀번호 찾기
	  public List<UserDto> findId(UserDto user);
	  public void findPw(UserDto user, HttpServletResponse response) throws Exception;

	  // 네이버 로그인
	  public String getNaverLoginURL(HttpServletRequest request) throws Exception;
	  public String getNaverLoginAccessToken(HttpServletRequest request) throws Exception;
	  public UserDto getNaverProfile(String accessToken) throws Exception;
	  public void naverJoin(HttpServletRequest request, HttpServletResponse response);
	  public void naverLogin(HttpServletRequest request, HttpServletResponse response, UserDto naverProfile) throws Exception;

	  public ResponseEntity<Map<String, Object>> checkEmail(String email);
	  public ResponseEntity<Map<String, Object>> sendCode(String email);
	  public void join(HttpServletRequest request, HttpServletResponse response);
	  public void leave(HttpServletRequest request, HttpServletResponse response);
	  public void inactiveUserBatch();
	  public void active(HttpSession session, HttpServletRequest request, HttpServletResponse response);

	  // 입찰, 출품목록 가져오기
	  public void loadAuctionBidList(HttpServletRequest request, Model model);    // 입찰 목록
	  public void loadAuctionSalesList(HttpServletRequest request, Model model);  // 출품 목록

	  
	  // 카카오톡 로그인
	  public void kakaoJoin(HttpServletRequest request, HttpServletResponse response);
	  public void kakaoLogin(HttpServletRequest request, HttpServletResponse response, UserDto kakaoProfile) throws Exception;
	  
	  public String getKakaoLoginURL(HttpServletRequest request) throws Exception;
	  public String getKakaoLoginAccessToken(HttpServletRequest request) throws Exception;
	  public UserDto getKakaoProfile(String accesskakaoToken) throws Exception;
    public void chargeEmoney(int userNo, int amount);
	


	  
	  
}
