package com.gdu.drawauction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.drawauction.intercept.RequiredLoginInterceptor;
import com.gdu.drawauction.intercept.ShouldNotLoginInterceptor;

import lombok.RequiredArgsConstructor;

@EnableWebMvc
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final RequiredLoginInterceptor requiredLoginInterceptor;
  private final ShouldNotLoginInterceptor shouldNotLoginInterceptor;
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requiredLoginInterceptor)
      .addPathPatterns("/user/mypage.form", "/user/modifyPw.form")
      .addPathPatterns("/draw/write.form", "/draw/edit.form", "/draw/remove.do", "/draw/WishListControll.do", "/draw/orderPayment.form")
      .addPathPatterns("/auction/write.form", "/auction2/detail.do")
      .addPathPatterns("/mypage/detail.do", "/mypage/modify.form", "/mypage/modifyPw.form", "/mypage/getAuctionBidList.do", "/mypage/getAuctionSalesList.do"
                     , "/mypage/drawList.do", "/mypage/getMyDrawList.do", "/mypage/getDrawOrderList.do", "/mypage/getDrawReceivedOrderList.do"
                     , "/mypage/charge.do", "/mypage/getEmoneyList.do"
                     , "/mypage/getWishList.do", "/mypage/getAuctionWishList.do"
                     , "/mypage/controlAuctionWish.do", "/mypage/getDrawWishList.do"
                     , "/mypage/controlDrawWish.do");
      
    registry.addInterceptor(shouldNotLoginInterceptor)
      .addPathPatterns("/user/agree.form", "/user/join.form", "/user/login.form");
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
      .addResourceLocations("classpath:/static/", "classpath:/templates/");
    registry.addResourceHandler("/drawauction/draw/**")
      .addResourceLocations("file:/drawauction/draw/");
    registry.addResourceHandler("/drawauction/auction/**")
    .addResourceLocations("file:/drawauction/auction/");
    registry.addResourceHandler("/drawauction/inquiry/**")
    .addResourceLocations("file:/drawauction/inquiry/");
    registry.addResourceHandler("/drawauction/draw/**")
      .addResourceLocations("file:/drawauction/draw/");
    registry.addResourceHandler("/drawauction/auction/**")
    .addResourceLocations("file:/drawauction/auction/");
    registry.addResourceHandler("/drawauction/inquiry/**")
    .addResourceLocations("file:/drawauction/inquiry/");
  }
  
}
