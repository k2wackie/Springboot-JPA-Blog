package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더패턴
//ORM -> JAVA(또는 다른 언어) Object - > 테이블로 매핑해주는 기술
@Entity // User클래스가 MySQL에 테이블이 생성됨
public class User {

	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto-increment

	@Column(nullable = false, length = 30)
	private String username; // 아이디

	@Column(nullable = false, length = 100) // 12345=> 해쉬(비밀번호 암호화로 100으로)
	private String password;

	@Column(nullable = false, length = 50)
	private String email;

	@ColumnDefault(" 'user' ")
	private String role; // Enum을 쓰는게 좋음 //admin, 일반user, manager //Enum으로 설정하면 도메인(어떤범위)를 정해서 입력하도록

	@CreationTimestamp
	private Timestamp createDate;

}
