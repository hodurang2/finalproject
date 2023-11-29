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
public class AnswerDto {
  private InquiryDto inquiryDto;
  private String contents;
  private Date createdAt;
  private int status;

}
