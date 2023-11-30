package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArtDto {
  private int artNo;
  private String title;
  private String contents;
  private int artType;
  private int width;
  private int height;
  private UserDto sellerDto;
  private CategoryDto categoryDto;
}
