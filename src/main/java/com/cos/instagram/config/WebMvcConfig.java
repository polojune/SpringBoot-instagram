package com.cos.instagram.config;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.cos.instagram.config.auth.CosAnnotation;
import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.oauth.dto.LoginUser;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
      
	 private final HttpSession httpSession; 
	 
	 @Value("${file.path}")
	 private String uploadFolder;
	 
	 @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry 
		    .addResourceHandler("/upload/**")
		    .addResourceLocations("file:///" + uploadFolder)
		    .setCachePeriod(3600)
		    .resourceChain(true)
		    .addResolver(new PathResourceResolver());
		    
		
	}
	 
	 
	 
	 @Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
	     resolvers.add(new HandlerMethodArgumentResolver() {
			
			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				  boolean isCosUserAnnotation = parameter.getParameterAnnotation(CosAnnotation.class)!=null; 
				  boolean isStringClass = String.class.equals(parameter.getParameterType());
				  
				
				return isCosUserAnnotation && isStringClass;
			}
			
			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				// TODO Auto-generated method stub
				return "코스";
			}
		});
		 
		 resolvers.add(new HandlerMethodArgumentResolver() {
		
		
			
			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUserAnnotation.class)!=null;
				boolean isUserClass = LoginUser.class.equals(parameter.getParameterType());
				
				return isLoginUserAnnotation && isUserClass;
			}
			
			//2.세션을 만들어서 @LoginUserAnnotation 에 주입해준다.
			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				
				return httpSession.getAttribute("loginUser");
			}
		});
		 
		 
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}
}
