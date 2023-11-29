package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  
  @PostMapping("/add.do")
  public String add(MultipartHttpServletRequest multipartRequest
                  , RedirectAttributes redirectAttributes) throws Exception {
    boolean addResult = drawService.addDraw(multipartRequest);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/draw/list.do";
  }
  
  @GetMapping("/detail.do")
  public String detail(HttpServletRequest request, Model model) {
    drawService.loadDraw(request, model);
    return "draw/detail";
  }
  
  

}
