package com.gdu.drawauction.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtils {

  // 공지 작성시 사용된 이미지가 저장될 경로 반환하기
  public String getNoticeImagePath() {
    LocalDate today = LocalDate.now();
    return "/notice/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(today);
  }
  
  // 1:1문의 작성시 사용된 파일이 저장될 경로 반환하기
  public String getInquiryPath() {
    LocalDate today = LocalDate.now();
    return "/inquiry/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(today);
  }
  
  //경매 게시판 작성시 첨부한 파일이 저장될 경로 반환하기
  public String getAuctionImagePath() {
    LocalDate today = LocalDate.now();
    return "/auction/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(today);
  }
  //그려드림 게시판 작성시 첨부한 파일이 저장될 경로 반환하기
  public String getDrawImagePath() {
    LocalDate today = LocalDate.now();
    return "/draw/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(today);
  }
  
  // 임시 파일이 저장될 경로 반환하기 (zip 파일)
  public String getTempPath() {
    return "/temporary";
  }
  
  // 파일이 저장될 이름 반환하기
  public String getFilesystemName(String originalFilename) {
    
    /*  UUID.확장자  */
    
    String extName = null;
    if(originalFilename.endsWith("tar.gz")) {  // 확장자에 마침표가 포함되는 예외 경우를 처리한다.
      extName = "tar.gz";
    } else {
      String[] arr = originalFilename.split("\\.");  // [.] 또는 \\.
      extName = arr[arr.length - 1];
    }
    
    return UUID.randomUUID().toString().replace("-", "") + "." + extName;
    
  }

  // 임시 파일 이름 반환하기 (확장자는 제외하고 이름만 반환)
  public String getTempFilename() {
    return System.currentTimeMillis() + "";
  }
  
}
