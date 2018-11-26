package com.footnotesforthefuture.simple_blog.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.footnotesforthefuture.simple_blog.model.Post;
import com.footnotesforthefuture.simple_blog.model.User;
import com.footnotesforthefuture.simple_blog.service.PostService;
import com.footnotesforthefuture.simple_blog.service.UserService;

import reactor.core.publisher.Mono;

@Controller
public class PostController {

	private final PostService postService;
    private final UserService userService;
    
    @Autowired
    public PostController(PostService postService, UserService userService) {
    	this.postService = postService;
    	this.userService = userService;
    }
    
    @InitBinder
    public void allowNullStringBinding(WebDataBinder binder)
    {
    	binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));  
    }
    
    //TODO make flow more readable? (weird mixing of reactive and non reactive)
    @RequestMapping(value = "/newPost", method = RequestMethod.GET)
    public Mono<String> newPost(Principal principal,
                          Model model) {

    	Mono<User> userMono;
    	if(principal != null && principal.getName() != null)
        	userMono = userService.findByUsername(principal.getName());
        else
        	return Mono.just("/error");

        return userMono
        		.map(user -> {
        			Post post = new Post();
        			post.setUserId(user.getId());
        			model.addAttribute("post", post);
        			return "/editPost";
        		})
        		.defaultIfEmpty("/error");
    }

    //TODO User ID / Post ID validation
    @RequestMapping(value = "/newPost", method = RequestMethod.POST)
    public Mono<String> createNewPost(@Valid Post post,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Mono.just("/editPost");
        } else {
        	Mono<Post> postMono = postService.save(post);
            //return "redirect:/blog/" + post.getUser().getUsername(); //TODO add this
        	return postMono.then(Mono.just("redirect:/"));
        }
    }
    
    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public Mono<String> getPostWithId(@PathVariable String id,
                                Principal principal,
                                Model model) {

        Mono<Post> postMono = postService.findById(id);
        return postMono
        		.flatMap(post -> {
        			Mono<User> authorMono = userService.findById(post.getUserId()); //TODO what if post does not have user ID?
        			return authorMono.map(author -> {
        				model.addAttribute("post", post);
        				model.addAttribute("author", author);
        				if(principal != null && principal.getName().equals(author.getUsername())) {
        					model.addAttribute("username", author.getUsername());
        				}
        				return "/post";
        			}).defaultIfEmpty("/error");
        		}).defaultIfEmpty("/error");
    }
    
    @RequestMapping(value = "/editPost/{id}", method = RequestMethod.GET)
    public Mono<String> editPostWithId(@PathVariable String id,
                                 Principal principal,
                                 Model model) {

    	Mono<Post> postMono = postService.findById(id);
        return postMono
        		.flatMap(post -> {
        			Mono<User> authorMono = userService.findById(post.getUserId()); //TODO what if post does not have user ID?
        			return authorMono.map(author -> {
        				if(principal != null && principal.getName().equals(author.getUsername())) {
        					model.addAttribute("post", post);
            				return "/editPost";
        				}
        				return "/error";
        			}).defaultIfEmpty("/error");
        		}).defaultIfEmpty("/error"); //TODO There's got to be a better flow than this
    }
    
    //TODO REFACTOR 
    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public Mono<String> deletePostWithId(@PathVariable String id,
                                   Principal principal) {

    	Mono<Post> postMono = postService.findById(id);    	
    	
    	return postMono
        		.flatMap(post -> {
        			Mono<User> authorMono = userService.findById(post.getUserId()); //TODO what if post does not have user ID?
        			return authorMono.flatMap(author -> {
        				if(principal != null && principal.getName().equals(author.getUsername())) {
        					return postService.deleteById(id).then(Mono.just("redirect:/"));
        				}
        				return Mono.just("/error");
        			}).defaultIfEmpty("/error");
        		}).defaultIfEmpty("/error"); //TODO There's got to be a better flow than this
    }
}
