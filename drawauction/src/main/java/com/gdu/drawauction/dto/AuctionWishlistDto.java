package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuctionWishlistDto {
  private int auctionWishNo;
  private String wishedAt;
  private AuctionDto auctionDto;
  private UserDto userDto;
}
