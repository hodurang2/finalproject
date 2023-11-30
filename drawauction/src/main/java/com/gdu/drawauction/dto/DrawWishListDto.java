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
	private int userNo;
	private Date wishedAt;
	private DrawDto drawDto;
}
