package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NoticeAttachDto {
  private int noticeAttachNo;
  private NoticeDto noticeDto;
  private String path;
  private String originalFilename;
  private String filesystemName;
}
