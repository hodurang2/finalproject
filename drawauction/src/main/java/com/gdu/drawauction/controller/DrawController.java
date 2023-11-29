package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.drawauction.service.DrawService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/draw")
@RequiredArgsConstructor
@Controller
public class DrawController {
	
  private final DrawService drawService;
  
  @GetMapping("/list.do")
  public String list() {
    return "draw/list";
  }
  
  @GetMapping("/write.form")
  public String write() {
    return "draw/write";
  }
	
  @ResponseBody
  @GetMapping(value="/getList.do", produces="application/json")
  public Map<String, Object> getList(HttpServletRequest request){
    return drawService.getDrawList(request);
  }
  /*
  @ResponseBody
  @PostMapping(value="/imageUpload.do", produces="application/json")
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {
    return drawService.imageUpload(multipartRequest);
  }
  */
  

}
