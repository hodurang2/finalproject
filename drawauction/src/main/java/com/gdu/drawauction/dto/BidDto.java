package com.gdu.drawauction.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BidDto {
  private int bidNo;
  private String nickname;
  private int auctionNo;
  private int bidderNo;
  private int price;
  private String bidAt;
  private String endAt;
  private String postcode;
  private String roadAddress;
  private String jibunAddress;
  private String detailAddress;
  private String receiveEmail;
  private String auctionRequest;
  private int bidPrice;
  private AuctionDto auctionDto;
  private UserDto bidderDto;
}
