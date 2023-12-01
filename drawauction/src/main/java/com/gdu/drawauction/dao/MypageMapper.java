package com.gdu.drawauction.dao;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.UserDto;

@Mapper
public interface MypageMapper {

  public int updateUser(UserDto user);    // 회원 정보 수정(ajax)
  
  public int updateUserIntroduction(UserDto user);    // 소개글 수정(ajax)
  
  public int updateUserPw(UserDto user);    // 비밀번호 변경
  
  }
