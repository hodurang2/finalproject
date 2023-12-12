package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

<<<<<<< HEAD
import com.gdu.drawauction.dto.AuctionDto;
=======
>>>>>>> main
import com.gdu.drawauction.dto.UserDto;

@Mapper
public interface AdminMapper {
	// 고객정보
	public List<UserDto> getUserList(Map<String, Object> map);
	public int getUserCount();
	public UserDto getUser(int userNo);
	
	public int deleteUser(int userNo);
	
	
	// 경매정보
	public int getAdminAucCount();
	public List<AuctionDto> getAdminAucList(Map<String, Object> map);
}