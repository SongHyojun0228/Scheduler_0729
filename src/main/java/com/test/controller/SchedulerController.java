package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.service.SchedulerService;

@Controller
public class SchedulerController {
	@Autowired
	SchedulerService scheSvc;
	
	@RequestMapping("/start")
	public String start() {
		scheSvc.startScheduler();
		return "redirect:/admin/main";
	}
	
	@RequestMapping("/end")
	public String end() {
		scheSvc.endScheduler();
		return "redirect:/admin/main";
	}
}
