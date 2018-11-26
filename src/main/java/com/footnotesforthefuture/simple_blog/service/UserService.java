package com.footnotesforthefuture.simple_blog.service;

import com.footnotesforthefuture.simple_blog.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	Flux<User> findAllUsers();
	Mono<User> findByUsername(String username);
	Mono<User> findById(String id);
	Mono<User> save(User user);
}
