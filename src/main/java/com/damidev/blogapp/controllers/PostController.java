package com.damidev.blogapp.controllers;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.damidev.blogapp.models.Account;
import com.damidev.blogapp.models.Post;
import com.damidev.blogapp.repository.AccountRepository;
import com.damidev.blogapp.services.AccountService;
import com.damidev.blogapp.services.PostService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/post/{id}")
	public String getPost(@PathVariable Long id, Model model, Principal principal) {

		// find post by ID
		Optional<Post> optionalPost = postService.getPostById(id);
		Account account = new Account();
		
		String authUsername = "anonymousUser";
		if(principal != null) {
			authUsername = principal.getName();
			Optional<Account> optionalAccout = accountService.findAccountByEmail(authUsername);
			account = optionalAccout.get();
		}
		// if post exist, then shove it into the model
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			if(post.getAccount().getId() == account.getId()) {
				model.addAttribute("post", post);
				return "accountpost";
			}else {
				model.addAttribute("post", post);
				return "post";
			}
		} else {
			return "404";
		}

	}

	@GetMapping("/posts/new")
	@PreAuthorize("isAuthenticated()")
	public String createNewPostView(Model model, Principal principal) {
		
		String authUsername = "anonymousUser";
		if(principal != null) {
			authUsername = principal.getName();
		}
		
		Optional<Account> optionalAccout = accountService.findAccountByEmail(authUsername);
		if (optionalAccout.isPresent()) {
			Post post = new Post();
			post.setAccount(optionalAccout.get());
			model.addAttribute("post", post);
			return "newpost";
		} else {
			return "404";
		}
	}

	@PostMapping("/posts/new")
	public String saveNewPost(@ModelAttribute Post post) {
		postService.savePost(post);
		return "redirect:/post/" + post.getId();
	}

	@GetMapping("/post/{id}/edit")
	@PreAuthorize("isAuthenticated()")
	public String getPostForEdit(@PathVariable Long id, Model model) {
		// find post by ID
		Optional<Post> optionalPost = postService.getPostById(id);
		// if post exist put it in model
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			model.addAttribute("post", post);
			return "post_edit";
		} else {
			return "404";
		}
	}

	@PostMapping("/post/{id}")
	@PreAuthorize("isAuthenticated()")
	public String updatePost(@PathVariable Long id, Post post, BindingResult result, Model model) {
		// find post by ID
		Optional<Post> optionalPost = postService.getPostById(id);
		// if post exist put it in model
		if (optionalPost.isPresent()) {
			Post existingPost = optionalPost.get();
			existingPost.setTitle(post.getTitle());
			existingPost.setBody(post.getBody());
			existingPost.setTag(post.getTag());

			postService.savePost(existingPost);

		}

		return "redirect:/post/" + post.getId();
	}

	@GetMapping("/post/{id}/delete")
	@PreAuthorize("isAuthenticated()")
	public String deletePost(@PathVariable Long id) {
		// find post by ID
		Optional<Post> optionalPost = postService.getPostById(id);
		if(optionalPost.isPresent()) {
			Post post = optionalPost.get();
			postService.deletePost(post);
			return "redirect:/";
		}else {
			return "404";
		}
	}

}
