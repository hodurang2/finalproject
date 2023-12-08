package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  
  @PostMapping(value="/WishListControll.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> addWishList(HttpServletRequest request) {
	return drawService.WishListControll(request);
  }
  
  @GetMapping(value="/WishCheck.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> wishCheck(HttpServletRequest request) {
	return drawService.wishCheck(request);
  }
  
  @GetMapping("/edit.form")
  public String edit(HttpServletRequest request, Model model) {
    drawService.getDraw(request, model);
    return "draw/edit";
  }
  
  @GetMapping("/orderPayment.form")
  public String orderPayment(HttpServletRequest request, Model model) {
	drawService.loadDraw(request, model);
	drawService.getEmoney(request, model);
	return "draw/orderPayment";
  }
  
  @PostMapping("/modify.do")
  public String modify(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int modifyResult = drawService.modifyDraw(request);
    redirectAttributes.addFlashAttribute("modifyResult", modifyResult);
    return "redirect:/draw/detail.do?drawNo=" + request.getParameter("drawNo");
  }
  
  @ResponseBody
  @GetMapping(value="/getImageList.do", produces="application/json")
  public Map<String, Object> getAttachList(HttpServletRequest request) {
    return drawService.getImageList(request);
  }
  
  @ResponseBody
  @PostMapping(value="/addImage.do", produces="application/json")
  public Map<String, Object> addAttach(MultipartHttpServletRequest multipartRequest) throws Exception {
    return drawService.addImage(multipartRequest);
  }
  
  @ResponseBody
  @PostMapping(value="/removeImage.do", produces="application/json")
  public Map<String, Object> removeAttach(HttpServletRequest request) {
    return drawService.removeImage(request);
  }
  
  @PostMapping("/removeDraw.do")
  public String removeUpload(@RequestParam(value="drawNo", required=false, defaultValue="0") int drawNo
                           , RedirectAttributes redirectAttributes) {
    int removeResult = drawService.removeDraw(drawNo);
    redirectAttributes.addFlashAttribute("removeResult", removeResult);
    return "redirect:/draw/list.do";
  }
  
  @ResponseBody
  @GetMapping(value="/getReviewList.do", produces="application/json")
  public Map<String, Object> getReviewList(HttpServletRequest request){
    return drawService.getReviewList(request);
  }
  
  @PostMapping("/addReview.do")
  public String addReview(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int addReviewResult = drawService.addReview(request);
    redirectAttributes.addFlashAttribute("addReviewResult", addReviewResult);
    return "redirect:/draw/detail.do?drawNo=" + request.getParameter("drawNo");
  }
  
  @PostMapping("/orderPayment.do")
  public String addDrawOrder(HttpServletRequest request, RedirectAttributes redirectAttributes) {
	int resultSum = drawService.addDrawOrder(request);
	redirectAttributes.addFlashAttribute("resultSum", resultSum);
	return "redirect:/draw/detail.do?drawNo=" + request.getParameter("drawNo");
  }
  
  

}
