package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DrawOrderDto2 {
  private int orderNo;
  private String orderDate;
  private int price;
  private String receiveEmail;
  private String drawRequest;
  private int buyerNo;
  private String buyerNickname;
  private String buyerEmail;
  private String buyerMobile;
  private DrawDto2 drawDto2;
}
