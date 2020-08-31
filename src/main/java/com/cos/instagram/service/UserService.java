package com.cos.instagram.service;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.type.TrueFalseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.config.handler.ex.MyUserIdNotFoundException;
import com.cos.instagram.config.oauth.dto.LoginUser;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.web.dto.JoinReqDto;
import com.cos.instagram.web.dto.UserProfileRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
     
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public void 회원가입(JoinReqDto joinReqDto) {
		String encPassword = bCryptPasswordEncoder.encode(joinReqDto.getPassword());
		joinReqDto.setPassword(encPassword);
		userRepository.save(joinReqDto.toEntity());

	}

	// readOnly true를 걸면 트랙잭션이 시작되는데 DB변경을 할 수는 없다.
	// 읽기 전용 트랜잭션
	// 1.변경 감지 연산을 하지 않음.
	// 2.isolation(고립성)을 위해 phantom read 현상이 일어나지 않음
	@Transactional(readOnly = true)
	public UserProfileRespDto 회원프로필(int id, LoginUser loginUser) {
		int imageCount;
		int followerCount;
		int followingCount;
		
						
		User userEntity = userRepository.findById(id).orElseThrow(new Supplier<MyUserIdNotFoundException>() {

			@Override
			public MyUserIdNotFoundException get() {

				return new MyUserIdNotFoundException();
			}
		});
           //1.이미지 카운트
	       List<Image> imagesEntity = imageRepository.findByUserId(id);	
	       imageCount = imagesEntity.size();
	       
	       //2.팔로우 수 (나중에 수정해야함)
	       followerCount = 50; 
	       followingCount = 100;
	      
	       
	       System.out.println();
	 
	       
	       //3.이미지들 
	       
	       //4.최종마무리 
//	       userEntity.setFollowerCount(followerCount);
//	       userEntity.setFollowingCount(followingCount);
//	       userEntity.setImageCount(imageCount);
//          dto.setFollower(followerCount);
//          dto.setFollowing(followingCount);
//          dto.setImageCount(imageCount);
//          dto.setImage(imagesEntity);
//          dto.setUser(userEntity);
	       UserProfileRespDto userProfileRespDto = UserProfileRespDto.builder()
                            .pageHost(id==loginUser.getId())
	    		            .followerCount(followerCount)
                            .followingCount(followingCount)
                            .user(userEntity)
                            .images(imagesEntity)//수정필요 (댓글수 ,좋아요수)
                            .imageCount(imageCount)
                            .build();
	       
	       return userProfileRespDto;
	}

}
