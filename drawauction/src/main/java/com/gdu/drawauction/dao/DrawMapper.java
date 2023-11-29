package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.DrawDto;

@Mapper
public interface DrawMapper {
	public List<DrawDto> getDrawList(Map<String, Object> map);

}
