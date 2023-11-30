package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface DrawService {
	public Map<String, Object> getDrawList(HttpServletRequest request);
	public boolean addDraw(MultipartHttpServletRequest multipartRequest) throws Exception;
	public void loadDraw(HttpServletRequest request, Model model);
	public ResponseEntity<Map<String, Object>> WishListControll(HttpServletRequest request);
	public ResponseEntity<Map<String, Object>> wishCheck(HttpServletRequest request);
}
