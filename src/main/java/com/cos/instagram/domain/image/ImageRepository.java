package com.cos.instagram.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {
       
	List<Image> findByUserId(int userId);
	
	//내가 팔로우 하지 않은 사람들의 이미지들(최대20개) 
	@Query(value = "SELECT * FROM image i WHERE i.userId not in (SELECT f.toUserId FROM follow f WHERE f.fromUserId = ?1) AND i.userId != ?1 limit 20", nativeQuery = true)
	List<Image> mNonFollowImage(int loginUserId);
}
