package com.footnotesforthefuture.simple_blog.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.footnotesforthefuture.simple_blog.model.Post;

public interface PostRepository extends ReactiveMongoRepository<Post, String>{
}
