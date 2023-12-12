package com.gdu.drawauction.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.drawauction.dao.AdminMapper;
import com.gdu.drawauction.dto.AdminDto;
import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	@Autowired
	private final AdminMapper adminMapper;
	private final MyPageUtils myPageUtils;
	  
	// 유저 정보 전부 가져오기
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
	
	// 유저 기록 가져오기
	@Override
	public UserDto getUser(int userNo, Model model) {
		UserDto user = adminMapper.getUser(userNo);
		model.addAttribute("user", user);
		return user;
	}

	// 유저 삭제.
	@Override
	public int removeUser(HttpServletRequest request) {
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		return adminMapper.deleteUser(userNo);
	}
	
	
/////////////////////////////
	// 그려드림 영역 //
/////////////////////////////
	
	@Override
	public void loadDrawList(HttpServletRequest request, Model model) {

		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		
		int page = Integer.parseInt(opt.orElse("1"));
		int total = adminMapper.getDrawCount();
		int display = 10;
		
		myPageUtils.setPaging(page, total, display);
		
		
		Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
										, "end", myPageUtils.getEnd());
		
		List<DrawDto> drawList = adminMapper.getDrawList(map);
		
		model.addAttribute("drawList", drawList);
		model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/admin/drawList.do"));
	    model.addAttribute("beginNo", total - (page - 1) * display);
		
		
	}
	
	@Override
	public DrawDto getDraw(int drawNo, Model model) {
		DrawDto draw = adminMapper.getDraw(drawNo);
		model.addAttribute("draw", draw);
		return draw;
	}
	
	// 그려드림 삭제
	@Override
	public int removeDraw(HttpServletRequest request) {
		int drawNo = Integer.parseInt(request.getParameter("drawNo"));
		return adminMapper.deleteDraw(drawNo);
	}
	
	@Override
	public void loadUserSearchList(HttpServletRequest request, Model model) {
		
		  	String column = request.getParameter("column");
		    String query = request.getParameter("query");
		    
		    Map<String, Object> map = new HashMap();
		    map.put("query", query);
		    
		    int total = adminMapper.getSearchUserCount(map);
		    
		    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		    String strPage = opt.orElse("1");
		    int page = Integer.parseInt(strPage);
		    int display = 10;
		    
		    myPageUtils.setPaging(page, total, display);
		    
		    map.put("begin", myPageUtils.getBegin());
		    map.put("end", myPageUtils.getEnd());
		    
		    List<UserDto> serachUserList = adminMapper.getSearchUserList(map);
		    
		    model.addAttribute("serachUserList", serachUserList);
		    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/free/search.do", "query=" + query ));
		    model.addAttribute("beginNo", total - (page - 1) * display);  
		
	}
	
}
