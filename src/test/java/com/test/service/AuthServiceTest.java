package com.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.test.entity.Member;

@SpringBootTest
@Transactional 
public class AuthServiceTest {

	@Autowired
	private AuthService authService;

	@BeforeEach
	void setUp() {
		authService.createMember("admin", "1234", "관리자");
		authService.createMember("송효준", "1234", "송효준");
	}

	@Test
	void testCreateMember() {
		authService.createMember("thdgywns", "1234", "송효준");
		Member member = authService.findById("송효준");

		assertThat(member).isNotNull();
		assertThat(member.getPoint()).isEqualTo(1000);
	}

	@Test
	void testCheckMember() {
		boolean result = authService.checkMember("송효준", "1234");
		assertThat(result).isTrue();
	}

	@Test
	void testFindById() {
		Member member = authService.findById("송효준");
		assertThat(member.getName()).isEqualTo("송효준");
	}

	@Test
	void testFindAll() {
		List<Member> members = authService.findAll();
		assertThat(members.size()).isGreaterThanOrEqualTo(2);
	}

	@Test
	void testEditMember() {
		authService.editMember("송효준", "9999", "수정된이름", 1000L);
		Member member = authService.findById("송효준");
		assertThat(member.getPw()).isEqualTo("9999");
		assertThat(member.getPoint()).isEqualTo(1000L);
	}

	@Test
	void testDeleteMember() {
		authService.deleteMember("송효준");
		Member member = authService.findById("송효준");
		assertThat(member).isNull();
	}

	@Test
	void testAddPoints() {
		authService.addPoints("송효준", 500);
		Member member = authService.findById("송효준");
		assertThat(member.getPoint()).isEqualTo(1500);
	}

	@Test
	void testSubtractPoints() {
		authService.subtractPoints("송효준", 500);
		Member member = authService.findById("송효준");
		assertThat(member.getPoint()).isEqualTo(500);
	}
}
