package com.cos.instagram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.instagram.config.handler.ex.MyUserIdNotFoundException;
import com.cos.instagram.config.oauth.dto.LoginUser;
import com.cos.instagram.domain.follow.FollowRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.web.dto.JoinReqDto;
import com.cos.instagram.web.dto.UserProfileImageRespDto;
import com.cos.instagram.web.dto.UserProfileRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
	@PersistenceContext
	private EntityManager em;
	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${file.path}") 
	private String uploadFolder;
	
	@Transactional
	public void 프로필사진업로드(LoginUser loginUser, MultipartFile file) {
		   UUID uuid = UUID.randomUUID();
		   String imageFilename = uuid+"_"+file.getOriginalFilename(); 
		   Path imageFilepath = Paths.get(uploadFolder+ imageFilename); 
		   try {
			     Files.write(imageFilepath, file.getBytes());
		} catch (Exception e) {
	        e.printStackTrace();
		}
		
      User userEntity = userRepository.findById(loginUser.getId()).orElseThrow(new Supplier<MyUserIdNotFoundException>() {

		@Override
		public MyUserIdNotFoundException get() {
			
			return new MyUserIdNotFoundException();
		}
	});
      //더티체킹 
      userEntity.setProfileImage(imageFilename);
	}
	
	@Transactional
	public void 회원수정(User user) {
		 //더티 체킹 
		User userEntity = userRepository.findById(user.getId()).orElseThrow(new Supplier<MyUserIdNotFoundException>() {

			@Override
			public MyUserIdNotFoundException get() {
				
				return new MyUserIdNotFoundException();
			}
		});
		userEntity.setName(user.getName());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setBio(user.getBio());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
	}
	
	@Transactional
	public void 회원가입(JoinReqDto joinReqDto) {
		System.out.println("서비스 회원가입 들어옴");
		System.out.println(joinReqDto);
		String encPassword = bCryptPasswordEncoder.encode(joinReqDto.getPassword());
		System.out.println("encPassword : "+encPassword);
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
		boolean followState;
		
						
		User userEntity = userRepository.findById(id).orElseThrow(new Supplier<MyUserIdNotFoundException>() {

			@Override
			public MyUserIdNotFoundException get() {

				return new MyUserIdNotFoundException();
			}
		});
           //1.이미지 카운트
		 StringBuilder sb = new StringBuilder(); 
		 sb.append("select im.id, im.imageUrl, ");
		 sb.append("(select count(*) from likes lk where lk.imageId = im.id) as likeCount,");
		 sb.append("(select count(*) from comment ct where ct.imageId = im.id) as commentCount ");
		 sb.append(" from image im where im.userId = ?");		
	     String q = sb.toString();		
	  
		Query query = em.createNativeQuery(q, "UserProfileImageRespDtoMapping").setParameter(1, id);
		List<UserProfileImageRespDto> imagesEntity = query.getResultList();
		
		
		
//	       List<Image> imagesEntity = imageRepository.findByUserId(id);	
	       imageCount = imagesEntity.size();
	       
	       //2.팔로우 수 
	       followerCount = followRepository.mCountByFollower(id); 
	       followingCount = followRepository.mCountByFollowing(id);
	       followState = followRepository.mFollowState(loginUser.getId(), id)== 1? true:false;
	       
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
                            .followState(followState)
	    		            .followerCount(followerCount)
                            .followingCount(followingCount)
                            .user(userEntity)
                            .images(imagesEntity)//수정완료 (댓글수 ,좋아요수)
                            .imageCount(imageCount)
                            .build();
	       
	       return userProfileRespDto;
	}
	
	@Transactional(readOnly = true)
	public User 회원정보(LoginUser loginUser) {
		return userRepository.findById(loginUser.getId()).orElseThrow(new Supplier<MyUserIdNotFoundException>() {

			@Override
			public MyUserIdNotFoundException get() {
				
				return new MyUserIdNotFoundException();
			}
		});
	
	}

}
