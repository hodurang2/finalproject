package com.gdu.drawauction.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DrawOrderDto {
  private int orderNo;
  private Date orderDate;
  private int price;
  private String receiveEmail;
  private DrawDto drawDto;
  private UserDto userDto;
}
