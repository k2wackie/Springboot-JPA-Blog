package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더패턴
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increament
	private int id;

	@Column(nullable = false, length = 100)
	private String title;

	@Lob // 대용량 데이터에 사용
	private String content; // 섬머노트 라이브러리 사용 <html>태그가 섞여서 디자인됨

	private int count; // 조회수

	@ManyToOne(fetch = FetchType.EAGER) // Many=Board, User = One 연관관계 //EAGER:바로 불러오기, LAZY:필요시 불러오기
	@JoinColumn(name = "userId")
	private User user; // DB는 오브젝트를 저장할 수 없음. FK(foreign key), 자바는 오브젝트를 저장할 수 있음, ORM을 사용하면 오브젝트를 저장 가능

	// ManyToOne:기본 EAGER, OneToMany:기본 LAZY

	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // mappedBy 연관관계의 주인이 아니다 (난 FK가 아니니) DB에 칼럼을 만들지 않도록
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	private List<Reply> replys;

	@CreationTimestamp
	private Timestamp createDate;
}
