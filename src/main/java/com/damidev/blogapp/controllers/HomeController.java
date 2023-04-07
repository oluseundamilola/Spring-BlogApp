package com.damidev.blogapp.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.damidev.blogapp.models.Account;
import com.damidev.blogapp.models.Post;
import com.damidev.blogapp.services.AccountService;
import com.damidev.blogapp.services.PostService;

@Controller
public class HomeController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		List<Post> posts = postService.findAllPosts();
		model.addAttribute("posts", posts);
		
		String authUsername = "anonymousUser";
		if(principal != null) {
			authUsername = principal.getName();
		}
		Optional<Account> optionalAccout = accountService.findAccountByEmail(authUsername);
		Account account = new Account();
		if(optionalAccout.isPresent()) {
			 account = optionalAccout.get();
		}
		model.addAttribute("account", account);
		
		
		return "home";
	}
	
	@GetMapping("/users/{userId}/posts")
		public String getAllPostsByUserID(@PathVariable Long userId, Model model){
			List<Post> userPosts = postService.findUsersPosts(userId);
			model.addAttribute("posts", userPosts);
			return "usersPosts";
		}
	
	@GetMapping("/posts/{tag}")
	public String getAllPostByTag(@PathVariable String tag, Model model) {
		List<Post> tagPost = postService.findPostByTag(tag);
		model.addAttribute("posts", tagPost);
		return "tagPosts";
	}
	
	
	
	
	
	
	
	
	
	
	

}
