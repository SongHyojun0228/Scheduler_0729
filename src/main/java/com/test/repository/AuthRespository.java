package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.entity.Member;

@Repository
public interface AuthRespository extends JpaRepository<Member, String> {

}
