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

import com.gdu.drawauction.service.AuctionService2;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auction2")
@RequiredArgsConstructor
@Controller
public class AuctionController2 {
  private final AuctionService2 auctionService2;
  
  @GetMapping("/list.do")
  public String list() {
    return "auction/list";
  }
  
  @ResponseBody
  @GetMapping(value="/getList.do", produces="application/json")
  public Map<String, Object> getList(HttpServletRequest request){
    return auctionService2.getAuctionList(request);
  }
  
  @GetMapping("/detail.do")
  public String detail(HttpServletRequest request, Model model) {
    auctionService2.loadAuction(request, model);
    return "auction/detail";
  } 
  
  @GetMapping("/edit.form")
  public String edit(HttpServletRequest request, Model model) {
    auctionService2.loadAuction(request, model);
    return "auction/edit";
  }
  
  @ResponseBody
  @PostMapping(value="/controlWishlist.do", produces="application/json")
  public Map<String, Object> controlAuctionWishlist (HttpServletRequest request){
    return auctionService2.controlAuctionWishlist(request);
  }
  
  @GetMapping(value="/wishCheck.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> wishCheck(HttpServletRequest request) {
    return auctionService2.hasAuctionWishlist(request);
  }
  
  @GetMapping(value="/checkBidCount.do", produces="application/json")
  public Map<String, Object> getBidCount (int auctionNo) {
    return auctionService2.getBidCount(auctionNo);
  }
  
  @PostMapping("/modify.do")
  public String modify(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int modifyResult = auctionService2.modifyAuction(request);
    redirectAttributes.addFlashAttribute("modifyResult", modifyResult);
    return "redirect:/auction2/detail.do?auctionNo=" + request.getParameter("auctionNo");
  }
  
  @ResponseBody
  @GetMapping(value="/getImageList.do", produces="application/json")
  public Map<String, Object> getAttachList(HttpServletRequest request) {
    return auctionService2.getImageList(request);
  }
  
  @ResponseBody
  @PostMapping(value="/addImage.do", produces="application/json")
  public Map<String, Object> addAttach(MultipartHttpServletRequest multipartRequest) throws Exception {
    return auctionService2.addImage(multipartRequest);
  }
  
  @ResponseBody
  @PostMapping(value="/removeImage.do", produces="application/json")
  public Map<String, Object> removeAttach(HttpServletRequest request) {
    return auctionService2.removeImage(request);
  }
  
  @PostMapping("/removeAuction.do")
  public String removeUpload(@RequestParam(value="auctionNo", required=false, defaultValue="0") int auctionNo
                           , RedirectAttributes redirectAttributes) {
    int removeResult = auctionService2.removeAuction(auctionNo);
    redirectAttributes.addFlashAttribute("removeResult", removeResult);
    return "redirect:/auction/";
  }
  
  
}
