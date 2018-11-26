package com.footnotesforthefuture.simple_blog.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.footnotesforthefuture.simple_blog.model.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
	Mono<User> findFirstByUsername(String username);
	Mono<User> findFirstById(String id);
}
