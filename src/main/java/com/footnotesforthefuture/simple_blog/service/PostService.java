package com.footnotesforthefuture.simple_blog.service;

import com.footnotesforthefuture.simple_blog.model.Post;

import reactor.core.publisher.Flux;

public interface PostService {
	Flux<Post> findAllPosts();
}
