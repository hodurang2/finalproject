package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuctionImageDto {
  private int auctionImageNo;
  private int auctionNo;
  private String path;
  private String filesystemName;
  private String imageOriginalName;
  private int hasThumbnail;
}
