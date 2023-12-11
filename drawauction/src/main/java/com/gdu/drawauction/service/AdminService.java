package com.gdu.drawauction.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.drawauction.dto.AdminDto;

public interface AdminService {

	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void logout(HttpServletRequest request, HttpServletResponse response);
	public AdminDto getAdminUser(String email);
	
	
}
