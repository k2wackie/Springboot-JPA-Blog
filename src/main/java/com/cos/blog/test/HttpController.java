package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML파일)
//@Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpController {
	private static final String TAG = "HttpControllerTest:";

	@GetMapping("/http/lombok")
	public String lombokTest() {
//		Member member = new Member(1, "ssar", "12345", "ssar@nate.com");
		Member member = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG + "getter: " + member.getUsername());
		member.setUsername("cos");
		System.out.println(TAG + "setter: " + member.getUsername());
		return "Lombok test 완료";
	}

	// 인터넷 브라우저 요청은 무조건 get요청만 가능함
	// http://localhost:8080/http/get(select)
	@GetMapping("/http/get")
//	public String getTest(@RequestParam int id, @RequestParam String username) { 
	public String getTest(Member member) { // id=1&username=ssar&password=1234&email=ssar@nate.com //
											// Messageconverter(스프링부트)
		return "get 요청" + member.getId() + "," + member.getUsername() + "," + member.getPassword() + "," + member.getEmail();
	}

	// http://localhost:8080/http/post(insert)
	@PostMapping("/http/post") // text/plain, application/json
//	public String postTest(@RequestBody String text) {
	public String postTest(@RequestBody Member member) {// Messageconverter(스프링부트)
//		return "post요청" + text;
		return "post 요청" + member.getId() + "," + member.getUsername() + "," + member.getPassword() + "," + member.getEmail();

	}

	// http://localhost:8080/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member member) {
		return "put 요청" + member.getId() + "," + member.getUsername() + "," + member.getPassword() + "," + member.getEmail();
	}

	// http://localhost:8080/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
