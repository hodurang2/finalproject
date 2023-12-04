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

import com.gdu.drawauction.dao.DrawMapper;
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
public class DrawServiceImpl implements DrawService{
	
	private final DrawMapper drawMapper;
	private final MyFileUtils myFileUtils;
	private final MyPageUtils myPageUtils;
	
	// 그려드림 목록 전체 조회
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> getDrawList(HttpServletRequest request) {
	
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
	    int page = Integer.parseInt(opt.orElse("1"));
	    int total = drawMapper.getDrawCount();
	    int display = 9;
	    
	    myPageUtils.setPaging(page, total, display);
	    
	    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
	                                   , "end", myPageUtils.getEnd());
	    
	    List<DrawDto> drawList = drawMapper.getDrawList(map);
	    
	    int userNo;
	    
	    HttpSession session = request.getSession();
	    
	    if(session.getAttribute("user") == null) {
	        for(DrawDto drawDto : drawList) {
	          drawDto.setHeart("fa-regular");
	        }
	      } else {
	        UserDto user = (UserDto) session.getAttribute("user");
	        userNo = user.getUserNo();
	        for(DrawDto drawDto : drawList) {
	          Map<String, Object> wishMap = Map.of("drawNo", drawDto.getDrawNo(), "userNo", userNo);
	          int wishCheck = drawMapper.wishCheck(wishMap);
	          String heart;
	          if(wishCheck == 0) {
	        	  heart = "fa-regular";
	          } else {
	        	  heart = "fa-solid";
	          }
	          drawDto.setHeart(heart);
	        }
	      }

	    
	    return Map.of("drawList", drawList
	                , "totalPage", myPageUtils.getTotalPage());
	}
	
	// 그려드림 게시글 작성
	@Override
	public boolean addDraw(MultipartHttpServletRequest multipartRequest) throws Exception {
	    
		int categoryNo = Integer.parseInt(multipartRequest.getParameter("categoryNo")); 
	    String title = multipartRequest.getParameter("title");
	    int price = Integer.parseInt(multipartRequest.getParameter("price")); 
	    int width = Integer.parseInt(multipartRequest.getParameter("width"));
	    int height = Integer.parseInt(multipartRequest.getParameter("height"));
	    int workTerm = Integer.parseInt(multipartRequest.getParameter("workTerm"));
	    String contents = multipartRequest.getParameter("contents");
	    int sellerNo = Integer.parseInt(multipartRequest.getParameter("sellerNo"));
	    
	    DrawDto draw = DrawDto.builder()
	                        .categoryDto(CategoryDto.builder()
	                        				.categoryNo(categoryNo)
	                        				.build())
	                        .title(title)
	                        .price(price)
	                        .width(width)
	                        .height(height)
	                        .workTerm(workTerm)
	                        .contents(contents)
	                        .userDto(UserDto.builder()
	                                  .userNo(sellerNo)
	                                  .build())
	                        .build();
	    
	    int drawCount = drawMapper.insertDraw(draw);
	    
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
	                    .size(500, 500)      // 가로 100px, 세로 100px
	                    .toFile(thumbnail);
	        }
	        
	        DrawImageDto image = DrawImageDto.builder()
	                            .path(path)
	                            .imageOriginalName(imageOriginalName)
	                            .filesystemName(filesystemName)
	                            .hasThumbnail(hasThumbnail)
	                            .drawNo(draw.getDrawNo())
	                            .build();
	        
	        imageCount += drawMapper.insertImage(image);
	      }  // if
	    }  // for
	    return (drawCount == 1) && (files.size() == imageCount);
	    
	 }
	
	// 그려드림 상세보기
	@Transactional(readOnly=true)
	@Override
	public void loadDraw(HttpServletRequest request, Model model) {
	    
	  Optional<String> opt = Optional.ofNullable(request.getParameter("drawNo"));
	  int drawNo = Integer.parseInt(opt.orElse("0"));
	
	  model.addAttribute("draw", drawMapper.getDraw(drawNo));
	  model.addAttribute("imageList", drawMapper.getImageList(drawNo));
	    
	}
	
	// 그려드림 찜목록 insert, delete
	@Override
	public ResponseEntity<Map<String, Object>> WishListControll(HttpServletRequest request) {
	  
	  int drawNo = Integer.parseInt(request.getParameter("drawNo")); 
	  HttpSession session = request.getSession();
	  UserDto user = (UserDto)session.getAttribute("user");
	  int userNo = user.getUserNo();
	  Map<String, Object> map = Map.of("drawNo", drawNo, "userNo", userNo);
	  
	  int wishCheckResult = drawMapper.wishCheck(map);
	  
	  if(wishCheckResult == 0) {
		int addWishResult = drawMapper.addWishList(map);
		return new ResponseEntity<>(Map.of("addWishResult", addWishResult), HttpStatus.OK);
	  } else {
		int removeWishResult =drawMapper.removeWishList(map);
		return new ResponseEntity<>(Map.of("removeWishResult", removeWishResult), HttpStatus.OK);
	  }
	  
	  
		
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> wishCheck(HttpServletRequest request) {
	  
	  int drawNo = Integer.parseInt(request.getParameter("drawNo")); 
	  int userNo = Integer.parseInt(request.getParameter("userNo"));
	  
	  Map<String, Object> map = Map.of("drawNo", drawNo, "userNo", userNo);
	  
	  int wishCheckResult = drawMapper.wishCheck(map);
	  
	  return new ResponseEntity<>(Map.of("wishCheckResult", wishCheckResult), HttpStatus.OK);
	}
	
	@Transactional(readOnly=true)
    @Override
    public void getDraw(HttpServletRequest request, Model model) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("drawNo"));
		int drawNo = Integer.parseInt(opt.orElse("0"));
		
		model.addAttribute("draw", drawMapper.getDraw(drawNo));
		model.addAttribute("imageList", drawMapper.getImageList(drawNo));
    }
	
	@Override
	public int modifyDraw(HttpServletRequest request) {
		
		int categoryNo = Integer.parseInt(request.getParameter("categoryNo"));
		String title = request.getParameter("title");
		int price = Integer.parseInt(request.getParameter("price"));
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		int workTerm = Integer.parseInt(request.getParameter("workTerm"));
		String contents = request.getParameter("contents");
		int drawNo = Integer.parseInt(request.getParameter("drawNo"));
		
		Map<String, Object> map = Map.of("categoryNo", categoryNo
										, "title", title
										, "price", price
										, "width", width
										, "height", height
										, "workTerm", workTerm
										, "contents", contents
										, "drawNo", drawNo);
		
	    return drawMapper.updateDraw(map);
	}
	
	@Override
	public Map<String, Object> getImageList(HttpServletRequest request) {
	    
	    Optional<String> opt = Optional.ofNullable(request.getParameter("drawNo"));
	    int drawNo = Integer.parseInt(opt.orElse("0"));
	    
	    return Map.of("imageList", drawMapper.getImageList(drawNo));
	    
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
	        
	        DrawImageDto image = DrawImageDto.builder()
	                            .path(path)
	                            .imageOriginalName(imageOriginalName)
	                            .filesystemName(filesystemName)
	                            .hasThumbnail(hasThumbnail)
	                            .drawNo(Integer.parseInt(multipartRequest.getParameter("drawNo")))
	                            .build();
	        
	        ImageCount += drawMapper.insertImage(image);
	        
	      }  // if
	      
	    }  // for
	    
	    return Map.of("imageResult", files.size() == ImageCount);
	    
	}
	
	@Override
	public Map<String, Object> removeImage(HttpServletRequest request) {
	    
	    Optional<String> opt = Optional.ofNullable(request.getParameter("drawImageNo"));
	    int drawImageNo = Integer.parseInt(opt.orElse("0"));
	    
	    DrawImageDto image = drawMapper.getImage(drawImageNo);
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
	    
	    int removeResult = drawMapper.deleteImage(drawImageNo);
	    
	    return Map.of("removeResult", removeResult);
	    
	  }
	
	@Override
	public int removeDraw(int drawNo) {
	    
	    // 파일 삭제
	    List<DrawImageDto> imageList = drawMapper.getImageList(drawNo);
	    for(DrawImageDto image : imageList) {
	      
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
	    return drawMapper.deleteDraw(drawNo);
	}
	
}
