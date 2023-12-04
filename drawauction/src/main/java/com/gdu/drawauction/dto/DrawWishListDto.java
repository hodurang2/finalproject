package com.gdu.drawauction.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DrawWishListDto {
	private int drawWishNo;
	private Date wishedAt;
	private UserDto userNo;
	private DrawDto drawDto;
}
