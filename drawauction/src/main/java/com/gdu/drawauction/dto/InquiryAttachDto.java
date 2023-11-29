package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InquiryAttachDto {
  private int inquiryAttachNo;
  private InquiryDto inquiryDto;
  private String path;
  private String originalFilename;
  private String filesystemName;
}
