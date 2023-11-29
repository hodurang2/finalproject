package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface DrawService {
	public Map<String, Object> getDrawList(HttpServletRequest request);
	//public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest);

}
