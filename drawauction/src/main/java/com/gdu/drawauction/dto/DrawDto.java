package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DrawDto {
	private int drawNo;
	private String title;
	private int price;
	private String contents;
	private int width;
	private int height;
	private CategoryDto categoryDto; // private String name;
	private UserDto userDto; // private int sellerNo;
	private int workTerm;
}
