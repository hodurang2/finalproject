package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.BidDto;
import com.gdu.drawauction.dto.UserDto;

@Mapper
public interface MypageMapper {

  public int updateUser(UserDto user);    // 회원 정보 수정(ajax)
  
  public int updateUserIntroduction(UserDto user);    // 소개글 수정(ajax)
  
  public int updateUserPw(UserDto user);    // 비밀번호 변경
  
  public int getAuctionBidCount(int bidderNo);     // 입찰 작품 수(종료, 진행 포함)

  public List<BidDto> getAuctionBidList(Map<String, Object> map);   // 입찰 내역
  
  }
