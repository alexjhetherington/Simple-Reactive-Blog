package com.footnotesforthefuture.simple_blog.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.footnotesforthefuture.simple_blog.model.User;
import com.footnotesforthefuture.simple_blog.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PostListController {

	private final UserService userService;
	
	@Autowired
	public PostListController(UserService userService) {
		this.userService = userService;
	}
	
	//TODO for now post list is behaving as user list for initial local commit. Squash it when posts are done :)
	@GetMapping("/")
	public Mono<String> home(Model model, Mono<Principal> principal) {
		Flux<User> users = userService.findAllUsers();		
		model.addAttribute("allUsers", users);
		principal.subscribe(x -> pleaseOhGodWhy(x));
		return Mono.just("post_list");
	}
	
	private void pleaseOhGodWhy(Principal principal) {
		System.out.println(principal);
	}
}
