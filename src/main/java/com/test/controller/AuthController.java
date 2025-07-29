package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.entity.Member;
import com.test.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@GetMapping("/auth/signup")
	public String getSignup() {
		return "auth/signup";
	}

	@ResponseBody
	@PostMapping("/signup")
	public String Signup(@RequestParam("id") String id, @RequestParam("pw") String pw,
			@RequestParam("name") String name) {
		try {
			System.out.println(">>>>>>>> 회원가입 성공");
			this.authService.createMember(id, pw, name);
			return "<script>alert('가입되었습니다. 로그인 해주세요.'); location.href='/auth/login';</script>";
		} catch (Exception e) {
			System.out.println(">>>>>>>> 회원가입 실패");
			e.printStackTrace();
			return "redirect:/auth/signup";
		}

	}

	@GetMapping("/auth/login")
	public String getLogin() {
		return "auth/login";
	}

	@ResponseBody
	@PostMapping("/login")
	public String login(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session) {
		boolean isValidUser = authService.checkMember(id, pw);

		if (!isValidUser) {
			return "<script>alert('아이디/비밀번호를 다시 확인하세요'); location.href='/auth/login';</script>";
		}

		Member member = this.authService.findById(id);

		session.setAttribute("user", member);

		if (id.equals("admin")) {
			return "<script>location.href='/admin/main';</script>";
		}

		return "<script>location.href='/main';</script>";
	}

	@ResponseBody
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "<script>alert('로그아웃 되었습니다.'); location.href='/auth/login';</script>";
	}

}
