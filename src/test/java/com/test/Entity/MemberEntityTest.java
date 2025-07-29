package com.test.Entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.test.entity.Member;
import com.test.repository.AuthRespository;

@SpringBootTest
public class MemberEntityTest {

    @Autowired
    private AuthRespository authRepository;

    @Test
    void testSaveAndFindMember() {
        // given
        Member member = new Member();
        member.setId("testUser");
        member.setPw("1234");
        member.setName("테스트");
        member.setPoint(1000L);

        // when
        authRepository.save(member);
        Member found = authRepository.findById("testUser").orElse(null);

        // then
        assertNotNull(found);
        assertEquals("testUser", found.getId());
        assertEquals("테스트", found.getName());
        assertEquals(1000L, found.getPoint());
    }
}
