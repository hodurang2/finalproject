package com.gdu.drawauction.service;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.drawauction.dao.AdminMapper;
import com.gdu.drawauction.dto.AdminDto;
import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyJavaMailUtils;
import com.gdu.drawauction.util.MyPageUtils;
import com.gdu.drawauction.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	@Autowired
	private final AdminMapper adminMapper;
	
	private final MyPageUtils myPageUtils;
	  	
	
	
//	@Override
//	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String email = request.getParameter("email");
//		 String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
//		    
//		    Map<String, Object> map = Map.of("email", email
//		                                   , "pw", pw);
//		    
//		    HttpSession session = request.getSession();
//		    
//		    AdminDto user = adminMapper.getAdminUser(map);
//		    
//		    if(user != null) {
//		        request.getSession().setAttribute("user", user);
//		        adminMapper.insertAccess(email);
//		        response.sendRedirect(request.getContextPath() + "/main.do");
//		      } else {
//		        response.setContentType("text/html; charset=UTF-8");
//		        PrintWriter out = response.getWriter();
//		        out.println("<script>");
//		        out.println("alert('일치하는 회원 정보가 없습니다.')");
//		        out.println("location.href='" + request.getContextPath() + "/main.do'");
//		        out.println("</script>");
//		        out.flush();
//		        out.close();
//		      }
//		
//	}
//
//	@Override
//	public void logout(HttpServletRequest request, HttpServletResponse response) {
//		
//		HttpSession session = request.getSession();
//		session.invalidate();
//		
//		try {
//			response.sendRedirect(request.getContextPath() + "/main.do");
//		} catch (Exception e) {
//			e.printStackTrace();	// 오류 값 출력하기!
//		}
//		
//	}
	
//	@Override
//	public AdminDto getAdminUser(String email) {
//		return adminMapper.getAdminUser(Map.of("email", email));
//	}
//	
	@Override
	public void loadUserList(HttpServletRequest request, Model model) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		
		int page = Integer.parseInt(opt.orElse("1"));
		int total = adminMapper.getUserCount();
		int display = 10;
		
		myPageUtils.setPaging(page, total, display);
		
		
		Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
										, "end", myPageUtils.getEnd());
		
		List<UserDto> userList = adminMapper.getUserList(map);
		
		model.addAttribute("userList", userList);
		model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/admin/userList.do"));
	    model.addAttribute("beginNo", total - (page - 1) * display);
	}
	
	
	@Override
	public UserDto getUser(int userNo, Model model) {
		UserDto user = adminMapper.getUser(userNo);
		model.addAttribute("user", user);
		return user;
	}

	@Override
	public int removeUser(HttpServletRequest request) {
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		return adminMapper.deleteUser(userNo);
	}
	
<<<<<<< HEAD
	@Override
	public Map<String, Object> getAdminAucList(HttpServletRequest request) {
	  Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = adminMapper.getAdminAucCount();
    int display = 9;

    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<AuctionDto> adminAucList = adminMapper.getAdminAucList(map);

    return Map.of("adminAucList", adminAucList
                , "totalPage", myPageUtils.getTotalPage());
  }

}
=======
	
	
}
>>>>>>> main
