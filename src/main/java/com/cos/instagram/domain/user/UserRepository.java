package com.cos.instagram.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


//JpaRepository가 extends되면 @Repository필요없음  IOC자동으로 됨
public interface UserRepository extends JpaRepository<User, Integer> {
	 Optional<User> findByUsername(String username);
}
