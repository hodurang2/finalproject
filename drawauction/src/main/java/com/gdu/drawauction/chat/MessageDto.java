package com.gdu.drawauction.chat;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageDto {
  private int roomId;
  private String contents;
  private Date chatDate;
}
