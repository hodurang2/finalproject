package com.gdu.drawauction.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.drawauction.dto.AlarmDto;
import com.gdu.drawauction.service.AlarmService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AlarmController {
	
	private final AlarmService alarmService;
    
	@ResponseBody
	@GetMapping(value="/alarm/getAlarmList.do", produces = "application/json")
	public List<AlarmDto> getAlarmList(HttpServletRequest request) {
	  return alarmService.getAlarmList(request);
	}
	
	@ResponseBody
	@GetMapping(value="/alarm/alarmCheck.do", produces = "application/json")
	public int alarmCheck(HttpServletRequest request, Model model)  {
	  int checkResult = alarmService.alarmCheck(request);
	  return checkResult;
	}
	
	@ResponseBody
	@PostMapping(value="/alarm/updateAlarm.do", produces = "application/json")
	public Map<String, Object> updateAlarm(HttpServletRequest request) {
		
	  int updateResult = alarmService.updateAlarm(request);
	  return Map.of("updateResult", updateResult);
	}
}
