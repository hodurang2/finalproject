package com.gdu.drawauction.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.drawauction.dao.AlarmMapper;
import com.gdu.drawauction.dto.AlarmDto;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService  {
	
	private final AlarmMapper alarmMapper;

	@Override
	public List<AlarmDto> getAlarmList(HttpServletRequest request)  {
	  String email = request.getParameter("email");
	  List<AlarmDto> alarmList = alarmMapper.getAlarmList(email);
	  
	  return alarmList;
	}
	
	@Override
	public int alarmCheck(HttpServletRequest request) {
		String email = request.getParameter("email");
		return alarmMapper.alarmCheck(email);
	}
	
	@Transactional
	@Override
	public int updateAlarm(HttpServletRequest request) {
		
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		
		return alarmMapper.updateAlarm(userNo);
	}
}
