package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.AuctionImageDto;

@Mapper
public interface AuctionMapper2 {
  public List<AuctionDto> getAuctionList(Map<String, Object> map);
  public int getAuctionCount();
  public AuctionDto getAuction(int auctionNo);
  public int hasAuctionWishlist(Map<String, Object> map);
  public void insertAuctionWishlist(Map<String, Object> map);
  public void deleteAuctionWishlist(Map<String, Object> map);
  public int getBidCount(int auctionNo);
  public List<AuctionImageDto> getImageList(int auctionNo);
  public int updateAuction(Map<String, Object> map);
  public int updateArt(Map<String, Object> map);
  public int deleteAuction(int auctionNo);
  public int insertImage(AuctionImageDto image);
  public AuctionImageDto getImage(int auctionImageNo);
  public int deleteImage(int auctionImageNo);
  public int getEmoney(int userNo);
  public AuctionImageDto getAuctionImage(int auctionNo);
  public int insertBuyerEmoney(Map<String, Object> emoneyMap);
  public int insertSellerEmoney(Map<String, Object> emoneyMap);
  public int insertRealBid(Map<String, Object> bidMap);
  public int insertDigitalBid(Map<String, Object> bidMap);
}
