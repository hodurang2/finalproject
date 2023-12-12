package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.DrawImageDto;
import com.gdu.drawauction.dto.DrawOrderDto;
import com.gdu.drawauction.dto.DrawReviewDto;

@Mapper
public interface DrawMapper {
	public List<DrawDto> getDrawList(Map<String, Object> map);
	public List<DrawDto> getDrawSearchList(Map<String, Object> map);
	public int getDrawCount();
	public int getDrawSearchCount(int categoryNo);
	public int insertDraw(DrawDto draw);
	public int insertImage(DrawImageDto image);
	public DrawDto getDraw(int drawNo);
	public List<DrawImageDto> getImageList(int drawNo);
	public int addWishList(Map<String, Object> map);
	public int removeWishList(Map<String, Object> map);
	public int wishCheck(Map<String, Object> map);
	public DrawImageDto getImage(int drawNo);
	public DrawImageDto getDrawImage(int drawNo);
	public int deleteImage(int drawNo);
	public int updateDraw(Map<String, Object> map);
	public int deleteDraw(int drawNo);
	public int getReviewCount(int drawNo);
	public List<DrawReviewDto> getReviewList(Map<String, Object> map);
	public List<DrawOrderDto> getOrderReview(Map<String, Object> map);
	public int addReview(Map<String, Object> map);
	public int addDrawOrder(Map<String, Object> map);
	public int reviewCheck(Map<String, Object> map);
	public int getEmoney(int userNo);
	public int insertBuyerEmoney(Map<String, Object> map);
	public int insertSellerEmoney(Map<String, Object> map);
}
