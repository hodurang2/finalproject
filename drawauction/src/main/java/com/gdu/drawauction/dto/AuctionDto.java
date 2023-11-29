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
public class AuctionDto {
  private int auctionNo;
  private ArtDto artDto;
  private int startPrice;
  private int buyPrice;
  private Date startAt;
  private Date endAt;
  private int status;
}
