package com.cos.instagram.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.oauth.dto.LoginUser;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.service.ImageService;
import com.cos.instagram.web.dto.ImageReqDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {
    
	private final ImageService imageService;
	
	@GetMapping({ "", "/", "/image/feed" })
	public String feed(@LoginUserAnnotation LoginUser loginUser) {
 		System.out.println("loginUser : " + loginUser);
		return "image/feed";
	}
	
	@GetMapping("/test/image/feed")
	public List<Image> testFeed(@LoginUserAnnotation LoginUser loginUser) {
 		List<Image> images = imageService.피드사진(loginUser.getId());
		return null;
	}
	
	
	 @GetMapping("/image/UploadForm")
	 public String imageUploadForm() {
		 return "image/image-upload";
	 }
	 
	 @PostMapping("/image")
	 public String imageUpload(@LoginUserAnnotation LoginUser loginUser, ImageReqDto imageReqDto) {
		 System.out.println(imageReqDto);
		 imageService.사진업로드(imageReqDto, loginUser.getId());
		 return "redirect:/";
	 }
	 
	 @GetMapping("/image/explore")
	 public String imageExplore(@LoginUserAnnotation LoginUser loginUser, Model model) {
	      model.addAttribute("images", imageService.인기사진(loginUser.getId()));
		 return "image/explore";
	 }
}
