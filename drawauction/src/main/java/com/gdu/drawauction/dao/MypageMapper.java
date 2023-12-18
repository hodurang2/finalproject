package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.AuctionImageDto;
import com.gdu.drawauction.dto.BidDto;
import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.DrawImageDto;
import com.gdu.drawauction.dto.DrawOrderDto2;
import com.gdu.drawauction.dto.EmoneyDto;
import com.gdu.drawauction.dto.UserDto;

@Mapper
public interface MypageMapper {

  // 회원 정보 수정
  public int updateUser(UserDto user);                // 회원 정보 수정(ajax)
  public int updateUserIntroduction(UserDto user);    // 소개글 수정(ajax)
  public int updateUserPw(UserDto user);              // 비밀번호 변경
  
  // 회원 탈퇴
  public UserDto getUser(Map<String, Object> map);   // 사용자 가져오기
  public int insertLeaveUser(UserDto user);          // 탈퇴회원 추가
  public int deleteUser(UserDto user);               // 회원 삭제
  
  /*
  // 프로필 이미지
  public int insertUserBasicImage(String email);      // 사용자 기본 이미지 추가
  public UserImageDto getUserImage(String email);     // 사용자 이미지 가져오기
  public int insertUserImage(UserImageDto image);     // 사용자 이미지 추가
  */
  
  // E-Money
  public int getEmoneyCount(int userNo);                          // E-Money 입출금수
  public Integer getEmoneyBalance(Map<String, Object> map);       // E-Money 잔액
  public List<EmoneyDto> getEmoneyList(Map<String, Object> map);  // E-Money 내역
  
  // 갯수
  public int getArtForSaleCount(int sellerNo);   // 판매 중 작품 수
  public int getBiddingCount(int bidderNo);      // 입찰 중 작품 수
  
  
  // 경매
  public int getAuctionBidCount(int bidderNo);                      // 입찰 작품수(종료, 진행 포함)
  public List<BidDto> getAuctionBidList(Map<String, Object> map);   // 입찰 내역
  public int getAuctionSalesCount(int sellerNo);                    // 출품 작품수(종료, 진행 포함)
  public List<BidDto> getAuctionSalesList(Map<String, Object> map); // 출품 내역
  public AuctionImageDto getMyAuctionImage(int auctionNo);          // 경매 이미지

  // 그려드림
  public int getMyDrawCount(int sellerNo);                                      // 내가 올린 그려드림 갯수
  public List<DrawDto> getMyDrawList(Map<String, Object> map);                  // 나의 그려드림 목록
  public DrawImageDto getDrawImage(int drawNo);                                 // 그려드림 이미지
  public int getDrawOrderCount(int buyerNo);                                    // 내가 주문한 그려드림 갯수
  public List<DrawOrderDto2> getDrawOrderList(Map<String, Object> map);         // 내가 주문한 그려드림 목록
  public int getDrawReceivedOrderCount(int sellerNo);                           // 주문받은 그려드림 갯수
  public List<DrawOrderDto2> getDrawReceivedOrderList(Map<String, Object> map); // 주문받은 그려드림 목록
  
  // 경매 찜목록
  public int getAuctionWishCount(int userNo);                             // 경매 찜 갯수
  public List<AuctionDto> getAuctionWishList(Map<String, Object> map);    // 경매 찜목록
  public int hasAuctionWish(Map<String, Object> map);                     // 경매 좋아요 유무
  public int insertAuctionWish(Map<String, Object> map);                  // 경매 좋아요
  public int deleteAuctionWish(Map<String, Object> map);                  // 경매 좋아요 취소
  
  // 그려드림 찜목록
  public int getDrawWishCount(int userNo);                             // 그려드림 찜 갯수
  public List<DrawDto> getDrawWishList(Map<String, Object> map);    // 그려드림 찜목록
  public int hasDrawWish(Map<String, Object> map);                     // 그려드림 좋아요 유무
  public int insertDrawWish(Map<String, Object> map);                  // 그려드림 좋아요
  public int deleteDrawWish(Map<String, Object> map);                  // 그려드림 좋아요 취소
  
  }
