package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.drawauction.dto.InquiryDto;

public interface InquiryService {

  public void loadInquiryList(HttpServletRequest request, Model model);
  public InquiryDto getInquiry(int inquiryNo, Model model);
  public int addInquiry(HttpServletRequest request);
  
  public Map<String, Object> addAnswer(HttpServletRequest request);
  public Map<String, Object> loadAnswerList(HttpServletRequest request);
  public Map<String, Object> addAnswerReply(HttpServletRequest request);
  
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest);
  
}
