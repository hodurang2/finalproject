package com.gdu.drawauction.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.gdu.drawauction.dto.UserDto;

public interface AdminService {

//	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception;
//	public void logout(HttpServletRequest request, HttpServletResponse response);
//	public AdminDto getAdminUser(String email);
	
	// 고객정보 mapper
	public void loadUserList(HttpServletRequest request, Model model);
	public UserDto getUser(int userNo, Model model);
	
	public int removeUser(HttpServletRequest request);
}