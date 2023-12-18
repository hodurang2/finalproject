package com.gdu.drawauction.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.BidDto;
import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.DrawOrderDto2;
import com.gdu.drawauction.dto.EmoneyDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyFileUtils;
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
  private final MyFileUtils myFileUtils;
  
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
  
  // 회원탈퇴
  @Override
  public void leave(HttpServletRequest request, HttpServletResponse response) {
  
    Optional<String> opt = Optional.ofNullable(request.getParameter("userNo"));
    int userNo = Integer.parseInt(opt.orElse("0"));
    
    UserDto user = mypageMapper.getUser(Map.of("userNo", userNo));
    
    if(user == null) {
      try {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('회원 탈퇴를 수행할 수 없습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
        out.println("</script>");
        out.flush();
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    int insertLeaveUserResult = mypageMapper.insertLeaveUser(user);
    int deleteUserResult = mypageMapper.deleteUser(user);
    
   try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertLeaveUserResult == 1 && deleteUserResult == 1) {
        HttpSession session = request.getSession();
        session.invalidate();
        out.println("alert('회원 탈퇴되었습니다. 그 동안 드로옥션을 이용해 주셔서 감사합니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
      } else {
        out.println("alert('회원 탈퇴되지 않았습니다.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  /*
  @Override
  public boolean addUserImage(MultipartHttpServletRequest multipartRequest) throws Exception {
      
    
      int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
      
      UserDto user = UserDto.builder()
                            .userNo(userNo)
                            .build();
      
      int userCount = mypageMapper.insertUser(user);
      
      List<MultipartFile> files = multipartRequest.getFiles("files");
      
      int imageCount;
      if(files.get(0).getSize() == 0) {
        imageCount = 1;
      } else {
        imageCount = 0;
      }
      
      MultipartFile multipartFile = files.get(0);
      
      if(multipartFile != null && !multipartFile.isEmpty()) {
        String path = myFileUtils.getUserImagePath();
        File dir = new File(path);
        
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        String imageOriginalName = multipartFile.getOriginalFilename();
        String filesystemName = myFileUtils.getFilesystemName(imageOriginalName);
        File file = new File(dir, filesystemName);
        
        multipartFile.transferTo(file);
        
        String contentType = Files.probeContentType(file.toPath());  // 이미지의 Content-Type은 image/jpeg, image/png 등 image로 시작한다.
        int hasThumbnail = (contentType != null && contentType.startsWith("image")) ? 1 : 0;
        
        if(hasThumbnail == 1) {
          File thumbnail = new File(dir, "s_" + filesystemName);  // small 이미지를 의미하는 s_을 덧붙임
          Thumbnails.of(file)
                    .size(500, 500)      // 가로 100px, 세로 100px
                    .toFile(thumbnail);
        }
        
        UserImageDto image = UserImageDto.builder()
                  .path(path)
                  .imageOriginalName(imageOriginalName)
                  .filesystemName(filesystemName)
                  .hasThumbnail(hasThumbnail)
                  .userNo(user.getUserNo())
                  .build();
        
        imageCount += mypageMapper.insertUserImage(image);
        
      }
     
      return (userCount == 1) && (files.size() == imageCount);
      
   }
  

  @Override
  public Map<String, Object> addUserImage(MultipartHttpServletRequest multipartRequest) throws Exception {
      
      List<MultipartFile> files =  multipartRequest.getFiles("files");
      
      int ImageCount;
      if(files.get(0).getSize() == 0) {
        ImageCount = 1;
      } else {
        ImageCount = 0;
      }
      
      for(MultipartFile multipartFile : files) {
        
        if(multipartFile != null && !multipartFile.isEmpty()) {
          
          String path = myFileUtils.getUserImagePath();
          File dir = new File(path);
          if(!dir.exists()) {
            dir.mkdirs();
          }
          
          String imageOriginalName = multipartFile.getOriginalFilename();
          String filesystemName = myFileUtils.getFilesystemName(imageOriginalName);
          File file = new File(dir, filesystemName);
          
          multipartFile.transferTo(file);
          
          String contentType = Files.probeContentType(file.toPath());  // 이미지의 Content-Type은 image/jpeg, image/png 등 image로 시작한다.
          int hasThumbnail = (contentType != null && contentType.startsWith("image")) ? 1 : 0;
          
          if(hasThumbnail == 1) {
            File thumbnail = new File(dir, "s_" + filesystemName);  // small 이미지를 의미하는 s_을 덧붙임
            Thumbnails.of(file)
                      .size(250, 250)      // 가로 100px, 세로 100px
                      .toFile(thumbnail);
          }
          
          int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
          
          UserImageDto image = UserImageDto.builder()
                              .path(path)
                              .imageOriginalName(imageOriginalName)
                              .filesystemName(filesystemName)
                              .hasThumbnail(hasThumbnail)
                              .userNo(userNo)
                              .build();
          
          ImageCount += mypageMapper.insertUserImage(image);
          
        }  // if
        
      }  // for
      
      return Map.of("imageResult", files.size() == ImageCount);
      
  }
   */
  
  
  
  @Override
  public void getCount(HttpServletRequest request, Model model) {

    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");
    
    if(user != null) {
      int userNo = user.getUserNo();
      
      int artForSaleCount = mypageMapper.getArtForSaleCount(userNo);
      int biddingCount = mypageMapper.getBiddingCount(userNo);
      int wishAuctionCount = mypageMapper.getAuctionWishCount(userNo);
      int wishDrawCount = mypageMapper.getDrawWishCount(userNo);
      
      model.addAttribute("artForSaleCount", artForSaleCount);
      model.addAttribute("biddingCount", biddingCount);
      model.addAttribute("wishAuctionCount", wishAuctionCount);
      model.addAttribute("wishDrawCount", wishDrawCount);
      
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
  /*
  @Override
  public Map<String, Object> getProfileImage(HttpServletRequest request) {
    
    Map<String, Object> map = new HashMap<>();

    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");

    if(user != null) {
    
      String email = user.getEmail();
      String pw = mySecurityUtils.getSHA256(user.getPw());

      UserDto2 userDto = mypageMapper.getUser(Map.of("email", email,
                                                     "pw", pw));
      
      userDto.setUserImageDto(mypageMapper.getUserImage(email));
      
      map.put("userDto", userDto);

    } else {
      
      map.put("userDto", null);
      
    }
    return map;
  }
  
  */
  
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
      
      List<DrawOrderDto2> drawOrderList = mypageMapper.getDrawOrderList(Map.of("begin", myPageUtils.getBegin()
                                                                 , "end", myPageUtils.getEnd()
                                                                 , "buyerNo", buyerNo));
      
      
      
      for(DrawOrderDto2 drawOrderDto2 : drawOrderList) {
        drawOrderDto2.getDrawDto2().setImage(mypageMapper.getDrawImage(drawOrderDto2.getDrawDto2().getDrawNo()));
      }
      
      map.put("drawOrderList", drawOrderList);
      map.put("totalPage", myPageUtils.getTotalPage());

    } else {
      
      map.put("drawOrderList", null);
      
    }
    return map;
    
  }
  
  @Transactional(readOnly=true)
  @Override
  public Map<String, Object> getDrawReceivedOrderList(HttpServletRequest request) {
    
    Map<String, Object> map = new HashMap<>();
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");

    if(user != null) {
    
      int sellerNo = user.getUserNo();
      int total = mypageMapper.getDrawReceivedOrderCount(sellerNo);
      int display = 9;

      myPageUtils.setPaging(page, total, display);
      
      List<DrawOrderDto2> drawReceivedOrderList = mypageMapper.getDrawReceivedOrderList(Map.of("begin", myPageUtils.getBegin()
                                                                                           , "end", myPageUtils.getEnd()
                                                                                           , "sellerNo", sellerNo));
      
      for(DrawOrderDto2 drawOrderDto2 : drawReceivedOrderList) {
        drawOrderDto2.getDrawDto2().setImage(mypageMapper.getDrawImage(drawOrderDto2.getDrawDto2().getDrawNo()));
      }
      
      map.put("drawReceivedOrderList", drawReceivedOrderList);
      map.put("totalPage", myPageUtils.getTotalPage());

    } else {
      
      map.put("drawReceivedOrderList", null);
      
    }
    return map;
    
  }
  
  @Override
  public void getEmoneyList(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");
    
    if(user != null) {
      int userNo = user.getUserNo();
      Optional<Integer> opt2 = Optional.ofNullable(mypageMapper.getEmoneyCount(userNo));
      int total = Integer.parseInt(opt.orElse("0"));
      int display = 5;
      
      List<Integer> balanceList = new ArrayList<>();
      
      int balance;
      
      if (total != 0) {
        for (int i = total; i > 0; i--) {
          balanceList.add(mypageMapper.getEmoneyBalance(Map.of("userNo", userNo, "no", i)));
        }
        int lastIndex = total - 1;
        balance = balanceList.get(lastIndex);
        Collections.reverse(balanceList);     // balanceList 역순으로 저장
        
    } else {
        balanceList.add(0);
        balance = 0;
    }
      
      myPageUtils.setPaging(page, total, display);
      
      Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                     , "end", myPageUtils.getEnd()
                                     , "userNo", userNo);
      
      List<EmoneyDto> emoneyList = mypageMapper.getEmoneyList(map);
      
      model.addAttribute("page", page);
      model.addAttribute("total", total);
      model.addAttribute("balanceList", balanceList);
      model.addAttribute("balance", balance);
      model.addAttribute("emoneyList", emoneyList);
      model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/mypage/getEmoneyList.do"));
      model.addAttribute("beginNo", total - (page - 1) * display);
    }
  }

  
  
  @Override
  public Map<String, Object> getAuctionWishList(HttpServletRequest request) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");

    Map<String, Object> map = new HashMap<>();

    if(user != null) {
      
      int userNo = user.getUserNo();
      int total = mypageMapper.getAuctionWishCount(userNo);
      int display = 9;
      
      myPageUtils.setPaging(page, total, display);
  
      List<AuctionDto> auctionWishList = mypageMapper.getAuctionWishList(Map.of("begin", myPageUtils.getBegin()
                                                                              , "end", myPageUtils.getEnd() 
                                                                              , "userNo", userNo));

      for(AuctionDto auctionDto : auctionWishList) {
        
        Map<String, Object> auctionWishMap = Map.of("auctionNo", auctionDto.getAuctionNo(),
                                                    "userNo", userNo);
        
        int hasAuctionWish = mypageMapper.hasAuctionWish(auctionWishMap);
        
        String heartClass;
        if(hasAuctionWish == 0) {
          heartClass = "fa-regular";
        } else {
          heartClass = "fa-solid";
        }
        auctionDto.setHeartClass(heartClass);
      }
      
      for(AuctionDto auctionDto : auctionWishList) {
        auctionDto.setImage(mypageMapper.getMyAuctionImage(auctionDto.getAuctionNo()));
      }
      
      map.put("auctionWishList", auctionWishList);
      map.put("totalPage", myPageUtils.getTotalPage());
      
    } else {
      
      map.put("auctionWishList", null);
      
    }
    
    return map;
    
  }
  
  @Override
  public Map<String, Object> controlAuctionWish(HttpServletRequest request) {
    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
    HttpSession session = request.getSession();
    UserDto user = (UserDto) session.getAttribute("user");
    int userNo = user.getUserNo();
    
    Map<String, Object> map = Map.of("auctionNo", auctionNo, "userNo", userNo);
    
    int hasAuctionWish = mypageMapper.hasAuctionWish(map);
    if(hasAuctionWish == 0) {
      mypageMapper.insertAuctionWish(map);
    } else if(hasAuctionWish == 1) {
      mypageMapper.deleteAuctionWish(map);
    }
    return Map.of("hasAuctionWish", hasAuctionWish);
  }

  @Override
  public Map<String, Object> getDrawWishList(HttpServletRequest request) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    HttpSession session = request.getSession();
    UserDto user = (UserDto)session.getAttribute("user");

    Map<String, Object> map = new HashMap<>();

    if(user != null) {
      
      int userNo = user.getUserNo();
      int total = mypageMapper.getDrawWishCount(userNo);
      int display = 9;
      
      myPageUtils.setPaging(page, total, display);
  
      List<DrawDto> drawWishList = mypageMapper.getDrawWishList(Map.of("begin", myPageUtils.getBegin()
                                                                     , "end", myPageUtils.getEnd() 
                                                                     , "userNo", userNo));

      for(DrawDto drawDto : drawWishList) {
        
        Map<String, Object> drawWishMap = Map.of("drawNo", drawDto.getDrawNo()
                                               , "userNo", userNo);
        
        int hasDrawWish = mypageMapper.hasDrawWish(drawWishMap);
        
        String heart;
        if(hasDrawWish == 0) {
          heart = "fa-regular";
        } else {
          heart = "fa-solid";
        }
        drawDto.setHeart(heart);
      }
      
      for(DrawDto drawDto : drawWishList) {
        drawDto.setImage(mypageMapper.getDrawImage(drawDto.getDrawNo()));
      }
      
      map.put("drawWishList", drawWishList);
      map.put("totalPage", myPageUtils.getTotalPage());
      
    } else {
      
      map.put("drawWishList", null);
      
    }
    
    return map;
    
  }
  
  @Override
  public Map<String, Object> controlDrawWish(HttpServletRequest request) {
    int drawNo = Integer.parseInt(request.getParameter("drawNo"));
    HttpSession session = request.getSession();
    UserDto user = (UserDto) session.getAttribute("user");
    int userNo = user.getUserNo();
    
    Map<String, Object> map = Map.of("drawNo", drawNo, "userNo", userNo);
    
    int hasDrawWish = mypageMapper.hasDrawWish(map);
    if(hasDrawWish == 0) {
      mypageMapper.insertDrawWish(map);
    } else if(hasDrawWish == 1) {
      mypageMapper.deleteDrawWish(map);
    }
    return Map.of("hasDrawWish", hasDrawWish);
  }
  

}
