package com.gdu.drawauction.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.type.BigIntegerTypeHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.drawauction.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;

//    @GetMapping("/login.form")
//    public String loginForm(HttpServletRequest request, Model model) throws Exception {
//      // referer : 이전 주소가 저장되는 요청 Header 값
//      String referer = request.getHeader("referer");
//      String[] exceptUrl = {"/agree.form", "/join.form", "/join_option.form", "/find_id.form", "/find_pw.form", "/artistProfile.form"};
//      String ret = "";
//      if(referer != null) {
//        for(String url : exceptUrl) {
//          if(referer.contains(url)) {
//            ret = request.getContextPath() + "admin/home.do" ; 
//          }
//        }
//      } else {
//        ret = request.getContextPath() + "admin/home.do" ;
//      }
//
//      return "admin/login";
//    }
//    
//    
    
//    @PostMapping("/login.do")
//    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        adminService.login(request, response);
//    }
//
//    @GetMapping("/logout.do")
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        adminService.logout(request, response);
//    }
//    
    @GetMapping("/home.form")
    public String home() {
      return "admin/home";
    }
    
    @GetMapping("/userList.do")
    public String userList(HttpServletRequest request, Model model) {
      adminService.loadUserList(request, model);
      return "admin/userList";
    
    }

    @PostMapping("/removeUser.do")
    public String removeUser(HttpServletRequest request, RedirectAttributes redirectAttributes) {
      int removeResult = adminService.removeUser(request);
      redirectAttributes.addFlashAttribute("removeResult", removeResult);
      return "redirect:/admin/userList.do";
    }
    
 
    // 그려드림
    @GetMapping("/drawList.do")
    public String drawList(HttpServletRequest request, Model model) {
      adminService.loadDrawList(request, model);
      return "admin/drawList";
    }
    
    @PostMapping("/removeDraw.do")
    public String removeDraw(HttpServletRequest request, RedirectAttributes redirectAttributes) {
      int removeResult = adminService.removeDraw(request);
      redirectAttributes.addFlashAttribute("removeResult", removeResult);
      return "redirect:/admin/drawList.do";
    }
    
    
    // 회원 검색
    @GetMapping("/search.do")
    public String Usersearch(HttpServletRequest request, Model model) {
      adminService.loadUserSearchList(request, model);
      return "admin/userList";
    }

     
    @GetMapping("/adminAucList.do")
    public String getAdminAucList(HttpServletRequest request, Model model){
      adminService.getAdminAucList(request, model);
      return "admin/adminAucList";
       
    }
    
    @PostMapping("/removeAdminAuc.do")
    public String removeAdminAuc(HttpServletRequest request, RedirectAttributes redirectAttributes) {
      int removeResult = adminService.removeAdminAuc(request);
      System.out.println("컨트롤러" + removeResult);
      redirectAttributes.addFlashAttribute("removeResult", removeResult);
      return "redirect:/admin/adminAucList.do";
    }
    

}
