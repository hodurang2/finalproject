package com.gdu.drawauction.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.drawauction.dao.DrawMapper;
import com.gdu.drawauction.dto.DrawDto;
import com.gdu.drawauction.util.MyFileUtils;
import com.gdu.drawauction.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

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
	    
	    return Map.of("drawList", drawList
	                , "totalPage", myPageUtils.getTotalPage());
	}
}
