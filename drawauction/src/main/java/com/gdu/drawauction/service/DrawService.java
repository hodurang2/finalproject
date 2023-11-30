package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface DrawService {
	public Map<String, Object> getDrawList(HttpServletRequest request);
	public boolean addDraw(MultipartHttpServletRequest multipartRequest) throws Exception;
	public void loadDraw(HttpServletRequest request, Model model);
	public void addWishList(HttpServletRequest request);
}
