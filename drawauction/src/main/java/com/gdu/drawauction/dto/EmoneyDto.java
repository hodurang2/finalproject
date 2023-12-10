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
public class EmoneyDto {
	private int emoneyNo;
	private UserDto userDto; // userNo
	private int emoneyHistory;
	private String emoneyDate;
}
