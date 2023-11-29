package com.gdu.drawauction.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.drawauction.dao.UserMapper;
import com.gdu.drawauction.dto.InactiveUserDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyJavaMailUtils;
import com.gdu.drawauction.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  
  private final UserMapper userMapper;
  private final MySecurityUtils mySecurityUtils;
  private final MyJavaMailUtils myJavaMailUtils;
  
  private final String client_id = "dxLQ_GbhqsM3QHNFLIB3";
  private final String client_secret = "CsMJ8FIn4F";
  
  @Override
  public UserDto getUser(String email) {
    return userMapper.getUser(Map.of("email", email));
  }
  
  @Override
  public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
  
	  String email = request.getParameter("email");
	  String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
  
	  Map<String , Object> map = Map.of("email", email
			  							, "pw", pw);
	  
	  HttpSession session = request.getSession();
	  
	  // 휴면 계정인지 확인하기.
	  InactiveUserDto inactiveUser = userMapper.getInactiveUser(map);
	  if(inactiveUser != null) {
		  session.setAttribute("inactiveUser", inactiveUser);
		  response.sendRedirect(request.getContextPath() + "/user/active.form");		// 여쭤보기
	  }
	  
	  // 정상적인 로그인 처리
	  UserDto user = userMapper.getUser(map);
	  
	  if(user != null) {
		  request.getSession().setAttribute("user", user);
		  userMapper.insertAccess(email);
		  response.sendRedirect(request.getParameter("referer"));
	  } else {
	      response.setContentType("text/html; charset=UTF-8");
	      PrintWriter out = response.getWriter();
	      out.println("<script>");
	      out.println("alert('일치하는 회원 정보가 없습니다.')");
	      out.println("location.href='" + request.getContextPath() + "/main.do'");		// 여쭤보기
	      out.println("</script>");
	      out.flush();
	      out.close();
	  }
	  
  }
  
  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    
    HttpSession session = request.getSession();
    
    session.invalidate();
    
    try {
      response.sendRedirect(request.getContextPath() + "/main.do");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 이메일 인증
  @Transactional(readOnly=true)
  @Override
  public ResponseEntity<Map<String, Object>> checkEmail(String email) {
    
    Map<String, Object> map = Map.of("email", email);
    
    boolean enableEmail = userMapper.getUser(map) == null
                       && userMapper.getInactiveUser(map) == null;
    
    return new ResponseEntity<>(Map.of("enableEmail", enableEmail), HttpStatus.OK);
  }
  
  // 인증코드보내기
  @Override
  public ResponseEntity<Map<String, Object>> sendCode(String email) {
    
    // RandomString 생성(6자리, 문자 사용, 숫자 사용)
    String code = mySecurityUtils.getRandomString(6, true, true);
    
    // 메일 전송
    myJavaMailUtils.sendJavaMail(email
                               , "들어옥션 인증 코드"
                               , "<div>인증코드는 <strong>" + code + "</strong>입니다.</div>");
    
    return new ResponseEntity<>(Map.of("code", code), HttpStatus.OK);
    
  }
  
  // 회원가입하기
  @Override
  public void join(HttpServletRequest request, HttpServletResponse response) {
    
    String email = request.getParameter("email");
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    String name = mySecurityUtils.preventXSS(request.getParameter("name"));
    
    String mobile = request.getParameter("mobile");
    String gender = request.getParameter("gender");
    
    String postcode = request.getParameter("postcode");
    String roadAddress = request.getParameter("roadAddress");
    String jibunAddress = request.getParameter("jibunAddress");
    String detailAddress = mySecurityUtils.preventXSS(request.getParameter("detailAddress"));
    
    String nickname = request.getParameter("nickname");
    String introduction = request.getParameter("introduction");
    String event = request.getParameter("event");
    
    UserDto user = UserDto.builder()
    			   .email(email)
    			   .pw(pw)
    			   .name(name)
    			   .mobile(mobile)
    			   .gender(gender)
    			   .postcode(postcode)
    			   .roadAddress(roadAddress)
    			   .jibunAddress(jibunAddress)
    			   .detailAddress(detailAddress)
    			   .agree(event.equals("on") ? 1 : 0)
    			   .nickname(nickname)
    			   .introduction(introduction)
    			   .build();    			   
    
    int joinResult = userMapper.insertUser(user);
    
try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(joinResult == 1) {
        request.getSession().setAttribute("user", userMapper.getUser(Map.of("email", email)));
        userMapper.insertAccess(email);
        out.println("alert('회원 가입되었습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
      } else {
        out.println("alert('회원 가입이 실패했습니다.')");
        out.println("history.go(-2)");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
                    
    
  }

  // 아이디찾기
  @Override
  public UserDto findId(UserDto user) {
    return userMapper.findId(user);
  }
  
  // 비밀번호찾기
  @Override
  public void findPw(UserDto user, HttpServletResponse response) throws Exception {
    
    response.setContentType("text/html;charset=utf-8");
    PrintWriter out = response.getWriter();
    
    // RandomString 생성(10자리, 문자 사용, 숫자 사용) -- 임시 비밀번호
    String temporaryPw = mySecurityUtils.getRandomString(10, true, true);
    // 생성된 임시 비밀번호 암호화 처리
    String temporarySHAPw = mySecurityUtils.getSHA256(temporaryPw);
    
    int pwCheckResult = userMapper.findPwCheck(user);  // 1 or 0
    String email = user.getEmail();
    String name = user.getName();
    
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("email", email);
    map.put("name", name);
    map.put("pw", temporarySHAPw);
    
    if(pwCheckResult == 1) {
      userMapper.updatePw(map);
      myJavaMailUtils.sendJavaMail(email
          , "들어옥션 임시 비밀번호발급"
          , "<div>임시 비밀번호는 <strong>" + temporaryPw + "</strong>입니다. <h2 style='color: crimson;'>* 로그인 후 비밀번호를 변경해주세요 *</h2></div>");
      out.print(email + "로 임시 비밀번호가 전송되었습니다. 로그인 후 비밀번호를 변경해주세요.");
      out.close();
    } else {
      out.print("잘못된 정보입니다. 정보를 다시 입력하세요." );
      out.close();
    }
    
  }

  // 네이버 로그인 URL
  @Override
  public String getNaverLoginURL(HttpServletRequest request) throws Exception {
    
    // 네이버로그인-1
    // 네이버 로그인 연동 URL 생성하기를 위해 redirect_uri(URLEncoder), state(SecureRandom) 값의 전달이 필요하다.
    // redirect_uri : 네이버로그인-2를 처리할 서버 경로를 작성한다.
    // redirect_uri 값은 네이버 로그인 Callback URL에도 동일하게 등록해야 한다.
    
    String apiURL = "https://nid.naver.com/oauth2.0/authorize";
    String response_type = "code";
    String redirect_uri = URLEncoder.encode("http://localhost:8080" + request.getContextPath() + "/user/naver/getAccessToken.do", "UTF-8");
    String state = new BigInteger(130, new SecureRandom()).toString();
  
    StringBuilder sb = new StringBuilder();
    sb.append(apiURL);
    sb.append("?response_type=").append(response_type);
    sb.append("&client_id=").append(client_id);
    sb.append("&redirect_uri=").append(redirect_uri);
    sb.append("&state=").append(state);
    
    return sb.toString();
    
  }

  // 네이버 로그인 인증 토큰
  @Override
  public String getNaverLoginAccessToken(HttpServletRequest request) throws Exception {
    
    // 네이버로그인-2
    // 접근 토큰 발급 요청
    // 네이버로그인-2를 수행하기 위해서는 네이버로그인-1의 응답 결과인 code와 state가 필요하다.
    
    // 네이버로그인-1의 응답 결과(access_token을 얻기 위해 요청 파라미터로 사용해야 함)
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    
    String apiURL = "https://nid.naver.com/oauth2.0/token";
    String grant_type = "authorization_code";  // access_token 발급 받을 때 사용하는 값(갱신이나 삭제시에는 다른 값을 사용함)
    
    StringBuilder sb = new StringBuilder();
    sb.append(apiURL);
    sb.append("?grant_type=").append(grant_type);
    sb.append("&client_id=").append(client_id);
    sb.append("&client_secret=").append(client_secret);
    sb.append("&code=").append(code);
    sb.append("&state=").append(state);
    
    // 요청
    URL url = new URL(sb.toString());
    HttpURLConnection con = (HttpURLConnection)url.openConnection();
    con.setRequestMethod("GET");  // 반드시 대문자로 작성
    
    // 응답
    BufferedReader reader = null;
    int responseCode = con.getResponseCode();
    if(responseCode == 200) {
      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else {
      reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
    }
    
    String line = null;
    StringBuilder responseBody = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      responseBody.append(line);
    }
    
    JSONObject obj = new JSONObject(responseBody.toString());
    return obj.getString("access_token");
    
  }

  // 네이버 사용자 프로필 정보 받아오기
  @Override
  public UserDto getNaverProfile(String accessToken) throws Exception {
    
    // 네이버 로그인-3
    // 접근 토큰을 전달한 뒤 사용자의 프로필 정보(이름, 이메일, 성별, 휴대전화번호) 받아오기
    // 요청 헤더에 Authorization: Bearer accessToken 정보를 저장하고 요청함
    
    // 요청
    String apiURL = "https://openapi.naver.com/v1/nid/me";
    URL url = new URL(apiURL);
    HttpURLConnection con = (HttpURLConnection)url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Authorization", "Bearer " + accessToken);
    
    // 응답
    BufferedReader reader = null;
    int responseCode = con.getResponseCode();
    if(responseCode == 200) {
      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else {
      reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
    }
    
    String line = null;
    StringBuilder responseBody = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      responseBody.append(line);
    }
    
    // 응답 결과(프로필을 JSON으로 응답) -> UserDto 객체
    JSONObject obj = new JSONObject(responseBody.toString());
    JSONObject response = obj.getJSONObject("response");
    UserDto user = UserDto.builder()
                    .email(response.getString("email"))
                    .name(response.getString("name"))
                    .gender(response.getString("gender"))
                    .build();
    
    return user;
    
  }

  // 네이버 회원가입
  @Override
  public void naverJoin(HttpServletRequest request, HttpServletResponse response) {
    
  	String email = request.getParameter("email");
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    String name = mySecurityUtils.preventXSS(request.getParameter("name"));
    String mobile = request.getParameter("mobile");
    String gender = request.getParameter("gender");
    String postcode = request.getParameter("postcode");
    String roadAddress = request.getParameter("roadAddress");
    String jibunAddress = request.getParameter("jibunAddress");
    String detailAddress = request.getParameter("detailAddress");
    String nickname = request.getParameter("nickname");
    String introduction = request.getParameter("introduction");
    String event = request.getParameter("event");
  
    UserDto user = UserDto.builder()
				   .email(email)
				   .pw(pw)
				   .name(name)
				   .mobile(mobile.replace("-", ""))
				   .gender(gender)
				   .agree(event != null ? 1 : 0)
				   .postcode(postcode)
				   .roadAddress(roadAddress)
				   .jibunAddress(jibunAddress)
				   .detailAddress(detailAddress)
				   .nickname(nickname)
				   .introduction(introduction)
				   .build();   

    int naverJoinResult = userMapper.insertNaverUser(user);
    
    try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(naverJoinResult == 1) {
        request.getSession().setAttribute("user", userMapper.getUser(Map.of("email", email)));
        userMapper.insertAccess(email);
        out.println("alert('네이버 간편가입이 완료되었습니다.')");
      } else {
        out.println("alert('네이버 간편가입이 실패했습니다.')");
      }
      out.println("location.href='" + request.getContextPath() + "/main.do'");
      out.println("</script>");
      out.flush();
      out.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  // 네이버 로그인
  @Override
  public void naverLogin(HttpServletRequest request, HttpServletResponse response, UserDto naveProfile) throws Exception {
    
    String email = naveProfile.getEmail();
    UserDto user = userMapper.getUser(Map.of("email", email));
    
    if(user != null) {
      request.getSession().setAttribute("user", user);
      userMapper.insertAccess(email);
    } else {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('일치하는 회원 정보가 없습니다.')");
      out.println("location.href='" + request.getContextPath() + "/main.do'");
      out.println("</script>");
      out.flush();
      out.close();
    }
  }
  
  // 탈퇴
  @Override
  public void leave(HttpServletRequest request, HttpServletResponse response) {
    
	  Optional<String> opt = Optional.ofNullable(request.getParameter("userNo"));
    int userNo = Integer.parseInt(opt.orElse("0"));
    
    UserDto user = userMapper.getUser(Map.of("userNo", userNo));
    
    if(user == null) {
      try {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('회원 탈퇴를 수행할 수 없습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
        out.println("</script>");
        out.flush();
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    int insertLeaveUserResult = userMapper.insertLeaveUser(user);
    int deleteUserResult = userMapper.deleteUser(user);
    
   try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertLeaveUserResult == 1 && deleteUserResult == 1) {
        HttpSession session = request.getSession();
        session.invalidate();
        out.println("alert('회원 탈퇴되었습니다. 그 동안 이용해 주셔서 감사합니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
      } else {
        out.println("alert('회원 탈퇴되지 않았습니다.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  // 휴면계정
  @Override
  public void active(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
  
    InactiveUserDto inactiveUser = (InactiveUserDto)session.getAttribute("inactiveUser");
    String email = inactiveUser.getEmail();
    
    int insertActiveUserResult = userMapper.insertActiveUser(email);
    int deleteInactiveUserResult = userMapper.deleteInactiveUser(email);
    
    try {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertActiveUserResult == 1 && deleteInactiveUserResult == 1) {
        out.println("alert('휴면계정이 복구되었습니다. 계정 활성화를 위해서 곧바로 로그인 해 주세요.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");  // 로그인 페이지로 보내면 로그인 후 다시 휴면 계정 복구 페이지로 돌아오므로 main으로 이동한다.
      } else {
        out.println("alert('휴면계정이 복구가 실패했습니다. 다시 시도하세요.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public void inactiveUserBatch() {
    userMapper.insertInactiveUser();
    userMapper.deleteUserForInactive();
  }

  
  
}


