package com.cos.blog.test;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html파일이 아니라 data를 리턴해주는 controller=RestController
@RestController
public class DummyController {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;

	// {id}주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/3을 찾다가 내가 데이터베이스에서 못찾게 되면 user가 null이 될것이니
		// return null을 리턴하면 프로그램에 문제가 발생하니
		// 그래서 Optional로 user객체를 감싸서 가져올테니 null인지 아닌지 판단하여 return하라

//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			@Override
//			public User get() {
//				return new User();
//			}
//		});

//		//람다식이라는것도 있음
//		User user = userRepository.findById(id).orElseThrow(() -> {
//				return new IllegalArgumentException("id:" + id + " 해당 유저는 없습니다. ");
//		});

		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("id:" + id + " 해당 유저는 없습니다. ");
			}
		});

		// 요청:웹브라우저
		// user객체 =자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터로) -> json
		// 스프링부트 =MessageConverter가 응답시에 자동으로 작동
		// 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
		return user;

	}

	// http://localhost:8000/blog/dummy/join (요청)
	// http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
//	public String join(String username, String password, String email) {
//		System.out.println("username:" + username);
//		System.out.println("password:" + password);
//		System.out.println("email:" + email);
//		return "회원가입이 완료되었습니다.";
//
//	}

	public String join(User user) {
		System.out.println("id: " + user.getId());
		System.out.println("username:" + user.getUsername());
		System.out.println("password:" + user.getPassword());
		System.out.println("email:" + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("createDate: " + user.getCreateDate());

		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";

	}

}
