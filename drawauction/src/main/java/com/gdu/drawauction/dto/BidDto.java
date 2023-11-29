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
  private AuctionDto auctionDto;
  private UserDto bidderDto;
  private int price;
  private Date bidAt;
  private String postcode;
  private String roadAddress;
  private String jibunAddress;
  private String detailAddress;
}
