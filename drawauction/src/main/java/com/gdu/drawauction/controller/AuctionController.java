package com.gdu.drawauction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.drawauction.service.AuctionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auction")
@RequiredArgsConstructor
@Controller
public class AuctionController {
  private final AuctionService auctionService;
  
  
}
