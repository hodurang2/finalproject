package com.gdu.drawauction.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    int display = 10;
    
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
    String contents = request.getParameter("contents");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    
    InquiryDto inquiry = InquiryDto.builder()
                          .title(title)
                          .content(contents)
                          .userDto(UserDto.builder()
                                    .userNo(userNo)
                                    .build())
                          .build();
                          
    
    int addResult = inquiryMapper.insertInquiry(inquiry);
    System.out.println(inquiry.getInquiryNo());
    Document document = Jsoup.parse(contents);
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
    System.out.println(inquiryNo);
    String contents = request.getParameter("contents");
    System.out.println(contents);
    
    
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
  
  
}
