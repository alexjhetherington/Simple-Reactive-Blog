package com.footnotesforthefuture.simple_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.footnotesforthefuture.simple_blog.model.Post;
import com.footnotesforthefuture.simple_blog.model.User;
import com.footnotesforthefuture.simple_blog.service.PostService;
import com.footnotesforthefuture.simple_blog.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PostListController {

	private final UserService userService;
	private final PostService postService;
	
	@Autowired
	public PostListController(UserService userService, PostService postService) {
		this.userService = userService;
		this.postService = postService;
	}
	
	@GetMapping("/")
	public Mono<String> home(Model model) {
		Flux<Post> posts = postService.findAllPosts();		
		model.addAttribute("posts", posts);
		return Mono.just("/post_list");
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public Mono<String> blogForUsername(@PathVariable String username, Model model) {
		
		Mono<User> authorMono = userService.findByUsername(username);
		return authorMono
				.doOnNext(author -> {
					Flux<Post> posts = postService.findByAuthor(author);
					model.addAttribute("posts", posts);
				})
				.then(Mono.just("/post_list"));
	}
}
