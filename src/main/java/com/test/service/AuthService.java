package com.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.entity.Member;
import com.test.repository.AuthRespository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRespository authRespository;

	// 회원 생성
	public void createMember(String id, String pw, String name) {
		Member member = new Member();

		member.setId(id);
		member.setPw(pw);
		member.setName(name);
		member.setPoint((long) 1000);

		try {
			this.authRespository.save(member);
			System.out.println(">>>>> 데베에 유저 등록 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(">>>>> 데베에 유저 등록 실패");
		}
	}

	// 존재하는 회원인가
	public boolean checkMember(String id, String pw) {
		try {
			Optional<Member> _member = this.authRespository.findById(id);

			if (_member.isPresent()) {
				Member member = _member.get();
				return member.getPw().equals(pw);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// 멤버 조회
	public Member findById(String id) {
		try {
			Optional<Member> _member = this.authRespository.findById(id);

			if (_member.isPresent()) {
				return _member.get();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 모든 멤버 조회
	public List<Member> findAll() {
		try {
			List<Member> members = this.authRespository.findAll();
			return members;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	// 멤버 수정
	public void editMember(String id, String pw, String name, Long point) {
		try {
			Optional<Member> _member = this.authRespository.findById(id);
			
			if(_member.isPresent()) {
				Member member = _member.get();
				member.setPw(pw);
				member.setName(name);
				member.setPoint((long) point);	
				
				this.authRespository.save(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 멤버삭제
	@Transactional
	public void deleteMember(String id) {
		try {
			this.authRespository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 포인트 추가
	@Transactional
	public void addPoints(String id, int points) {
		Optional<Member> _member = this.authRespository.findById(id);

		if (_member.isPresent()) {
			Member member = _member.get();
			member.setPoint(member.getPoint() + points);
			this.authRespository.save(member);
		}
	}

	// 포인트 차감
	@Transactional
	public void subtractPoints(String id, int points) {
		Optional<Member> _member = authRespository.findById(id);

		if (_member.isPresent()) {
			Member member = _member.get();
			member.setPoint(member.getPoint() - points);
			authRespository.save(member);
		}
	}

}
