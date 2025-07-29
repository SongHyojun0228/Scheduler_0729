package com.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.entity.Member;
import com.test.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {

	private final AuthService authService;

	@GetMapping("/admin/main")
	public String getAdminPage(HttpSession session, Model model) {
		Member user = (Member) session.getAttribute("user");

		if (user == null) {
			return "redirect:/auth/login";
		}

		if (!"admin".equals(user.getName())) {
			System.out.println(">>>>>>>>>>>> 관리자 외 접근 확인됨");
			return "redirect:/main";
		}

		List<Member> members = this.authService.findAll();

		model.addAttribute("members", members);

		return "admin/admin_page";
	}

	@GetMapping("/admin/logout")
	public String logout(HttpSession session) {
		session.invalidate();

		return "redirect:/auth/login";
	}

	@GetMapping("/admin/edit/member/{id}")
	public String getEditMember(HttpSession session, @PathVariable("id") String id, Model model) {
		Member user = (Member) session.getAttribute("user");

		if (user == null) {
			return "redirect:/auth/login";
		}

		if (!"admin".equals(user.getName())) {
			System.out.println(">>>>>>>>>>>> 관리자 외 접근 확인됨");
			return "redirect:/main";
		}

		try {
			Member editMember = this.authService.findById(id);
			model.addAttribute("editMember", editMember);

			return "/admin/edit_member";
		} catch (Exception e) {
			e.printStackTrace();

			return "redirect:/admin/main";
		}

	}

	@ResponseBody
	@PostMapping("/edit/member")
	public String editMember(@RequestParam("id") String id, @RequestParam("pw") String pw,
			@RequestParam("name") String name, @RequestParam("point") Long point) {
		try {
			this.authService.editMember(id, pw, name, point);
			System.out.println(">>>>>>>>> 멤버 수정 성공");
			
			return "<script>alert('수정되었습니다.'); location.href='/admin/main';</script>";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(">>>>>>>>> 멤버 수정 실패");
			
			return "<script>alert('새로고침 후 다시 시도해주세요.'); location.href='/admin/main';</script>";
		}
	}

	@ResponseBody
	@PostMapping("/delete/member/{id}")
	public String deleteMember(@PathVariable("id") String id) {
		try {
			Member deleteMember = this.authService.findById(id);

			if (deleteMember != null) {
				this.authService.deleteMember(id);
				return "<script>alert('삭제되었습니다.'); location.href='/admin/main';</script>";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "<script>alert('새로고침 후 다시 시도해주세요.'); location.href='/admin/main';</script>";
		}

		return "<script>location.href='/admin/main';</script>";
	}

}
