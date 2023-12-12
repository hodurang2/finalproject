package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

<<<<<<< HEAD
import com.gdu.drawauction.dto.AdminDto;
import com.gdu.drawauction.dto.DrawDto;
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
	
	// 그려드림 관리
	public List<DrawDto> getDrawList(Map<String, Object> map);
	public int getDrawCount();
	public DrawDto getDraw(int drawNo);
	public int deleteDraw(int drawNo);
	
	
}