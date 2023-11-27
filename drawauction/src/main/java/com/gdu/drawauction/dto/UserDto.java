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
public class UserDto {
  private int userNo;
  private String email;
  private String pw;
  private String name;
  private String gender;
  private String mobile;
  private String postcode;
  private String roadAddress;
  private String jibunAddress;
  private String detailAddress;
  private int agree;
  private int state;
  private Date pwModifiedAt ;
  private Date joinedAt ;
}
