package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.AuctionImageDto;

@Mapper
public interface AuctionMapper {
  public List<AuctionDto> getAuctionList(Map<String, Object> map);
  public int getAuctionCount();
  public int insertAuctionWishlist(Map<String, Object> map);
  public int deleteAuctionWishlist(Map<String, Object> map);
  public int hasAuctionWishlist(Map<String, Object> map);
  public List<AuctionDto> searchAuctionList(Map<String, Object> map);
  public AuctionImageDto getAuctionimage(int auctionNo);
  public int insertArt(Map<String, Object> map);
  public int insertAuction(Map<String, Object> map);
  public int insertImage(AuctionImageDto auctionImageDto);
  public int getCurrentAuctionNo();
  
}
