package com.footnotesforthefuture.simple_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footnotesforthefuture.simple_blog.model.Post;
import com.footnotesforthefuture.simple_blog.model.User;
import com.footnotesforthefuture.simple_blog.repository.PostRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	@Override
	public Mono<Post> save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Flux<Post> findByAuthor(User user) {
		return postRepository.findByUserId(user.getId());
	}

	@Override
	public Mono<Post> findById(String id) {
		return postRepository.findById(id);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return postRepository.deleteById(id);
	}
	
	

}
