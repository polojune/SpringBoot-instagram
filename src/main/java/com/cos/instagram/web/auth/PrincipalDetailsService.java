package com.cos.instagram.web.auth;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.instagram.config.oauth.dto.LoginUser;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    
	private static final Logger log = LoggerFactory.getLogger(PrincipalDetailsService.class);

	private final UserRepository userRepository;
	private final HttpSession session;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername : username : " + username);
		User userEntity = userRepository.findByUsername(username).get();
		
		if(userEntity != null) {
			System.out.println("ㅇㅇㅇㅇ?");
			session.setAttribute("loginUser", new LoginUser(userEntity));
		}
				
		        		return new PrincipalDetails(userEntity);
	}
   
}
