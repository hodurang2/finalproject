package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.DrawImageDto;

@Mapper
public interface DrawMapper {
	public List<DrawDto> getDrawList(Map<String, Object> map);
	public int getDrawCount();
	public int insertDraw(DrawDto draw);
	public int insertImage(DrawImageDto image);
}
