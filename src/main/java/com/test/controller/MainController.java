package com.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.entity.Member;
import com.test.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final AuthService authService;

	@GetMapping("/main")
	public String mainPage(HttpSession session, Model model) {
	    Member user = (Member) session.getAttribute("user");
	    
	    if (user == null) {
	        return "redirect:/auth/login";
	    }
	    
	    model.addAttribute("user", user);
	    
	    return "/main/main";
	}
	
	@PostMapping("/buy")
	@ResponseBody
	public Map<String, Object> buyContent(@RequestBody Map<String, Object> payment, HttpSession session) {
	    Member member = (Member) session.getAttribute("user");
	    Map<String, Object> result = new HashMap<>();

	    if (member == null) {
	        result.put("error", "로그인을 해주세요.");
	        return result;
	    }

	    int price = (int) payment.get("price");
	    String courseName = (String) payment.get("courseName");

	    if (member.getPoint() < price) {
	        result.put("error", "포인트가 부족합니다. 광고를 클릭하세요.");
	        return result;
	    }

	    authService.subtractPoints(member.getId(), price);

	    Member updatedMember = authService.findById(member.getId());
	    session.setAttribute("user", updatedMember);

	    result.put("newPoint", updatedMember.getPoint());
	    result.put("course", courseName);
	    return result;
	}

	@ResponseBody
	@PostMapping("/ad/click")
	public Map<String, Object> adClick(HttpSession session) {
	    Member member = (Member) session.getAttribute("user");
	    Map<String, Object> result = new HashMap<>();

	    if (member == null) {
	        result.put("error", "로그인을 해주세요.");
	        return result;
	    }

	    int points = (int)(Math.random() * 1000) + 1;

	    try {
	        authService.addPoints(member.getId(), points);
	        // 갱신된 데이터 다시 조회
	        Member updatedMember = authService.findById(member.getId());
	        session.setAttribute("user", updatedMember);
	        System.out.println("포인트 추가 성공");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    result.put("points", points);
	    return result;
	}

	
}
