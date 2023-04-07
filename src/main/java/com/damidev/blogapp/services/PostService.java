package com.damidev.blogapp.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.stereotype.Service;

import com.damidev.blogapp.models.Post;
import com.damidev.blogapp.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	
	public Optional<Post> getPostById(Long id){
		return postRepository.findById(id);
	}
	
	public List<Post> findAllPosts(){
		return postRepository.findAll();
	}
	
	public List<Post> findUsersPosts(Long userId){
		List<Post> allPosts = postRepository.findAll();
		List<Post> userPosts = new ArrayList<>();
		
		for(Post post : allPosts) {
			if(post.getAccount().getId() == userId) {
				userPosts.add(post);
			}
		}
		return userPosts;
	}
	
	public List<Post> findPostByTag(String tag){
		List<Post> allPosts = postRepository.findAll();
		List<Post> postByTag = new ArrayList<>();
		
		for(Post post : allPosts) {
			if(post.getTag().contentEquals(tag)) {
				postByTag.add(post);
			}
		}
		return postByTag;
	}
	
	public Post savePost(Post post) {
		if(post.getId() == null) {
			post.setCreatedAt(LocalDateTime.now());
		}
		post.setLastUpdatedAt(LocalDateTime.now());
		return postRepository.saveAndFlush(post);
	}
	
	public void deletePost(Post post) {
		postRepository.delete(post);
	}

}










