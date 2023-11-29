package com.gdu.drawauction.service;

import org.springframework.stereotype.Service;

import com.gdu.drawauction.dao.MypageMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MypageServiceImpl implements MypageService {

  private final MypageMapper mypageMapper;
  
  
}
