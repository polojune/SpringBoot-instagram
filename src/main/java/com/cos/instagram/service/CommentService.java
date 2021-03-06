package com.cos.instagram.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.config.handler.ex.MyImageIdNotFoundException;
import com.cos.instagram.domain.comment.CommentRepository;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.notice.NoticeRepository;
import com.cos.instagram.domain.notice.NoticeType;
import com.cos.instagram.web.dto.CommentRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
      
	private final CommentRepository commentRepository;
	private final NoticeRepository noticeRepository;
	private final ImageRepository imageRepository;
	
	@Transactional
	public void 댓글쓰기(CommentRespDto commentRespDto) {
		commentRepository.mSave(
				  commentRespDto.getUserId(),
				  commentRespDto.getImageId(), 
				  commentRespDto.getContent());
		Image imageEntity = imageRepository.findById(commentRespDto.getImageId()).orElseThrow(new Supplier<MyImageIdNotFoundException>() {

			@Override
			public MyImageIdNotFoundException get() {
				
				return new MyImageIdNotFoundException();
			}
		});
		noticeRepository.mSave(commentRespDto.getUserId(), imageEntity.getUser().getId(), NoticeType.COMMENT.name());
	}
	
	@Transactional 
	public void 댓글삭제(int id) {
		 commentRepository.deleteById(id);
	}
}
