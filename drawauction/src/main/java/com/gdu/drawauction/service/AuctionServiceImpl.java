package com.gdu.drawauction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.drawauction.dao.AuctionMapper;
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
}
