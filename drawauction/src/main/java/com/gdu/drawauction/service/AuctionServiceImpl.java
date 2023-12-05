package com.gdu.drawauction.service;

import java.io.File;
import java.nio.file.Files;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.drawauction.dao.AuctionMapper;
import com.gdu.drawauction.dto.ArtDto;
import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.AuctionImageDto;
import com.gdu.drawauction.dto.CategoryDto;
import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.DrawImageDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyFileUtils;
import com.gdu.drawauction.util.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

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
    
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin(), 
                                     "end", myPageUtils.getEnd() 
                                     );
    
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
    
    for(AuctionDto auctionDto : auctionList) {
      auctionDto.setImage(auctionMapper.getAuctionimage(auctionDto.getAuctionNo()));
    }
    
    
    return Map.of("auctionList", auctionList
                , "totalPage", myPageUtils.getTotalPage());
  }
  
  @Override
  public Map<String, Object> controlAuctionWishlist(HttpServletRequest request) {
    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
    HttpSession session = request.getSession();
    UserDto user = (UserDto) session.getAttribute("user");
    System.out.println(auctionNo);
    System.out.println(user);
    System.out.println(user.getUserNo());
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
 
  @Override
  public Map<String, Object> getSearchAuctionList(HttpServletRequest request) {
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = auctionMapper.getAuctionCount();
    int display = 9;
    
    myPageUtils.setPaging(page, total, display);
    
    String searchWord = request.getParameter("searchWord");
    String auctionType = request.getParameter("auctionType");
    int categoryNo = Integer.parseInt(request.getParameter("categoryNo"));
    int status = Integer.parseInt(request.getParameter("status"));
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin(), 
                                         "end", myPageUtils.getEnd(), 
                                         "searchWord", searchWord, 
                                         "auctionType", auctionType, 
                                         "categoryNo", categoryNo, 
                                         "status", status
                                         );
    
    List<AuctionDto> searchList = auctionMapper.searchAuctionList(map);
    
    int userNo;
    
    HttpSession session = request.getSession();
    
    if(session.getAttribute("user") == null) {
      for(AuctionDto auctionDto : searchList) {
        auctionDto.setHeartClass("fa-regular");
      }
    } else {
      UserDto user = (UserDto) session.getAttribute("user");
      userNo = user.getUserNo();
      for(AuctionDto auctionDto : searchList) {
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
    

    for(AuctionDto auctionDto : searchList) {
      auctionDto.setImage(auctionMapper.getAuctionimage(auctionDto.getAuctionNo()));
    }
    
    
    return Map.of("searchList", searchList
                , "totalPage", myPageUtils.getTotalPage());
  }
  
  @Override
  public boolean addAuction(MultipartHttpServletRequest multipartRequest) throws Exception {
      
    Optional<String> optWidth = Optional.ofNullable(multipartRequest.getParameter("width"));
    Optional<String> optHeight = Optional.ofNullable(multipartRequest.getParameter("height"));
    int width = Integer.parseInt(optWidth.orElse("0"));
    int height = Integer.parseInt(optHeight.orElse("0"));
    
    int categoryNo = Integer.parseInt(multipartRequest.getParameter("categoryNo")); 
    int artType = Integer.parseInt(multipartRequest.getParameter("artType"));
    Optional<String> optBuyPrice = Optional.ofNullable(multipartRequest.getParameter("buyPrice"));
    int buyPrice = Integer.parseInt(optBuyPrice.orElse("-1"));
    System.out.println(buyPrice);
    int startPrice = Integer.parseInt(multipartRequest.getParameter("startPrice")); 
    String endAtStr = multipartRequest.getParameter("endAt");
    String title = multipartRequest.getParameter("title");
    String contents = multipartRequest.getParameter("contents");
    
    HttpSession session = multipartRequest.getSession();
    UserDto seller = (UserDto) session.getAttribute("user");
    
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    LocalDateTime endAt = LocalDateTime.parse(endAtStr, inputFormatter);
    
    
     Map<String, Object> art = Map.of("title", title,
                            "contents", contents,
                            "artType", artType,
                            "width", width,
                            "height", height,
                            "sellerNo", seller.getUserNo(),
                            "categoryNo", categoryNo);
     auctionMapper.insertArt(art);
     
      
      Map<String, Object> auction = Map.of(
                            "startPrice", startPrice,
                            "buyPrice", buyPrice,
                            "endAt", endAt);
      int auctionCount = auctionMapper.insertAuction(auction);
      
      List<MultipartFile> files = multipartRequest.getFiles("files");
      
      // 첨부 없을 때 : [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]]
      // 첨부 1개     : [MultipartFile[field="files", filename="animal1.jpg", contentType=image/jpeg, size=123456]]
      
      int imageCount;
      if(files.get(0).getSize() == 0) {
        imageCount = 1;
      } else {
        imageCount = 0;
      }
      
      for(MultipartFile multipartFile : files) {
        
        if(multipartFile != null && !multipartFile.isEmpty()) {
          
          String path = myFileUtils.getAuctionImagePath();
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
          
          AuctionImageDto image = AuctionImageDto.builder()
                              .auctionNo(auctionMapper.getCurrentAuctionNo())
                              .path(path)
                              .filesystemName(filesystemName)
                              .imageOriginalName(imageOriginalName)
                              .hasThumbnail(hasThumbnail)
                              .build();
          
          imageCount += auctionMapper.insertImage(image);
        }  // if
      }  // for
      return (auctionCount == 1) && (files.size() == imageCount);
      
   }
  
}
