package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html파일이 아니라 data를 리턴해주는 controller=RestController
@RestController
public class DummyController {

	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;

	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
//		} catch (Exception e) { //이것도 동작하나 정확한 Exception을 잡아주는 것이 좋음
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}

		return "삭제 되었습니다. id: " + id;

	}

	// save함수는 id를 전달하지 않으면 insert
	// save함수는 id를 전달하고 id에 대한 데이터가 있으면 update
	// save함수는 id를 전달하고 id에 대한 데이터가 없으면 insert
	// email, password
	@Transactional // 함수 종료 시 자동 commit됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json데이터를 요청 => Java Object로 변환해서(MessageConverter의 Jackson라이브러리)
		System.out.println("id" + id);
		System.out.println("email: " + requestUser.getEmail());
		System.out.println("password: " + requestUser.getPassword());

		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

//		userRepository.save(user);

		// 더티 체킹
		return user;

	}

	// http://localhost:8000/blog/dummy/user/
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}

	// 한페이지당 2 건의 데이터를 리턴
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUsers = userRepository.findAll(pageable);
		
		List<User> users = pagingUsers.getContent();
		return pagingUsers;
	}

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
