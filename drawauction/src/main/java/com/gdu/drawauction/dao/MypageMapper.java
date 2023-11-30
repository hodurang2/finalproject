package com.gdu.drawauction.dao;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.UserDto;

@Mapper
public interface MypageMapper {

  public int updateUser(UserDto user);    // 회원 정보 수정
  
}
