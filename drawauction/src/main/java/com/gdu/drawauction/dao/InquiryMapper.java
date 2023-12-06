package com.gdu.drawauction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.drawauction.dto.AnswerDto;
import com.gdu.drawauction.dto.InquiryAttachDto;
import com.gdu.drawauction.dto.InquiryDto;

@Mapper
public interface InquiryMapper {

  public int getInquiryCount();
  public List<InquiryDto> getInquiryList(Map<String, Object> map);
  public InquiryDto getInquiry(int inquiryNo);
  public int insertInquiry(InquiryDto inquiry);
  
  public int insertInquiryAttach(InquiryAttachDto attach);
  public List<InquiryAttachDto> getInquiryAttachList(int inquiryNo);
  public InquiryAttachDto getInquiryAttach(int inquiryAttachNo);
  public int updateDownloadCount(int inquiryAttachNo);
  
  public int insertAnswer(AnswerDto answer);
  public int getAnswerCount(int inquiryNo);
  public List<AnswerDto> getAnswerList(Map<String, Object> map);
  public int insertAnswerReply(AnswerDto answer);
  
  
}
