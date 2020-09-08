package com.cos.instagram.domain.notice;

import lombok.Getter;

@Getter
public enum NoticeType {
      LIKE("좋아요"), COMMENT("댓글작성"), FOLLOW("팔로우");
	
	  NoticeType(String key) {
		    this.key = key; 
	}
	 private String key;  
}
