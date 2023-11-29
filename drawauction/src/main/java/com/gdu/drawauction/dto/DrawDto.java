package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DrawDto {
	private int drawNo;
	private int categoryNo;
	private String title;
	private String contents;
	private int width;
	private int height;
	private UserDto userDto; // private int sellerNo;
}
