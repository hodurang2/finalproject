package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.UserDto;

@Mapper
public interface AdminMapper {
	// 고객정보
	public List<UserDto> getUserList(Map<String, Object> map);
	public int getUserCount();
	public UserDto getUser(int userNo);
	
	public int deleteUser(int userNo);
	
	
}