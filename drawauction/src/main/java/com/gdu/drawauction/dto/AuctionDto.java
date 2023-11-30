package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuctionDto {
  private int auctionNo;
  private int startPrice;
  private int buyPrice;
  private String startAt;
  private String endAt;
  private int status;
  private int price;
  private ArtDto artDto;
}
