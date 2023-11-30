package com.gdu.drawauction.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InactiveUserDto {
	
	private int userNo;
	private String email;
	private String pw;
	private String name;
	private String mobile;
	private String gender;
	private int agree;
	private int state;
	private String joinedAt;
	private String postcode;
	private String roadAddress;
	private String jibunAddress;
	private String detailAddress;
	private String nickname;
	private String introduction;
	private String inactivedAt;
	
}