package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.AuctionImageDto;
import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.UserDto;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

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
  
  // 회원 검색
  public int getSearchUserCount(Map<String, Object> map);
  public List<UserDto> getSearchUserList(Map<String, Object> map);
	
	// 경매정보
	public int getAdminAucCount();
	public List<AuctionDto> getAdminAucList(Map<String, Object> map);
	public List<AuctionImageDto> getImageList(int auctionNo);
	public int deleteAdminAuc(int auctionNo);
}