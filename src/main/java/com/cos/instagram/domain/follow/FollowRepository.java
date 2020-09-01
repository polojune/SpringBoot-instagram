package com.cos.instagram.domain.follow;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
     
	
	 @Query(value = "SELECT count(*) FROM follow WHERE toUserId = ?1", nativeQuery = true)
	 int mCountByFollower(int toUserId); 
	
	 @Query(value ="SELECT count(*) FROM follow WHERE fromUserId = ?1", nativeQuery = true)
	 int mCountByFollowing(int fromUserId); 
	 
	 @Query(value ="SELECT count(*) FROM follow WHERE fromUserId = ?1 and toUserId = ?2", nativeQuery = true )
	 int mFollowState(int loginUserId, int pageUserId);
	 
	 //수정,삭제, 추가시에는 트랜잭션 어노테이션 필요 @Transaction
	 //수정시는 modify 어노테이션 필요 @Modifying
	 
	 @Modifying
	 @Query(value = "INSERT INTO follow(fromUserId, toUserId) VALUES(?1,?2)",nativeQuery = true) 
	 int mFollow(int loginUserId, int pageUserId); 
	 
	 @Modifying
	 @Query(value = "DELETE FROM follow WHERE fromUserId = ?1 AND toUserId = ?2", nativeQuery = true)
	 int mUnFollow(int loginUserId, int pageUserId);  	
}
