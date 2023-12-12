package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AlarmDto;

@Mapper
public interface AlarmMapper {
	public void insertDrawAlarm(Map<String, Object> map);
	public void insertInquiryAlarm(Map<String, Object> map);
	public List<AlarmDto> getAlarmList(String email);
	public int alarmCheck(String email);
	public int updateAlarm(int userNo);
}
