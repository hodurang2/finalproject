package com.gdu.drawauction.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.drawauction.dao.MypageMapper;
import com.gdu.drawauction.dto.BidDto;
import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.DrawOrderDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyPageUtils;
import com.gdu.drawauction.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class MypageServiceImpl implements MypageService {

  private final MypageMapper mypageMapper;
  private final MySecurityUtils mySecurityUtils;
  private final MyPageUtils myPageUtils;
  
  @Override
  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request) {
    
    String name = mySecurityUtils.preventXSS(request.getParameter("name"));
    String nickname = request.getParameter("nickname");
    String gender = request.getParameter("gender");
    String mobile = request.getParameter("mobile");
    String postcode = request.getParameter("postcode");
    String roadAddress = request.getParameter("roadAddress");
    String jibunAddress = request.getParameter("jibunAddress");
    String detailAddress = request.getParameter("detailAddress");
    String event = request.getParameter("event");
    int agree = event.equals("on") ? 1 : 0;
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    UserDto user = UserDto.builder()
                          .name(name)
                          .nickname(nickname)
                          .gender(gender)
                          .mobile(mobile)
                          .postcode(postcode)
                          .roadAddress(roadAddress)
                          .jibunAddress(jibunAddress)
                          .detailAddress(detailAddress)
                          .agree(agree)
                          .userNo(userNo)
                          .build();
    
    int modifyResult = mypageMapper.updateUser(user);
    
    if(modifyResult == 1) {
      HttpSession session = request.getSession();
      UserDto sessionUser = (UserDto)session.getAttribute("user");
      sessionUser.setName(name);
      sessionUser.setNickname(nickname);
      sessionUser.setGender(gender);
      sessionUser.setMobile(mobile);
      sessionUser.setPostcode(postcode);
      sessionUser.setRoadAddress(roadAddress);
      sessionUser.setJibunAddress(jibunAddress);
      sessionUser.setDetailAddress(detailAddress);
      sessionUser.setAgree(agree);
    }
    
    return new ResponseEntity<>(Map.of("modifyResult", modifyResult), HttpStatus.OK);
    
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> modifyIntroduction(HttpServletRequest request) {
    String introduction = request.getParameter("introduction");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    UserDto user = UserDto.builder()
                          .introduction(introduction)
                          .userNo(userNo)
                          .build();
    
    int modifyIntroductionResult = mypageMapper.updateUserIntroduction(user);
    
    if(modifyIntroductionResult == 1) {
      HttpSession session = request.getSession();
      UserDto sessionUser = (UserDto)session.getAttribute("user");
      sessionUser.setIntroduction(introduction);
    }
    
    return new ResponseEntity<>(Map.of("modifyIntroductionResult", modifyIntroductionResult), HttpStatus.OK);
  }
 
  @Override
  public void modifyPw(HttpServletRequest request, HttpServletResponse response) {
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    UserDto user = UserDto.builder()
                    .pw(pw)
                    .userNo(userNo)
                    .build();
    
    int modifyPwResult = mypageMapper.updateUserPw(user);
    
    try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(modifyPwResult == 1) {
        HttpSession session = request.getSession();
        UserDto sessionUser = (UserDto)session.getAttribute("user");
        sessionUser.setPw(pw);
        out.println("alert('비밀번호가 수정되었습니다.')");
        out.println("location.href='" + request.getContextPath() + "/mypage/modify.form'");
      } else {
        out.println("alert('비밀번호가 수정되지 않았습니다.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public void getCount(HttpServletRequest request, Model model) {

    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");
    
    if(user != null) {
      int sellerNo = user.getUserNo();
      int bidderNo = user.getUserNo();
      
      int artForSaleCount = mypageMapper.getArtForSaleCount(sellerNo);
      int biddingCount = mypageMapper.getBiddingCount(bidderNo);
      
      model.addAttribute("artForSaleCount", artForSaleCount);
      model.addAttribute("biddingCount", biddingCount);
      
    }
    
  }
  
  
  @Override
  public void getAuctionBidList(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");
    
    if(user != null) {
      int bidderNo = user.getUserNo();
      int total = mypageMapper.getAuctionBidCount(bidderNo);
      System.out.println(total);
      int display = 10;
      
      myPageUtils.setPaging(page, total, display);
      
      Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                     , "end", myPageUtils.getEnd()
                                     , "bidderNo", bidderNo);
      
      List<BidDto> bidList = mypageMapper.getAuctionBidList(map);

      for(BidDto bidDto : bidList) {
        //System.out.println(mypageMapper.getMyAuctionImage(bidDto.getAuctionDto().getAuctionNo()));
        bidDto.getAuctionDto().setImage(mypageMapper.getMyAuctionImage(bidDto.getAuctionDto().getAuctionNo()));
      }
      
      model.addAttribute("bidList", bidList);
      model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/mypage/auctionBidList.do"));
      model.addAttribute("beginNo", total - (page - 1) * display);
    }
  }

  @Override
  public void getAuctionSalesList(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");
    
    if(user != null) {
      int sellerNo = user.getUserNo();
      int total = mypageMapper.getAuctionSalesCount(sellerNo);
      int display = 10;
          
      myPageUtils.setPaging(page, total, display);
      
      Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                     , "end", myPageUtils.getEnd()
                                     , "sellerNo", sellerNo);
      
      List<BidDto> salesList = mypageMapper.getAuctionSalesList(map);

      for(BidDto bidDto : salesList) {
        bidDto.getAuctionDto().setImage(mypageMapper.getMyAuctionImage(bidDto.getAuctionDto().getAuctionNo()));
      }
 
      model.addAttribute("salesList", salesList);
      model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/mypage/getAuctionSalesList.do"));
      model.addAttribute("beginNo", total - (page - 1) * display);
      
    }
  }
  
  
  
  @Transactional(readOnly=true)
  @Override
  public Map<String, Object> getMyDrawList(HttpServletRequest request) {
    
    Map<String, Object> map = new HashMap<>();
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");

    if(user != null) {
    
      int sellerNo = user.getUserNo();
      int total = mypageMapper.getMyDrawCount(sellerNo);
      int display = 9;

      myPageUtils.setPaging(page, total, display);
      
      List<DrawDto> myDrawList = mypageMapper.getMyDrawList(Map.of("begin", myPageUtils.getBegin()
                                                                 , "end", myPageUtils.getEnd()
                                                                 , "sellerNo", sellerNo));
      
      
      
      for(DrawDto drawDto : myDrawList) {
        drawDto.setImage(mypageMapper.getDrawImage(drawDto.getDrawNo()));
      }
      
      map.put("myDrawList", myDrawList);
      map.put("totalPage", myPageUtils.getTotalPage());

    } else {
      
      map.put("myDrawList", null);
      
    }
    return map;
    
  }
  
  @Transactional(readOnly=true)
  @Override
  public Map<String, Object> getDrawOrderList(HttpServletRequest request) {
    
    Map<String, Object> map = new HashMap<>();
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");

    if(user != null) {
    
      int buyerNo = user.getUserNo();
      int total = mypageMapper.getDrawOrderCount(buyerNo);
      int display = 9;

      myPageUtils.setPaging(page, total, display);
      
      List<DrawOrderDto> drawOrderList = mypageMapper.getDrawOrderList(Map.of("begin", myPageUtils.getBegin()
                                                                 , "end", myPageUtils.getEnd()
                                                                 , "buyerNo", buyerNo));
      
      
      
      for(DrawOrderDto drawOrderDto : drawOrderList) {
        drawOrderDto.getDrawDto().setImage(mypageMapper.getDrawImage(drawOrderDto.getDrawDto().getDrawNo()));
      }
      
      map.put("drawOrderList", drawOrderList);
      map.put("totalPage", myPageUtils.getTotalPage());

    } else {
      
      map.put("drawOrderList", null);
      
    }
    return map;
    
  }

}
