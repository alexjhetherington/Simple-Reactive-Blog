package com.footnotesforthefuture.simple_blog.service;

import com.footnotesforthefuture.simple_blog.model.Post;
import com.footnotesforthefuture.simple_blog.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
	Flux<Post> findAllPosts();
	Flux<Post> findByAuthor(User user);
	Mono<Post> findById(String id);
	Mono<Post> save(Post post);
	Mono<Void> deleteById(String id);
}
