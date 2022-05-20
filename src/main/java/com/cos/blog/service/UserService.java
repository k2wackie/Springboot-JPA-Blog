package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다는 의미
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public int 회원가입(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService: 회원가입:" + e.getMessage());
		}
		return -1;
	}

	@Transactional(readOnly = true) // Select 할 때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료(정합성 유지)
	public User 로그인(User user) {

		User userData = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		return userData;
	}
}
