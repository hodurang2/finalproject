package com.gdu.drawauction.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequestMapping("/draw")
@RequiredArgsConstructor
@Controller
public class DrawController {
	
	@ResponseBody
	  @GetMapping(value="/getList.do", produces="application/json")
	  public Map<String, Object> getList(HttpServletRequest request){
	    return drawService.getUploadList(request);
	  }

}
