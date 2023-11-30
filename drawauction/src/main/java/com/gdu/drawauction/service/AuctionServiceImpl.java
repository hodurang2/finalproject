package com.gdu.drawauction.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.drawauction.dao.AuctionMapper;
import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.util.MyFileUtils;
import com.gdu.drawauction.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {

  private final AuctionMapper auctionMapper;
  private final MyFileUtils myFileUtils;
  private final MyPageUtils myPageUtils;
  
  @Override
  public Map<String, Object> getAuctionList(HttpServletRequest request) {
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = auctionMapper.getAuctionCount();
    int display = 9;
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<AuctionDto> auctionList = auctionMapper.getAuctionList(map);
    return Map.of("auctionList", auctionList
                , "totalPage", myPageUtils.getTotalPage());
  }
  
  @Override
  public void addAuctionWishlist(HttpServletRequest request) {
    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    Map<String, Object> map = Map.of("auctionNo", auctionNo, "userNo", userNo);
    auctionMapper.insertAuctionWishlist(map);
  }
  
  @Override
  public void removeAuctionWishlist(HttpServletRequest request) {
    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    Map<String, Object> map = Map.of("auctionNo", auctionNo, "userNo", userNo);
    auctionMapper.deleteAuctionWishlist(map);
  }
}
