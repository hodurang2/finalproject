package com.gdu.drawauction.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/user")
@RequiredArgsConstructor
@Controller
public class UserController {
  
  private final UserService userService;  
 
  @GetMapping("/login.form")
  public String loginForm(HttpServletRequest request, Model model) throws Exception {
    // referer : 이전 주소가 저장되는 요청 Header 값
    String referer = request.getHeader("referer");
    String[] exceptUrl = {"/agree.form", "/join.form", "/join_option.form", "/find_id.form", "/find_pw.form", "/artistProfile.form"};
    String ret = "";
    if(referer != null) {
      for(String url : exceptUrl) {
        if(referer.contains(url)) {
          ret = request.getContextPath() + "/main.do" ; 
        }
      }
    } else {
      ret = request.getContextPath() + "/main.do" ;
    }
    
    model.addAttribute("referer", ret.isEmpty() ? referer : ret);
    // 네이버로그인-1
    model.addAttribute("naverLoginURL", userService.getNaverLoginURL(request));
 // 카카오로그인 -1
    model.addAttribute("kakaoLoginURL", userService.getKakaoLoginURL(request));
    return "user/login";
  }
  // 카카오톡 로그인-2  
  @GetMapping("/kakao/getKakaoAccessToken.do")
  public String getKakaoAccessToken(HttpServletRequest request) throws Exception {
	  String accessToken  = userService.getKakaoLoginAccessToken(request);
	  return "redirect:/user/kakao/getKakaoProfile.do?accessToken=" + accessToken;
  }
  
  // 카카오톡 로그인 -3 
  @GetMapping("/kakao/getKakaoProfile.do")
  public String getKakaoProfile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
	  
	  UserDto kakaoProfile = userService.getKakaoProfile(request.getParameter("accessToken"));
	  UserDto user = userService.getUser(kakaoProfile.getEmail());
	  if(user == null) {
		  model.addAttribute("kakaoProfile", kakaoProfile);
		  return "user/kakao_join";
	  } else {
		  userService.kakaoLogin(request, response, kakaoProfile);
	      return "redirect:/main.do";
	  }
	  
  }
  
  // 카카오 간편로그인
  @PostMapping("/kakao/join.do")
  public void kakaoJoin(HttpServletRequest request, HttpServletResponse response) {
	  userService.kakaoJoin(request, response);
  }
  
  
  @GetMapping("/naver/getAccessToken.do")
  public String getAccessToken(HttpServletRequest request) throws Exception {
    // 네이버로그인-2
    String accessToken = userService.getNaverLoginAccessToken(request);
    return "redirect:/user/naver/getProfile.do?accessToken=" + accessToken;
  }
  
  @GetMapping("/naver/getProfile.do")
  public String getProfile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    // 네이버로그인-3
    UserDto naverProfile = userService.getNaverProfile(request.getParameter("accessToken"));
    // 네이버로그인 후속 작업(처음 시도 : 간편가입, 이미 가입 : 로그인)
    UserDto user = userService.getUser(naverProfile.getEmail());
    if(user == null) {
      // 네이버 간편가입 페이지로 이동
      model.addAttribute("naverProfile", naverProfile);
      return "user/naver_join";
    } else {
      // naverProfile로 로그인 처리하기
      userService.naverLogin(request, response, naverProfile);
      return "redirect:/main.do";
    }
  }
  
  // 네이버 간편가입
  @PostMapping("/naver/join.do")
  public void naverJoin(HttpServletRequest request, HttpServletResponse response) {
    userService.naverJoin(request, response);
  }

  
  
  @PostMapping("/login.do")
  public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    userService.login(request, response);
  }
  
  @GetMapping("/logout.do")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    userService.logout(request, response);
  }
  
  @GetMapping("/agree.form")
  public String agreeForm() {
    return "user/agree";
  }
  
  @GetMapping("/artistProfile.form")
  public String artistProfileForm() {
    return "user/artistProfile";
  }
  
  @GetMapping("/join.form")
  public String joinForm(@RequestParam(value="service", required=false, defaultValue="off") String service
                       , @RequestParam(value="event", required=false, defaultValue="off") String event
                       , Model model) {
    String rtn = null;
    if(service.equals("off")) {
      rtn = "redirect:/main.do";
    } else {
      model.addAttribute("event", event);  // user 폴더 join.jsp로 전달하는 event는 "on" 또는 "off" 값을 가진다.
      rtn = "user/join";
    }
    return rtn;
  }
  
  @GetMapping(value="/checkEmail.do", produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam String email) {
    return userService.checkEmail(email);
  }
  
  @GetMapping(value="/sendCode.do", produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> sendCode(@RequestParam String email) {
    return userService.sendCode(email);
  }
  
  @PostMapping("/join.do")
  public void join(HttpServletRequest request, HttpServletResponse response) {
    userService.join(request, response);
  }
  
  @GetMapping("/mypage.form")
  public String mypageForm() {
    return "user/mypage";
  }

  
  @PostMapping("/leave.do")
  public void leave(HttpServletRequest request, HttpServletResponse response) {
    userService.leave(request, response);
  }
  
  @GetMapping("/active.form")
  public String activeForm() {
    return "user/active";
  }
  
  @GetMapping("/active.do")
  public void active(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
	  userService.active(session, request, response);
  }

  @GetMapping("/find.form")
  public String findForm() {
    return "user/find";
  }
  
//  @PostMapping("/findId.do")
//  public String findId(HttpServletRequest request, Model model, UserDto user) {
//    
//	user.setName(request.getParameter("name"));
//	user.setMobile(request.getParameter("mobile"));
//    UserDto findId = userService.findId(user);
//    
//    model.addAttribute("findId", findId);
//    
//    return "user/find_id";
//  }
//  
  
  @PostMapping("/findId.do")
  public String findId(HttpServletRequest request, Model model, UserDto user) {
      user.setName(request.getParameter("name"));
      List<UserDto> findIds = userService.findId(user);
      model.addAttribute("findIds", findIds);
      return "user/find_id";
  }
  
  @PostMapping("/findPw.do")
  public void findPw(HttpServletRequest request, HttpServletResponse response, UserDto user) throws Exception {
    
	    user.setEmail(request.getParameter("email"));
	    user.setName(request.getParameter("name"));
	    user.setMobile(request.getParameter("mobile"));
	    userService.findPw(user, response);
  }

  @GetMapping("/chargeEmoney.do")
  public void chargeEmoney(int userNo, int amount) {
    userService.chargeEmoney(userNo, amount);
  }
  
  
}