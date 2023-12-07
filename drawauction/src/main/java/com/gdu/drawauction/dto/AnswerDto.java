package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AnswerDto {
  private int inquiryNo;
  private int userNo;
  private String contents;
  private String createdAt;
  private int status;
  private UserDto userDto;
}
