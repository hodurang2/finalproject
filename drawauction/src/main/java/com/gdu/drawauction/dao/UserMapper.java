package com.gdu.drawauction.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.InactiveUserDto;
import com.gdu.drawauction.dto.LeaveUserDto;
import com.gdu.drawauction.dto.UserDto;

@Mapper
public interface UserMapper {

	public UserDto getUser(Map<String, Object> map);
	public int insertAccess(String email);
	public LeaveUserDto getLeaveUser(Map<String, Object> map);
	public InactiveUserDto getInactiveUser(Map<String, Object> map);
	public int insertUser(UserDto user);
	public int updateUser(UserDto user);
	public int updateUserPw(UserDto user);
public int insertLeaveUser(UserDto user);
public int deleteUser(UserDto user);
public int insertInactiveUser();
public int deleteUserForInactive();
public int insertActiveUser(String email);
public int deleteInactiveUser(String email);
public int insertNaverUser(UserDto user);
}
