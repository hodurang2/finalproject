package com.gdu.drawauction.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.drawauction.dao.AuctionMapper;
import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.UserDto;
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
    
    int userNo;
    
    HttpSession session = request.getSession();
    
    if(session.getAttribute("user") == null) {
      for(AuctionDto auctionDto : auctionList) {
        auctionDto.setHeartClass("fa-regular");
      }
    } else {
      UserDto user = (UserDto) session.getAttribute("user");
      userNo = user.getUserNo();
      for(AuctionDto auctionDto : auctionList) {
        Map<String, Object> wishMap = Map.of("auctionNo", auctionDto.getAuctionNo(), "userNo", userNo);
        int hasAuctionWishlist = auctionMapper.hasAuctionWishlist(wishMap);
        String heartClass;
        if(hasAuctionWishlist == 0) {
          heartClass = "fa-regular";
        } else {
          heartClass = "fa-solid";
        }
        auctionDto.setHeartClass(heartClass);
      }
    }
    
    
    
    
    return Map.of("auctionList", auctionList
                , "totalPage", myPageUtils.getTotalPage());
  }
  
  @Override
  public Map<String, Object> controlAuctionWishlist(HttpServletRequest request) {
    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
    HttpSession session = request.getSession();
    UserDto user = (UserDto) session.getAttribute("user");
    int userNo = user.getUserNo();
    
    Map<String, Object> map = Map.of("auctionNo", auctionNo, "userNo", userNo);
    
    int hasAuctionWishlist = auctionMapper.hasAuctionWishlist(map);
    if(hasAuctionWishlist == 0) {
      auctionMapper.insertAuctionWishlist(map);
    } else if(hasAuctionWishlist == 1) {
      auctionMapper.deleteAuctionWishlist(map);
    }
    return Map.of("hasAuctionWishlist", hasAuctionWishlist);
  }
 
  
}
