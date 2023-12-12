package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.drawauction.service.AuctionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auction")
@RequiredArgsConstructor
@Controller
public class AuctionController {
  private final AuctionService auctionService;
  
  @GetMapping("/")
  public String list() {
    return "auction/list";
  }
  
  @GetMapping("/write.form")
  public String write() {
    return "auction/write";
  }
  
  @ResponseBody
  @GetMapping(value="/getList.do", produces="application/json")
  public Map<String, Object> getList(HttpServletRequest request){
    return auctionService.getAuctionList(request);
  }
  
  @ResponseBody
  @GetMapping(value="/controlAuctionWishlist.do", produces="application/json")
  public Map<String, Object> controlAuctionWishlist (HttpServletRequest request){
    return auctionService.controlAuctionWishlist(request);
  }
  
  @ResponseBody
  @GetMapping(value="/search.form", produces="application/json")
  public Map<String, Object> search(HttpServletRequest request){
    return auctionService.getSearchAuctionList(request);
  }
  
  
 @PostMapping("/add.do") 
 public String add(MultipartHttpServletRequest multipartRequest , RedirectAttributes redirectAttributes) throws Exception {
   boolean addResult = auctionService.addAuction(multipartRequest);
   redirectAttributes.addFlashAttribute("addResult", addResult); 
   return "redirect:/auction/"; 
   }
}
