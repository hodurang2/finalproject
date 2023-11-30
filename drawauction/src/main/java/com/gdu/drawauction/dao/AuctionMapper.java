package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AuctionDto;

@Mapper
public interface AuctionMapper {
  public List<AuctionDto> getAuctionList(Map<String, Object> map);
  public int getAuctionCount();
}
