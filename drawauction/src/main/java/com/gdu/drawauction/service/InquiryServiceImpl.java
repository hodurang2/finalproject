package com.gdu.drawauction.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.drawauction.dao.InquiryMapper;
import com.gdu.drawauction.dto.AnswerDto;
import com.gdu.drawauction.dto.InquiryAttachDto;
import com.gdu.drawauction.dto.InquiryDto;
import com.gdu.drawauction.dto.UserDto;
import com.gdu.drawauction.util.MyFileUtils;
import com.gdu.drawauction.util.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@RequiredArgsConstructor
@Service
public class InquiryServiceImpl implements InquiryService{
  
  private final InquiryMapper inquiryMapper;
  private final MyPageUtils myPageUtils;
  private final MyFileUtils myFileUtils;

  @Override
  public void loadInquiryList(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = inquiryMapper.getInquiryCount();
    int display = 5;
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<InquiryDto> inquiryList = inquiryMapper.getInquiryList(map);
    
    model.addAttribute("inquiryList", inquiryList);
    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/inquiry/list.do"));
    model.addAttribute("beginNo", total - (page - 1) * display);
    
  }
  
  @Override
  public InquiryDto getInquiry(int inquiryNo, Model model) {
    return inquiryMapper.getInquiry(inquiryNo);
  }
  
  @Override
  public int addInquiry(HttpServletRequest request) {
    
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    String inquiryType = request.getParameter("inquiryType");
    
    
    InquiryDto inquiry = InquiryDto.builder()
                          .title(title)
                          .content(content)
                          .userDto(UserDto.builder()
                                    .userNo(userNo)
                                    .build())
                          .inquiryType(inquiryType)
                          .build();
                          
    
    int addResult = inquiryMapper.insertInquiry(inquiry);
    System.out.println(inquiry.getInquiryNo());
    Document document = Jsoup.parse(content);
    Elements elements = document.getElementsByTag("img");

    if (elements != null) {
        for (Element element : elements) {
            String src = element.attr("src");
            String filesystemName = src.substring(src.lastIndexOf("/") + 1);
            InquiryAttachDto inquiryAttach = InquiryAttachDto.builder()
                    .inquiryNo(inquiry.getInquiryNo())
                    .path(myFileUtils.getInquiryPath())
                    .filesystemName(filesystemName)
                    .build();

            inquiryMapper.insertInquiryAttach(inquiryAttach);
        }
    }
    
    return addResult;
  }
  
  @Override
  public Map<String, Object> addAnswer(HttpServletRequest request) {
    
    int inquiryNo = Integer.parseInt(request.getParameter("inquiryNo"));
    String contents = request.getParameter("contents");
    
    
    AnswerDto answer = AnswerDto.builder()
                          .inquiryNo(inquiryNo)
                          .contents(contents)
                          .build();
    
    int addAnswerResult = inquiryMapper.insertAnswer(answer);
    
    return Map.of("addAnswerResult", addAnswerResult);
  }
    
  @Override
  public Map<String, Object> loadAnswerList(HttpServletRequest request) {

    int inquiryNo = Integer.parseInt(request.getParameter("inquiryNo"));
    int page = Integer.parseInt(request.getParameter("page"));
    int total = inquiryMapper.getAnswerCount(inquiryNo);
    int display = 10;
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("inquiryNo", inquiryNo
                                   , "begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<AnswerDto> answerList = inquiryMapper.getAnswerList(map);
    String paging = myPageUtils.getAjaxPaging();
    
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("answerList", answerList);
    result.put("paging", paging);
    return result;
  }
  
  @Override
  public Map<String, Object> addAnswerReply(HttpServletRequest request) {
    String contents = request.getParameter("contents");
    int inquiryNo = Integer.parseInt(request.getParameter("inquiryNo"));

    AnswerDto answer = AnswerDto.builder()
                          .inquiryNo(inquiryNo)
                          .contents(contents)
                          .build();
    
    int addAnswerReplyResult = inquiryMapper.insertAnswerReply(answer);
    
    return Map.of("addAnswerReplyResult", addAnswerReplyResult);
    
  }
  
  @Override
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {
    
    // 이미지가 저장될 경로
    String imagePath = myFileUtils.getInquiryPath();
    File dir = new File(imagePath);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    // 이미지 파일 (CKEditor는 이미지를 upload라는 이름으로 보냄)
    MultipartFile upload = multipartRequest.getFile("upload");
    
    // 이미지가 저장될 이름
    String originalFilename = upload.getOriginalFilename();
    String filesystemName = myFileUtils.getFilesystemName(originalFilename);
    
    // 이미지 File 객체
    File file = new File(dir, filesystemName);
    
    // 저장
    try {
      upload.transferTo(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // CKEditor로 저장된 이미지의 경로를 JSON 형식으로 반환해야 함
    return Map.of("uploaded", true
                , "url", multipartRequest.getContextPath() + imagePath + "/" + filesystemName);
  }
  
  @Override
  public Map<String, Object> addInquiryAttach(MultipartHttpServletRequest multipartRequest) throws Exception {
    
    List<MultipartFile> files =  multipartRequest.getFiles("files");
    
    int attachCount;
    if(files.get(0).getSize() == 0) {
      attachCount = 1;
    } else {
      attachCount = 0;
    }
    
    for(MultipartFile multipartFile : files) {
      
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        String path = myFileUtils.getUploadPath();
        File dir = new File(path);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        String originalFilename = multipartFile.getOriginalFilename();
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);
        File file = new File(dir, filesystemName);
        
        multipartFile.transferTo(file);
        
        String contentType = Files.probeContentType(file.toPath());  // 이미지의 Content-Type은 image/jpeg, image/png 등 image로 시작한다.
        int hasThumbnail = (contentType != null && contentType.startsWith("image")) ? 1 : 0;
        
        if(hasThumbnail == 1) {
          File thumbnail = new File(dir, "s_" + filesystemName);  // small 이미지를 의미하는 s_을 덧붙임
          Thumbnails.of(file)
                    .size(100, 100)      // 가로 100px, 세로 100px
                    .toFile(thumbnail);
        }
        
        InquiryAttachDto InquiryAttach = InquiryAttachDto.builder()
                            .path(path)
                            .originalFilename(originalFilename)
                            .filesystemName(filesystemName)
                            .hasThumbnail(hasThumbnail)
                            .inquiryNo(Integer.parseInt(multipartRequest.getParameter("inquiryNo")))
                            .build();
        
        attachCount += inquiryMapper.insertInquiryAttach(InquiryAttach);
        
      }  // if
      
    }  // for
    
    return Map.of("attachResult", files.size() == attachCount);
  }
  
  @Override
  public Map<String, Object> getInquiryAttachList(HttpServletRequest request) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("inquiryNo"));
    int inquiryNo = Integer.parseInt(opt.orElse("0"));
    
    return Map.of("inquiryAttachList", inquiryMapper.getInquiryAttachList(inquiryNo));
  }
  
  @Override
  public ResponseEntity<Resource> download(HttpServletRequest request) {
    
    // 첨부 파일의 정보 가져오기
    int inquiryattachNo = Integer.parseInt(request.getParameter("inquiryAttachNo"));
    InquiryAttachDto inquiryattach = inquiryMapper.getInquiryAttach(inquiryattachNo);
    
    // 첨부 파일 File 객체 -> Resource 객체
    File file = new File(inquiryattach.getPath(), inquiryattach.getFilesystemName());
    Resource resource = new FileSystemResource(file);
    
    // 첨부 파일이 없으면 다운로드 취소
    if(!resource.exists()) {
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }
    
    // 다운로드 횟수 증가하기
    inquiryMapper.updateDownloadCount(inquiryattachNo);
    
    // 사용자가 다운로드 받을 파일의 이름 결정 (User-Agent값에 따른 인코딩 처리)
    String originalFilename = inquiryattach.getOriginalFilename();
    String userAgent = request.getHeader("User-Agent");
    try {
      // IE
      if(userAgent.contains("Trident")) {
        originalFilename = URLEncoder.encode(originalFilename, "UTF-8").replace("+", " ");
      }
      // Edge
      else if(userAgent.contains("Edg")) {
        originalFilename = URLEncoder.encode(originalFilename, "UTF-8");
      }
      // Other
      else {
        originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    // 다운로드 응답 헤더 만들기
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/octet-stream");
    header.add("Content-Disposition", "attachment; filename=" + originalFilename);
    header.add("Content-Length", file.length() + "");
    
    // 응답
    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    
  }
  
  @Override
  public ResponseEntity<Resource> downloadAll(HttpServletRequest request) {
    
 // 다운로드 할 모든 첨부 파일 정보 가져오기
    int inquiryAttachNo = Integer.parseInt(request.getParameter("inquiryNo"));
    List<InquiryAttachDto> inquiryattachList = inquiryMapper.getInquiryAttachList(inquiryAttachNo);
    
    // 첨부 파일이 없으면 종료
    if(inquiryattachList.isEmpty()) {
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }
    
    // zip 파일을 생성할 경로
    File tempDir = new File(myFileUtils.getTempPath());
    if(!tempDir.exists()) {
      tempDir.mkdirs();
    }
    
    // zip 파일의 이름
    String zipName = myFileUtils.getTempFilename() + ".zip";
    
    // zip 파일의 File 객체
    File zipFile = new File(tempDir, zipName);
    
    // zip 파일을 생성하는 출력 스트림
    ZipOutputStream zout = null;
    
    // 첨부 파일들을 순회하면서 zip 파일에 등록하기
    try {
      
      zout = new ZipOutputStream(new FileOutputStream(zipFile));
      
      for(InquiryAttachDto inquiryAttach : inquiryattachList) {
        
        // 각 첨부 파일들의 원래 이름으로 zip 파일에 등록하기 (이름만 등록)
        ZipEntry zipEntry = new ZipEntry(inquiryAttach.getOriginalFilename());
        zout.putNextEntry(zipEntry);
        
        // 각 첨부 파일들의 내용을 zip 파일에 등록하기 (실제 파일 등록)
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(inquiryAttach.getPath(), inquiryAttach.getFilesystemName())));
        zout.write(bin.readAllBytes());
        
        // 자원 반납
        bin.close();
        zout.closeEntry();
        
        // 다운로드 횟수 증가
        inquiryMapper.updateDownloadCount(inquiryAttach.getInquiryAttachNo());
        
      }
      
      // zout 자원 반납
      zout.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // 다운로드할 zip 파일의 File 객체 -> Resource 객체
    Resource resource = new FileSystemResource(zipFile);
    
    // 다운로드 응답 헤더 만들기
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/octet-stream");
    header.add("Content-Disposition", "attachment; filename=" + zipName);
    header.add("Content-Length", zipFile.length() + "");
    
    // 응답
    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    
  }
}
