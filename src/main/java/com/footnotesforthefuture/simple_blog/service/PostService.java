package com.footnotesforthefuture.simple_blog.service;

import com.footnotesforthefuture.simple_blog.model.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
	Flux<Post> findAllPosts();
	Mono<Post> save(Post post);
}
