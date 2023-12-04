package com.gdu.drawauction.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatDto {
  private int roomId;
  private int userNo;
  private int userNo2;
}