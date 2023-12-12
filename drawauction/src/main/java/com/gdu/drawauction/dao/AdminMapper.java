package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AdminDto;
import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.UserDto;

@Mapper
public interface AdminMapper {

	public AdminDto getAdminUser(Map<String, Object> map);
	public int insertAccess(String email);
	
	// 고객정보
	public List<UserDto> selectUserList(Map<String, Object> map);
	public int getUserListCount();
	public UserDto getUserCount(int userNo);
	
	// 경매정보
	public int getAdminAucCount();
	public List<AuctionDto> getAdminAucList(Map<String, Object> map);
}