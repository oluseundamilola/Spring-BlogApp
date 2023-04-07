package com.damidev.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.damidev.blogapp.models.Account;
import com.damidev.blogapp.services.AccountService;

@Controller
public class RegisterController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/register")
	public String getLoginPage(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		return "register";
	}
	
	@PostMapping("/register")
	public String registerNewUser(@ModelAttribute Account account) {
		accountService.saveAccount(account);
		return "redirect:/";
	}

}
