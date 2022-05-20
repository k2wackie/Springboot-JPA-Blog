package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다는 의미
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional
	public int 회원가입(User user) {
		try {
			String rawPassword = user.getPassword(); // 원문 패스워드
			String encPassword = encoder.encode(rawPassword); // 해쉬 패스워드
			user.setPassword(encPassword);
			user.setRole(RoleType.USER);
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService: 회원가입:" + e.getMessage());
		}
		return -1;
	}

}
