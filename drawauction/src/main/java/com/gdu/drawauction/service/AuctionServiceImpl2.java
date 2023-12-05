package com.gdu.drawauction.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.drawauction.dao.AuctionMapper2;
import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyFileUtils;
import com.gdu.drawauction.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class AuctionServiceImpl2 implements AuctionService2 {
  
  private final AuctionMapper2 auctionMapper2;
  private final MyFileUtils myFileUtils;
  private final MyPageUtils myPageUtils;
  
  @Override
  public Map<String, Object> getAuctionList(HttpServletRequest request) {
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = auctionMapper2.getAuctionCount();
    int display = 9;
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<AuctionDto> auctionList = auctionMapper2.getAuctionList(map);
    System.out.println(auctionList.get(0).getEndAt());
    System.out.println(auctionList.get(0).getEndAt());
    System.out.println(auctionList.get(0).getEndAt());
    return Map.of("auctionList", auctionList
                , "totalPage", myPageUtils.getTotalPage());
  }
  
  @Transactional(readOnly=true)
  @Override
  public void loadAuction(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("auctionNo"));
    int auctionNo = Integer.parseInt(opt.orElse("0"));
    int bidCount = auctionMapper2.getBidCount(auctionNo);
    
    model.addAttribute("auction", auctionMapper2.getAuction(auctionNo));
    model.addAttribute("bidCount", bidCount);
    model.addAttribute("imageList", auctionMapper2.getImageList(auctionNo));
  }
  
  @Override
  public Map<String, Object> controlAuctionWishlist(HttpServletRequest request) {
    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
    HttpSession session = request.getSession();
    UserDto user = (UserDto) session.getAttribute("user");
    int userNo = user.getUserNo();
    
    Map<String, Object> map = Map.of("auctionNo", auctionNo, "userNo", userNo);
    
    int hasAuctionWishlist = auctionMapper2.hasAuctionWishlist(map);
    if(hasAuctionWishlist == 0) {
      auctionMapper2.insertAuctionWishlist(map);
    } else if(hasAuctionWishlist == 1) {
      auctionMapper2.deleteAuctionWishlist(map);
    }
    return Map.of("hasAuctionWishlist", hasAuctionWishlist);
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> hasAuctionWishlist(HttpServletRequest request) {
    
    int auctionNo = Integer.parseInt(request.getParameter("auctionNo")); 
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    Map<String, Object> map = Map.of("auctionNo", auctionNo, "userNo", userNo);
    
    int wishCheckResult = auctionMapper2.hasAuctionWishlist(map);
    
    return new ResponseEntity<>(Map.of("wishCheckResult", wishCheckResult), HttpStatus.OK);
  }
  
@Override
  public Map<String, Object> getBidCount(int auctionNo) {
    
    int bidCount = auctionMapper2.getBidCount(auctionNo);
    return Map.of("bidCount", bidCount);
  }
  
}
