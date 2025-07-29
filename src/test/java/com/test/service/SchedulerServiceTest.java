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
		// given: AuthService.findAll() 호출될 때 반환할 값 세팅
		Member member = new Member();
		member.setId("test");
		member.setName("테스트유저");
		member.setPoint(1000L);
		when(authService.findAll()).thenReturn(Arrays.asList(member));

		// when: 스케줄러 시작
		schedulerService.startScheduler();

		// then: 2~3초 정도 대기 후 로그 확인 or Mock 호출 확인
		Thread.sleep(2500); // 2초 주기로 실행하니 최소 2초 대기

		// verify: findAll()이 최소 한 번은 호출됐는지 확인
		verify(authService, atLeastOnce()).findAll();
	}

	@Test
	void testEndScheduler() {
		schedulerService.startScheduler();
		schedulerService.endScheduler();

		// 여기서는 단순히 종료 시 에러 없이 잘 끝나는지 확인
		// 실제 Quartz 스케줄러 상태 체크도 가능 (isShutdown 같은 메서드)
	}
}
