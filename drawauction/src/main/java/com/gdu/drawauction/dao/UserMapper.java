package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.BidDto;
import com.gdu.drawauction.dto.InactiveUserDto;
import com.gdu.drawauction.dto.LeaveUserDto;
import com.gdu.drawauction.dto.UserDto;

@Mapper
public interface UserMapper {
	  public UserDto getUser(Map<String, Object> map);
	  public int insertAccess(String email);							// 접속기록 넣기
	  public LeaveUserDto getLeaveUser(Map<String, Object> map);		// 탈퇴 회원 
	  public InactiveUserDto getInactiveUser(Map<String, Object> map);	// 휴면 회원
	  
	  public int insertUser(UserDto user);								// 유저 정보 넣기 
	  public int updateUser(UserDto user);								// 유저 정보 수정하기
	  public int insertLeaveUser(UserDto user);							
	  public int deleteUser(UserDto user);								
	  public int insertInactiveUser();									
	  public int deleteUserForInactive();							
	  public int insertActiveUser(String email);						
	  public int deleteInactiveUser(String email);
	  
	  // 입찰, 출품 가져오기.
	  public int getAuctionBidCount(int bidderNo);     // 입찰 작품수(종료, 진행 포함)
	  public List<BidDto> getAuctionBidList(Map<String, Object> map);   // 입찰 내역
	  public int getAuctionSalesCount(int sellerNo);   // 출품 작품수(종료, 진행 포함)
	  public List<BidDto> getAuctionSalesList(Map<String, Object> map); // 출품 내역
	  
	  
	  // 소셜로그인
	  public int insertNaverUser(UserDto user);
	  public int insertKakaoUser(UserDto user);
	  
	  // id/pw 찾기
	  public List<UserDto> findId(UserDto user);
	  public int findPw(UserDto user);
	  public void updatePw(Map<String, Object> map);
	  

}