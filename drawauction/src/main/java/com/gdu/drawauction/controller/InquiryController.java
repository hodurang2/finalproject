package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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
  
  @PostMapping("/addInquiry.do")
  public String addInquiry(MultipartHttpServletRequest multipartRequest
                  , RedirectAttributes redirectAttributes) throws Exception {
    boolean addResult = inquiryService.addInquiry(multipartRequest);
    redirectAttributes.addFlashAttribute("addResult", addResult);
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
  @GetMapping(value="/getInquiryAttachList.do", produces="application/json")
  public Map<String, Object> getInquiryAttachList(HttpServletRequest request) {
    return inquiryService.getInquiryAttachList(request);
  }
  
  
  @GetMapping("/download.do")
  public ResponseEntity<Resource> download(HttpServletRequest request) {
    return inquiryService.download(request);
  }
  
  @GetMapping("/downloadAll.do")
  public ResponseEntity<Resource> downloadAll(HttpServletRequest request) {
    return inquiryService.downloadAll(request);
  }
  
}
