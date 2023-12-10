package com.gdu.drawauction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserImageDto {
	private int userImageNo;
	private int userNo;
	private String path;
	private String filesystemName;
	private String imageOriginalName;
	private int hasThumbnail;
}
