package com.footnotesforthefuture.simple_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footnotesforthefuture.simple_blog.model.Post;
import com.footnotesforthefuture.simple_blog.repository.PostRepository;

import reactor.core.publisher.Flux;

@Service
public class PostServiceImpl implements PostService{

	private final PostRepository postRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	@Override
	public Flux<Post> findAllPosts() {
		return postRepository.findAll();
	}

}
