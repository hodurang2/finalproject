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

import com.gdu.drawauction.service.InquiryService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/inquiry")
@RequiredArgsConstructor
@Controller
public class InquiryController {
  
  private final InquiryService inquiryService;

  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
    inquiryService.loadInquiryList(request, model);
    return "inquiry/list";
  }
  
  @GetMapping("/detail.do")
  public String detail(int inquiryNo, Model model) {
    model.addAttribute("inquiry", inquiryService.getInquiry(inquiryNo, model));
    return "inquiry/detail";
  }
  
  @GetMapping("/write.form")
  public String write() {
    return "inquiry/write";
  }
  
  @PostMapping(value = "/imageUpload.do", produces = "application/json")
  @ResponseBody
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {
    return inquiryService.imageUpload(multipartRequest);
  }
  
  @PostMapping(value = "/addInquiry.do", produces = "application/json")
  public String addNotice(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("addResult", inquiryService.addInquiry(request));
    return "redirect:/inquiry/list.do"; 
  }
  
  @ResponseBody
  @PostMapping(value="/addAnswer.do", produces="application/json")
  public Map<String, Object> addAnswer(HttpServletRequest request) {
    return inquiryService.addAnswer(request);
  }
  
  @ResponseBody
  @GetMapping(value="/answerList.do", produces="application/json")
  public Map<String, Object> answerList(HttpServletRequest request){
    return inquiryService.loadAnswerList(request);
  }
  
  @ResponseBody
  @PostMapping(value="/addAnswerReply.do", produces="application/json")
  public Map<String, Object> addAnswerReply(HttpServletRequest request) {
    return inquiryService.addAnswerReply(request);
  }
  
  
  
}
