package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/user/joinform")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/user/loginform")
	public String loginForm() {
		return "user/loginForm";
	}
}
