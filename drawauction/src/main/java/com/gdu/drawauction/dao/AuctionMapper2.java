package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.AuctionImageDto;
import com.gdu.drawauction.dto.DrawImageDto;

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
  public int insertImage(DrawImageDto image);
  public DrawImageDto getImage(int drawImageNo);
  public int deleteImage(int drawImageNo);
}
