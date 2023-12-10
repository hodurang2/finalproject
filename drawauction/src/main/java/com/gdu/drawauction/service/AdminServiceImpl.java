package com.gdu.drawauction.service;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.drawauction.dao.AdminMapper;
import com.gdu.drawauction.dto.AdminDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyJavaMailUtils;
import com.gdu.drawauction.util.MyPageUtils;
import com.gdu.drawauction.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminMapper adminMapper;
	
	
	private final MySecurityUtils mySecurityUtils;
	private final MyJavaMailUtils myJavaMailUtils;
	private final MyPageUtils myPageUtils;
	  	
	
	
	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = request.getParameter("email");
		 String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
		    
		    Map<String, Object> map = Map.of("email", email
		                                   , "pw", pw);
		    
		    HttpSession session = request.getSession();
		    
		    AdminDto user = adminMapper.getAdminUser(map);
		    
		    if(user != null) {
		        request.getSession().setAttribute("user", user);
		        adminMapper.insertAccess(email);
		        response.sendRedirect(request.getContextPath() + "/main.do");
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

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		try {
			response.sendRedirect(request.getContextPath() + "/main.do");
		} catch (Exception e) {
			e.printStackTrace();	// 오류 값 출력하기!
		}
		
	}
	
	@Override
	public AdminDto getAdminUser(String email) {
		return adminMapper.getAdminUser(Map.of("email", email));
	}
	
}
