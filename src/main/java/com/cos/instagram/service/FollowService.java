package com.cos.instagram.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.instagram.domain.follow.FollowRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {
     private final FollowRepository followRepository; 
     
     //서비스에서 롤백하려면 throw를 runtimeException을 던저야함. 
     @Transactional
     public void 팔로우(int loginUserId, int pageUserId) {
    	 int result = followRepository.mFollow(loginUserId, pageUserId);
    	 System.out.println("팔로우 result:" + result);
     }
     
     @Transactional 
     public void 팔로우취소(int loginUserId, int pageUserId) { 
    	  int result = followRepository.mUnFollow(loginUserId, pageUserId);
    	  System.out.println("팔로우 취소 result :" + result);
     }
}
