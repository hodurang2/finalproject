package com.gdu.drawauction.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.drawauction.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value = "/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private AdminService adminService;

    @GetMapping("/login.form")
    public String loginForm(HttpServletRequest request, Model model) throws Exception {
      // referer : 이전 주소가 저장되는 요청 Header 값
      String referer = request.getHeader("referer");
      String[] exceptUrl = {"/agree.form", "/join.form", "/join_option.form", "/find_id.form", "/find_pw.form", "/artistProfile.form"};
      String ret = "";
      if(referer != null) {
        for(String url : exceptUrl) {
          if(referer.contains(url)) {
            ret = request.getContextPath() + "admin/home.do" ; 
          }
        }
      } else {
        ret = request.getContextPath() + "admin/home.do" ;
      }

      return "admin/login";
    }
    
    
    
    @PostMapping("/login.do")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        adminService.login(request, response);
    }

    @GetMapping("/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        adminService.logout(request, response);
    }
    
    @GetMapping("/home.form")
    public String AdminHome() {
    	return "admin/home";
    }
	
}
