package com.gdu.drawauction.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AlarmDto {
	private int alarmNo;
	private String alarmContents;
	private String alarmType;
	private Date createdAt;
	private UserDto userDto;        // userNo
	private AuctionDto auctionDto;  // auctionNo
	private DrawDto drawDto;		// drawNo
	private NoticeDto noticeDto;    // noticeNo
	private InquiryDto inquiryDto;  // inquiryNo
}
