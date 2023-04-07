package com.damidev.blogapp.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.damidev.blogapp.models.Account;
import com.damidev.blogapp.models.Authority;
import com.damidev.blogapp.models.Post;
import com.damidev.blogapp.repository.AuthorityRepository;
import com.damidev.blogapp.services.AccountService;
import com.damidev.blogapp.services.PostService;

@Component
public class SeedData implements CommandLineRunner {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
    private AuthorityRepository authorityRepository;
	
	@Override
	public void run(String... args) throws Exception {
		List<Post> posts = postService.findAllPosts();
		
		if(posts.size() == 0) {
			
			Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);
			
			Account account1 = new Account();
			Account account2 = new Account();
			
			account1.setEmail("jack@domain.com");
			account1.setFirstName("Jackson");
			account1.setLastName("Nefemi");
			account1.setPassword("password");
			Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);
			
			account2.setEmail("joe@email.com");
			account2.setFirstName("Joe");
			account2.setLastName("Less");
			account2.setPassword("password");
			Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            account2.setAuthorities(authorities2);
			
			accountService.saveAccount(account1);
			accountService.saveAccount(account2);
			
			Post post1 = new Post();
			post1.setTitle("React 19? Should We Be Worried?");
			post1.setBody("If you are subscribed to my articles you know that frontend world was boiling at the end of last year (2022).\r\n"
					+ "\r\n"
					+ "Vue, Svelte, Angular, Ionic, Next, Nuxt teams were working like crazy to deliver top notch features as a Christmas or festive gift for the developers.\r\n"
					+ "\r\n"
					+ "Unfortunately, some of us, including me, were left with a little bit of a sad frog feeling because there was nothing new from the React team.\r\n"
					+ "\r\n"
					+ "In the last months I was browsing the release notes, merge requests, commits, and even teammates Instagram profiles to find any clue what they are up to (joking).");
			post1.setTag("technology");
			post1.setAccount(account1);
			
			Post post2 = new Post();
			post2.setTitle("France elections");
			post2.setBody("Unfortunately all I found were Christmas or festive wishes. Beautiful Christmas trees and New Year’s fireworks that followed the November wave of LinkedIn layoff posts and articles about Facebook firing 13% of its staff.\r\n"
					+ "\r\n"
					+ "Now, two months later it would be great to know if, and how it affects the React framework team, but the blog is silent and I can’t find anything that would indicate there will be any major release anytime soon.");
			post2.setTag("politics");
			post2.setAccount(account2);
			
			
			Post post3 = new Post();
			post3.setTitle("Best of 2022");
			post3.setBody("Unfortunately all I found were Christmas or festive wishes. Beautiful Christmas trees and New Year’s fireworks that followed the November wave of LinkedIn layoff posts and articles about Facebook firing 13% of its staff.\r\n"
					+ "\r\n"
					+ "Now, two months later it would be great to know if, and how it affects the React framework team, but the blog is silent and I can’t find anything that would indicate there will be any major release anytime soon.");
			post3.setTag("sports");
			post3.setAccount(account2);
			
			Post post4 = new Post();
			post4.setTitle("React Hooks");
			post4.setBody("Unfortunately all I found were Christmas or festive wishes. Beautiful Christmas trees and New Year’s fireworks that followed the November wave of LinkedIn layoff posts and articles about Facebook firing 13% of its staff.\r\n"
					+ "\r\n"
					+ "Now, two months later it would be great to know if, and how it affects the React framework team, but the blog is silent and I can’t find anything that would indicate there will be any major release anytime soon.");
			post4.setTag("technology");
			post4.setAccount(account1);
			
			postService.savePost(post1);
			postService.savePost(post2);
			postService.savePost(post3);
			postService.savePost(post4);
		}
	}

}
