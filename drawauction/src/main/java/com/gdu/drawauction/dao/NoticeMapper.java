package com.gdu.drawauction.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.NoticeAttachDto;
import com.gdu.drawauction.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
  public List<NoticeDto> getNoticeList(Map<String, Object> map);
  public int getNoticeCount();
  public NoticeDto getNotice(int noticeNo);
  public int insertNotice(NoticeDto noticeDto);
  public int insertNoticeAttach(NoticeAttachDto noticeAttach);
  public int updateNotice(NoticeDto notice);
  public int deleteNotice(int noticeNo);
}
