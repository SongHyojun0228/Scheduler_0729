package com.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

	@Id
	String id;
	
	@Column
	String pw;
	
	@Column
	String name;
	
	@Column
	Long point;
	
}
