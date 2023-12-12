package com.gdu.drawauction.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gdu.drawauction.dto.AlarmDto;

public interface AlarmService {
	
	public List<AlarmDto> getAlarmList(HttpServletRequest request);
	public int alarmCheck(HttpServletRequest request);
	public int updateAlarm(HttpServletRequest request);
}