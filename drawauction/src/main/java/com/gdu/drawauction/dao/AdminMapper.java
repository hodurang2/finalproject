package com.gdu.drawauction.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AdminDto;

@Mapper
public interface AdminMapper {

	public AdminDto getAdminUser(Map<String, Object> map);
	public int insertAccess(String email);
	
}
