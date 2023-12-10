package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminDto {

	private int AdminNo;
	private String AdminEmail;
	private String AdminPw;
	private String Admin;
	private int AdminState;
	private UserDto userDto;
	
}
