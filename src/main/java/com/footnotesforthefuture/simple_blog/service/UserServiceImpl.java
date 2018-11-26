package com.footnotesforthefuture.simple_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.footnotesforthefuture.simple_blog.model.User;
import com.footnotesforthefuture.simple_blog.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Flux<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Mono<User> findByUsername(String username) {
		return userRepository.findFirstByUsername(username);
	}

	@Override
	public Mono<User> save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}
