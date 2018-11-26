package com.footnotesforthefuture.simple_blog.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.footnotesforthefuture.simple_blog.model.User;
import com.footnotesforthefuture.simple_blog.service.UserService;

import reactor.core.publisher.Mono;

@Controller
public class AuthenticationController {
	
	private UserService userService;
		
	@Autowired
	public AuthenticationController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
    public Mono<String> login(Mono<Principal> principal) {
		return principal.map(x -> "redirect:/").defaultIfEmpty("login");
    }
	
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public Mono<String> registration(Model model) { //TODO should be mono?
        model.addAttribute("user", new User());
        return Mono.just("/registration");
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public Mono<String> createNewUser(@Valid User user, 
                                BindingResult bindingResult,
                                Model model) {
    	
    	return userService.findByUsername(user.getUsername())
    			.doOnNext(existingUser -> {
		    			bindingResult
		                .rejectValue("username", "error.user",
		                        "There is already a user registered with the username provided");
		    		})
    			.switchIfEmpty(userService.save(user).doOnNext(savedUser -> {
					model.addAttribute("successMessage", "User has been registered successfully");
		            model.addAttribute("user", new User());
		    	}))
    			.then(Mono.just("/registration"));
    }
}
