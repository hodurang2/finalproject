package com.gdu.drawauction.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.drawauction.service.AdminService;
import com.gdu.drawauction.service.DrawService;

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
    
    
}
