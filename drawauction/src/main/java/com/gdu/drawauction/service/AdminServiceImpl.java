package com.gdu.drawauction.service;


import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.drawauction.dao.AdminMapper;
import com.gdu.drawauction.dto.AuctionDto;
import com.gdu.drawauction.dto.AuctionImageDto;
import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	@Autowired
	private final AdminMapper adminMapper;
	private final MyPageUtils myPageUtils;
	  
	// 유저 정보 전부 가져오기
	@Override
	public void loadUserList(HttpServletRequest request, Model model) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		
		int page = Integer.parseInt(opt.orElse("1"));
		int total = adminMapper.getUserCount();
		int display = 10;
		
		myPageUtils.setPaging(page, total, display);
		
		
		Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
										, "end", myPageUtils.getEnd());
		
		List<UserDto> userList = adminMapper.getUserList(map);
		
		model.addAttribute("userList", userList);
		model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/admin/userList.do"));
	    model.addAttribute("beginNo", total - (page - 1) * display);
	}
	
	// 유저 기록 가져오기
	@Override
	public UserDto getUser(int userNo, Model model) {
		UserDto user = adminMapper.getUser(userNo);
		model.addAttribute("user", user);
		return user;
	}

 
	// 유저 삭제.
  @Override
  	public int removeUser(HttpServletRequest request) {
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    return adminMapper.deleteUser(userNo);
  }
  
  
	/////////////////////////////
	  // 그려드림 영역 //
	/////////////////////////////
	  
	  @Override
	  public void loadDrawList(HttpServletRequest request, Model model) {
	
	    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
	    
	    int page = Integer.parseInt(opt.orElse("1"));
	    int total = adminMapper.getDrawCount();
	    int display = 10;
	    
	    myPageUtils.setPaging(page, total, display);
	    
	    
	    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
	                    , "end", myPageUtils.getEnd());
	    
	    List<DrawDto> drawList = adminMapper.getDrawList(map);
	    
	    model.addAttribute("drawList", drawList);
	    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/admin/drawList.do"));
	      model.addAttribute("beginNo", total - (page - 1) * display);
	    
	    
	  }
	  
	  @Override
	  public DrawDto getDraw(int drawNo, Model model) {
	    DrawDto draw = adminMapper.getDraw(drawNo);
	    model.addAttribute("draw", draw);
	    return draw;
	  }
	  
	  // 그려드림 삭제
	  @Override
	  public int removeDraw(HttpServletRequest request) {
	    int drawNo = Integer.parseInt(request.getParameter("drawNo"));
	    return adminMapper.deleteDraw(drawNo);
	  }
	  
	  public void getAdminAucList(HttpServletRequest request, Model model) {
	  Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = adminMapper.getAdminAucCount();
    int display = 9;

    myPageUtils.setPaging(page, total, display);
    
    int begin = myPageUtils.getBegin();
    int end = myPageUtils.getEnd();
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<AuctionDto> adminAucList = adminMapper.getAdminAucList(map);

    model.addAttribute("adminAucList", adminAucList);
    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/admin/adminAucList.do"));
    model.addAttribute("beginNo", total - (page - 1) * display);

  }
	
	@Override
  public int removeAdminAuc(HttpServletRequest request) {
      // 파일 삭제
	    int auctionNo = Integer.parseInt(request.getParameter("auctionNo"));
	    System.out.println("서비스임플" + auctionNo);
      List<AuctionImageDto> imageList = adminMapper.getImageList(auctionNo);
      System.out.println("이미지리스트" + imageList);
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
      return adminMapper.deleteAdminAuc(auctionNo);
  }
	

	
}


