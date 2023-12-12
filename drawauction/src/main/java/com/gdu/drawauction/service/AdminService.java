package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gdu.drawauction.dto.AdminDto;
import com.gdu.drawauction.dto.UserDto;

public interface AdminService {

//	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception;
//	public void logout(HttpServletRequest request, HttpServletResponse response);
//	public AdminDto getAdminUser(String email);
	
	// 고객정보 mapper
	public void loadUserList(HttpServletRequest request, Model model);
<<<<<<< HEAD
	public UserDto getUserCount(int userNo, Model model);
  
	// 경매정보가져오기
	public Map<String, Object> getAdminAucList(HttpServletRequest request);
=======
	public UserDto getUser(int userNo, Model model);
	
	public int removeUser(HttpServletRequest request);
>>>>>>> main
}