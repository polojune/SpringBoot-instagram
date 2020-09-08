package com.cos.instagram.domain.notice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Integer>{
      
	@Modifying
	@Query(value = "INSERT INTO notice(fromUserId,toUserId,noticeType,createDate) VALUES(?1, ?2, ?3, now())",nativeQuery = true)
	int mSave(int fromUserId, int toUserId, String noticeType); 
	
	List<Notice> findByToUserId(int loginUserId);
}
