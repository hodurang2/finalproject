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

	private String email;
	private String pw;
	private String name;
	private int state;
	private UserDto userDto;
	
}
