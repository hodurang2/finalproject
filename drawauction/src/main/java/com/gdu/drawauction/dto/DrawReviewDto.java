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
public class DrawReviewDto {
	private String reviewTitle;
	private String reviewContents;
	private int rating;
	private Date createdAt;
	private DrawDto drawDto;
	private UserDto userDto;
}
