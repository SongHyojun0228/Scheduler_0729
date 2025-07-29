package com.test.service;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.test.entity.Member;

@SpringBootTest
public class SchedulerServiceTest {

	@Autowired
	private SchedulerService schedulerService;

	@MockBean
	private AuthService authService;

	@AfterEach
	void tearDown() {
		schedulerService.endScheduler();
	}

	@Test
	void testStartScheduler() throws InterruptedException {
		Member member = new Member();
		member.setId("test");
		member.setName("테스트유저");
		member.setPoint(1000L);
		when(authService.findAll()).thenReturn(Arrays.asList(member));

		schedulerService.startScheduler();

		Thread.sleep(20000);
		
		verify(authService, atLeastOnce()).findAll();
	}

	@Test
	void testEndScheduler() {
		schedulerService.startScheduler();
		schedulerService.endScheduler();
	}
}
