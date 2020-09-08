package com.cos.instagram.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.domain.notice.Notice;
import com.cos.instagram.domain.notice.NoticeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeService {
      private final NoticeRepository noticeRepository;
      
      @Transactional(readOnly = true)
      public List<Notice> 알림리스트(int loginUserId){
    	   return noticeRepository.findByToUserId(loginUserId);
      }
}
