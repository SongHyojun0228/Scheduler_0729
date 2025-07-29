package com.test.service;

import java.util.HashSet;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.common.TestJob;

@Service
public class SchedulerServiceImpl implements SchedulerService {
	private Scheduler s = null;

	@Autowired
	private AuthService authService;
	
	@Override
	public void startScheduler() {
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			s = sf.getScheduler();
			s.getContext().put("authService", authService);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		JobDetail jobDetail = JobBuilder.newJob(TestJob.class)
								.build();
		
		CronTrigger ct = (CronTrigger) TriggerBuilder.newTrigger()   // 크론표현식
								.withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ?"))  // 2초마다 실행
							.forJob(jobDetail)
							.build();
							
		Set<Trigger> ts = new HashSet<Trigger>();
		ts.add(ct);
		
		try {
			s.scheduleJob(jobDetail, ts, false);  // [스케줄러 셋팅완료]
			System.out.println("<<< 포인트 스케줄러가 시작되었습니다. >>>");
			s.start();		// [스케줄러 시작]
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void endScheduler() {
		try {
			System.out.println("<<< 포인트 스케줄러의 실행이 종료되었습니다. >>>");
			s.shutdown();  // [스케줄러 끝]
		} catch (SchedulerException e) {
			e.printStackTrace();
		}	
	}
}
