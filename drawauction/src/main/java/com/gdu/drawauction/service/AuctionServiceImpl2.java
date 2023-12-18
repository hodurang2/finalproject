package com.gdu.drawauction.service;

import java.io.File;
import java.nio.file.Files;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.drawauction.dao.AuctionMapper2;
import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.AuctionImageDto;
import com.gdu.drawauction.dto.BidDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyFileUtils;
import com.gdu.drawauction.util.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;


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
    AuctionDto auctionDto = auctionMapper2.getAuction(auctionNo);
    System.out.println("auctionNo=" + auctionNo);
    int userNo;
    
    HttpSession session = request.getSession();
    
    if(session.getAttribute("user") == null) {
      auctionDto.setHeartClass("fa-regular");
      userNo = 0;
    } else {
      UserDto user = (UserDto) session.getAttribute("user");
      userNo = user.getUserNo();
      Map<String, Object> wishMap = Map.of("auctionNo", auctionDto.getAuctionNo(), "userNo", userNo);
      int wishCheck = auctionMapper2.hasAuctionWishlist(wishMap);
      String heart;
      if(wishCheck == 0) {
        heart = "fa-regular";
      } else {
        heart = "fa-solid";
      }
      auctionDto.setHeartClass(heart);
    }
    
    
    model.addAttribute("auction", auctionMapper2.getAuction(auctionNo));
    model.addAttribute("bidCount", auctionMapper2.getBidCount(auctionNo));
    model.addAttribute("imageList", auctionMapper2.getImageList(auctionNo));
    model.addAttribute("auctionImage", auctionMapper2.getAuctionImage(auctionNo));
  }
  
  @Override
  public Map<String, Object> controlAuctionWishlist(HttpServletRequest request) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("auctionNo"));
    int auctionNo = Integer.parseInt(opt.orElse("0"));
    AuctionDto auctionDto = auctionMapper2.getAuction(auctionNo);
    
    int userNo;
    
    HttpSession session = request.getSession();
    
    if(session.getAttribute("user") == null) {
      auctionDto.setHeartClass("fa-regular");
      userNo = 0;
    } else {
      UserDto user = (UserDto) session.getAttribute("user");
      userNo = user.getUserNo();
    }
    
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
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("auctionNo"));
    int auctionNo = Integer.parseInt(opt.orElse("0"));
    AuctionDto auctionDto = auctionMapper2.getAuction(auctionNo);
    
    int userNo;
    
    HttpSession session = request.getSession();
    
    if(session.getAttribute("user") == null) {
      auctionDto.setHeartClass("fa-regular");
      userNo = 0;
    } else {
      UserDto user = (UserDto) session.getAttribute("user");
      userNo = user.getUserNo();
    }
    
    Map<String, Object> map = Map.of("auctionNo", auctionNo, "userNo", userNo);
    
    int wishCheckResult = auctionMapper2.hasAuctionWishlist(map);
    
    return new ResponseEntity<>(Map.of("wishCheckResult", wishCheckResult), HttpStatus.OK);
  }
  
  @Override
  public Map<String, Object> getBidCount(int auctionNo) {
    int bidCount = auctionMapper2.getBidCount(auctionNo);
    return Map.of("bidCount", bidCount);
  }
  
  @Override
  public int modifyAuction(HttpServletRequest request) {
    
    System.out.println("서비스임플");
    int categoryNo = Integer.parseInt(request.getParameter("categoryNo"));
    System.out.println(categoryNo);
    String title = request.getParameter("title");
    System.out.println(title);
    int artType = Integer.parseInt(request.getParameter("artType"));
    System.out.println(artType);
    int buyPrice = Integer.parseInt(request.getParameter("buyPrice"));
    System.out.println(buyPrice);
    int startPrice = Integer.parseInt(request.getParameter("startPrice"));
    System.out.println(startPrice);
    int width = (request.getParameter("width").equals("")) ? 0 : Integer.parseInt(request.getParameter("width"));
    System.out.println(width);
    int height = (request.getParameter("height").equals("")) ? 0 : Integer.parseInt(request.getParameter("height"));
    System.out.println(height);
    String endAt = request.getParameter("endAt");
    endAt = endAt.replace("T", " ");
    System.out.println(endAt);
    String contents = request.getParameter("contents");
    System.out.println(contents);
    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
    System.out.println(auctionNo);
    int artNo = Integer.parseInt(request.getParameter("artNo"));
    System.out.println(artNo);
    
    Map<String, Object> map1 = Map.of("startPrice", startPrice
                                    , "buyPrice", buyPrice
                                    , "endAt", endAt
                                    , "auctionNo", auctionNo);
    Map<String, Object> map2 = Map.of("categoryNo", categoryNo
                                    , "title", title
                                    , "artType", artType
                                    , "width", width
                                    , "height", height
                                    , "contents", contents
                                    , "artNo", artNo);
    
    int result1 = auctionMapper2.updateAuction(map1);
    int result2 = auctionMapper2.updateArt(map2);
    
    int modifyResult = result1 + result2;
    
    return modifyResult;
  }
  
  @Override
  public Map<String, Object> getImageList(HttpServletRequest request) {
    Optional<String> opt = Optional.ofNullable(request.getParameter("auctionNo"));
    int auctionNo = Integer.parseInt(opt.orElse("0"));
    
    return Map.of("imageList", auctionMapper2.getImageList(auctionNo));  
  }
  
  @Override
  public Map<String, Object> addImage(MultipartHttpServletRequest multipartRequest) throws Exception {
    List<MultipartFile> files =  multipartRequest.getFiles("files");
    
    int ImageCount;
    if(files.get(0).getSize() == 0) {
      ImageCount = 1;
    } else {
      ImageCount = 0;
    }
    
    for(MultipartFile multipartFile : files) {
      
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        String path = myFileUtils.getDrawImagePath();
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
        
        AuctionImageDto image = AuctionImageDto.builder()
                            .path(path)
                            .imageOriginalName(imageOriginalName)
                            .filesystemName(filesystemName)
                            .hasThumbnail(hasThumbnail)
                            .auctionNo(Integer.parseInt(multipartRequest.getParameter("auctionNo")))
                            .build();
        
        ImageCount += auctionMapper2.insertImage(image);
        
      }  // if
      
    }  // for
    
    return Map.of("imageResult", files.size() == ImageCount);
    
  }
  
  
  @Override
  public Map<String, Object> removeImage(HttpServletRequest request) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("auctionImageNo"));
    int auctionImageNo = Integer.parseInt(opt.orElse("0"));
    
    AuctionImageDto image = auctionMapper2.getImage(auctionImageNo);
    File file = new File(image.getPath(), image.getFilesystemName());
    if(file.exists()) {
      file.delete();
    }
    
    if(image.getHasThumbnail() == 1) {
      File thumbnail = new File(image.getPath(), "s_" + image.getFilesystemName());
      if(thumbnail.exists()) {
        thumbnail.delete();
      }
    }
    
    int removeResult = auctionMapper2.deleteImage(auctionImageNo);
    
    return Map.of("removeResult", removeResult);
    
  }
  
  @Override
  public int removeAuction(int auctionNo) {
      
      // 파일 삭제
      List<AuctionImageDto> imageList = auctionMapper2.getImageList(auctionNo);
      for(AuctionImageDto image : imageList) {
        
        File file = new File(image.getPath(), image.getFilesystemName());
        if(file.exists()) {
          file.delete();
        }
        
        if(image.getHasThumbnail() == 1) {
          File thumbnail = new File(image.getPath(), "s_" + image.getFilesystemName());
          if(thumbnail.exists()) {
            thumbnail.delete();
          }
        }
      }
      return auctionMapper2.deleteAuction(auctionNo);
  }
  
  @Override
  public void getEmoney(HttpServletRequest request, Model model) {
    
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    int emoney = auctionMapper2.getEmoney(userNo);
    
    model.addAttribute("emoney", emoney);
  }
  
  @Override
  public int addMaxBid(HttpServletRequest request) {

    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
    int buyerNo = Integer.parseInt(request.getParameter("buyerNo"));    // 구매자 userNo
    int price = Integer.parseInt(request.getParameter("price"));
    int userNo = Integer.parseInt(request.getParameter("userNo"));      // 판매자 userNo    
    int artType = Integer.parseInt(request.getParameter("artType"));    // 작품유형    
    String auctionRequest = request.getParameter("auctionRequest");
    
    Map<String, Object> bidMap;
    Map<String, Object> emoneyMap;
    
    int addBidResult;
    
    if(artType == 0) { // 작품유형이 디지털(== 0)일 때
      
      String receiveEmail = request.getParameter("receiveEmail");
      
      bidMap = Map.of("auctionNo", auctionNo
                    , "buyerNo", buyerNo
                    , "price", price
                    , "receiveEmail", receiveEmail
                    , "auctionRequest", auctionRequest);

      emoneyMap = Map.of("userNo", userNo
                       , "price", price
                       , "buyerNo", buyerNo);
      
      addBidResult = auctionMapper2.insertDigitalBid(bidMap);
      
    } else { // 실물일 때
      
      String postcode = request.getParameter("postcode");
      String roadAddress = request.getParameter("roadAddress");
      String jibunAddress = request.getParameter("jibunAddress");
      String detailAddress = request.getParameter("detailAddress");
      
      bidMap = Map.of("auctionNo", auctionNo
                    , "buyerNo", buyerNo
                    , "price", price
                    , "postcode" ,postcode
                    , "roadAddress", roadAddress
                    , "jibunAddress" , jibunAddress
                    , "detailAddress" , detailAddress
                    , "auctionRequest", auctionRequest);

      emoneyMap = Map.of("userNo", userNo
                       , "price", price
                       , "buyerNo", buyerNo);
      
      addBidResult = auctionMapper2.insertRealBid(bidMap);
    }
    
    int buyerResult = auctionMapper2.insertBuyerEmoney(emoneyMap);
    int sellerResult = auctionMapper2.insertSellerEmoney(emoneyMap);
    int changeStatusResult = auctionMapper2.updateStatus(auctionNo);
    int resultSum = buyerResult + sellerResult + addBidResult + changeStatusResult;
    
    return resultSum;
    
  }
  
  @Transactional
  @Override
  public int addBid(BidDto bidDto) {
    
//    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
//    int bidderNo = Integer.parseInt(request.getParameter("bidderNo"));
//    int bidMoney = Integer.parseInt(request.getParameter("bidMoney"));
//    Map<String, Object> bidMap = Map.of("auctionNo", auctionNo
//                                        , "bidderNo", bidderNo
//                                        , "price", bidMoney);
//    Map<String, Object> emoneyMap = Map.of("buyerNo", bidderNo
//                                          , "price", bidMoney);
//    
//    System.out.println(auctionMapper2.getBidPrice(auctionNo));
//    
//    
//    int receiveUserNo= bidDto.getBidderDto().getUserNo();
//    System.out.println("receiveUserNo:" + receiveUserNo);
//    int returnMoney = bidDto.getPrice();
//    Map<String, Object> returnEmoneyMap = Map.of("userNo", receiveUserNo
//                                                 , "price", returnMoney);
//    
//    int returnResult = auctionMapper2.insertSellerEmoney(returnEmoneyMap);
//    int buyerResult = auctionMapper2.insertBuyerEmoney(emoneyMap);
//    int bidResult = auctionMapper2.insertBid(bidMap);
//    
//    int bidResultSum = buyerResult + bidResult;
//    
//    return bidResultSum;
    
    int bidResult = auctionMapper2.insertBid(bidDto);
    
    int auctionNo = bidDto.getAuctionNo();
    int bidderNo = bidDto.getBidderNo();
    int bidMoney = bidDto.getPrice();    
    System.out.println("price" + bidDto.getPrice() + "bidPrice" +bidDto.getBidPrice());
    Map<String, Object> emoneyMap = Map.of("buyerNo", bidderNo
                                         , "price", bidMoney);
    
    int buyerResult = auctionMapper2.insertBuyerEmoney(emoneyMap);

    int totalResult = buyerResult + bidResult;
    
    return totalResult;
    
  }
  
  @Transactional
  @Override
  public int getBidPrice(int auctionNo) {
    int bidprice = auctionMapper2.getBidPrice(auctionNo);
    System.out.println("bidprice서비스" + bidprice);
    return bidprice;
  }
  
  
  
}
