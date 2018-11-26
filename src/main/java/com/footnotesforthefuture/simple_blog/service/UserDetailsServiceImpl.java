package com.footnotesforthefuture.simple_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.footnotesforthefuture.simple_blog.model.SecurityUserDetails;
import com.footnotesforthefuture.simple_blog.model.User;
import com.footnotesforthefuture.simple_blog.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService{

	private final UserRepository userRepository;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public Mono<UserDetails> findByUsername(String username) {
		Mono<User> userMono = userRepository.findFirstByUsername(username);
		return userMono.map(user -> new SecurityUserDetails(user));
	}
}
