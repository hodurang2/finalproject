package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DrawDto2 {
	private int drawNo;
	private String title;
	private int price;
	private String contents;
	private int width;
	private int height;
	private int sellerNo;
	private String sellerNickname;
	private String sellerEmail;
	private String sellerMobile;
	private int workTerm;
	private String heart;
	private CategoryDto categoryDto; // private String name;
	private DrawImageDto image;
}
