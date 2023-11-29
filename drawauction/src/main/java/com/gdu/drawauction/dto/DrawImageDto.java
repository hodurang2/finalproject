package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DrawImageDto {
	private int drawImageNo;
	private int drawNo;
	private String path;
	private String filesystemName;
	private String imageOriginalName;
	private int hasThumbnail;
}
