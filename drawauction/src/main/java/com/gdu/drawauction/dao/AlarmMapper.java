package com.gdu.drawauction.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlarmMapper {
	public void insertDrawAlarm(Map<String, Object> map);
}
