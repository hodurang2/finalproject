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
public class InquiryDto {
  private int inquiryNo;
  private String title;
  private String content;
  private Date inquiryCreatedAt;
  private String inquiryType;
  private UserDto userDto;
  private AuctionDto auctionDto;
  private DrawDto drawDto;
}
