package com.cos.instagram.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.tag.Tag;
import com.cos.instagram.domain.tag.TagRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.domain.user.UserRole;

@RestController
public class TestApiController {
      
	  @Autowired
	  private UserRepository userRepository;
	  
	  @Autowired
	  private ImageRepository imageRepository;
	  
	  @Autowired
	  private TagRepository tagRepositoty;
	
	  @GetMapping("/test/api/join")
	  public User join() {
		  User user = User.builder()
				  .name("홍길동")
				  .password("1234")
				  .phones("0102323")
				  .bio("안녕 난 코스야")
				  .role(UserRole.USER)
				  .build();
			User userEntity = userRepository.save(user);
			return userEntity;
		  
	  }
	  
	  @GetMapping("/test/api/image")
	  public String image() {
		  User userEntity = userRepository.findById(1).get();
		  Image image = Image.builder()
				  .location("다낭")
				  .caption("설명")
				
				  .user(userEntity)
				  .build();

		  Image imageEntity=  imageRepository.save(image);
		   
		  
		  List<Tag> tags = new ArrayList<>();
		  Tag tag1 = Tag.builder()
				  .name("#다낭")
				  .image(imageEntity)
				  .build();
	
		  Tag tag2 = Tag.builder()
		  .name("#여행")
		  .image(imageEntity)
		  .build();
		  tags.add(tag1);
		  tags.add(tag2);
		
		  tagRepositoty.saveAll(tags);
		  
		  //List<Image> images = imageRepository.findAll();
		    return "image insert 잘됨"; //MessageConverter의 Jackson
	  }
	  @GetMapping("/test/api/image/list")
	  public List<Image> list(){
		  return imageRepository.findAll();
	  }
	  @GetMapping("/test/api/tag/list")
	  public List<Tag> tagList(){
		  return tagRepositoty.findAll();
	  }
	  
}
