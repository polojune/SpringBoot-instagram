package com.cos.instagram.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.config.handler.ex.MyImageIdNotFoundException;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.like.LikesRepository;
import com.cos.instagram.domain.notice.NoticeRepository;
import com.cos.instagram.domain.notice.NoticeType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
        private final LikesRepository likesRepository;
        private final NoticeRepository noticeRepository;
        private final ImageRepository imageRepository;
        
        
        @Transactional 
        public void 좋아요(int imageId, int loginUserId) {
        	 likesRepository.mSave(imageId, loginUserId);
        	 Image imageEntity = imageRepository.findById(imageId).orElseThrow(new Supplier<MyImageIdNotFoundException>() {

				@Override
				public MyImageIdNotFoundException get() {
				    
					return new MyImageIdNotFoundException();
				}
			}); 
        noticeRepository.mSave(loginUserId, imageEntity.getUser().getId(), NoticeType.LIKE.name());	 
        }
        
        @Transactional
        public void 싫어요(int imageId, int loginUserId) {
        	likesRepository.mDelete(imageId, loginUserId);
        }
}
