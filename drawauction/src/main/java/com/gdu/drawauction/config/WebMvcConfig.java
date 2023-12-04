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
      .addPathPatterns("/free/write.form")
      .addPathPatterns("/blog/write.form", "/blog/edit.form", "/blog/remove.do")
      .addPathPatterns("/draw/write.form", "/draw/edit.form", "/draw/remove.do", "/draw/WishListControll.do");
    registry.addInterceptor(shouldNotLoginInterceptor)
      .addPathPatterns("/user/agree.form", "/user/join.form", "/user/login.form");
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
      .addResourceLocations("classpath:/static/", "classpath:/templates/");
    registry.addResourceHandler("/draw/**")
      .addResourceLocations("file:/draw/");
    registry.addResourceHandler("/upload/**")
    .addResourceLocations("file:/upload/");
  }
  
}
