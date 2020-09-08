package com.cos.instagram.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.instagram.domain.notice.Notice;
import com.cos.instagram.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NoticeController {
      private final NoticeService noticeService; 
      
      @GetMapping("/notice/{loginUserId}")
      public String notice(@PathVariable int loginUserId, Model model) {
    	   model.addAttribute("notice", noticeService.알림리스트(loginUserId)); 
    	   return "notice/notice";
      }
      
      @GetMapping("/test/notice/{loginUserId}")
      public @ResponseBody List<Notice> testNotice(@PathVariable int loginUserId, Model model){
    	   return noticeService.알림리스트(loginUserId);
      }
}
