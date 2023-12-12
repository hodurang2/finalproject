package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface DrawService {
	public Map<String, Object> getDrawList(HttpServletRequest request);
	public Map<String, Object> getDrawSearchList(HttpServletRequest request);
	public boolean addDraw(MultipartHttpServletRequest multipartRequest) throws Exception;
	public void loadDraw(HttpServletRequest request, Model model);
	public ResponseEntity<Map<String, Object>> WishListControll(HttpServletRequest request);
	public ResponseEntity<Map<String, Object>> wishCheck(HttpServletRequest request);
	public void getDraw(HttpServletRequest request, Model model);
	public int modifyDraw(HttpServletRequest request);
	public Map<String, Object> getImageList(HttpServletRequest request);
	public Map<String, Object> addImage(MultipartHttpServletRequest multipartRequest) throws Exception;
	public Map<String, Object> removeImage(HttpServletRequest request);
	public int removeDraw(int drawNo);
	public Map<String, Object> getReviewList(HttpServletRequest request);
	public int addReview(HttpServletRequest request);
	public void getEmoney(HttpServletRequest request, Model model);
	public int addDrawOrder(HttpServletRequest request);
}
