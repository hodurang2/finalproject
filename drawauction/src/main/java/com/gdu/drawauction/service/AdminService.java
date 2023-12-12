package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import com.gdu.drawauction.dto.AdminDto;
import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.UserDto;

public interface AdminService {

	// 고객정보 mapper
	public void loadUserList(HttpServletRequest request, Model model);
	public UserDto getUser(int userNo, Model model);
	public int removeUser(HttpServletRequest request);
	
	// 그려드림 mapper
	public void loadDrawList(HttpServletRequest request, Model model);
	public DrawDto getDraw(int drawNo, Model model);
	public int removeDraw(HttpServletRequest request);
	
	// 회원검색
	public void loadUserSearchList(HttpServletRequest request, Model model);
	
}