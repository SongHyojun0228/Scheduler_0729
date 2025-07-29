package com.test.common;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.test.entity.Member;
import com.test.service.AuthService;

public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            AuthService authService = (AuthService) context.getScheduler().getContext().get("authService");

            if (authService == null) {
                System.out.println("AuthService가 null입니다. SchedulerServiceImpl에서 주입 확인 필요.");
                return;
            }

            List<Member> members = authService.findAll();
            System.out.println("Job이 실행됨 : " + LocalDateTime.now().toString().substring(0, 19)
                    + " / " + members.size() + "명에게 포인트 부여 (1점)");

            for (Member member : members) {
                authService.addPoints(member.getId(), 1);
            }

        } catch (SchedulerException e) {
            throw new JobExecutionException(e);
        }
    }
    
}
