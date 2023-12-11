package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.drawauction.dto.InquiryDto;

public interface InquiryService {

  public void loadInquiryList(HttpServletRequest request, Model model);
  public InquiryDto getInquiry(int inquiryNo, Model model);
  public boolean addInquiry(MultipartHttpServletRequest multipartRequest) throws Exception;
  
  public Map<String, Object> addAnswer(HttpServletRequest request);
  public Map<String, Object> loadAnswerList(HttpServletRequest request);
  public Map<String, Object> removeAnswer(int inquiryNo);
  
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest);
  public Map<String, Object> getInquiryAttachList(HttpServletRequest request);
  
  public ResponseEntity<Resource> download(HttpServletRequest request);
  public ResponseEntity<Resource> downloadAll(HttpServletRequest request);
}
