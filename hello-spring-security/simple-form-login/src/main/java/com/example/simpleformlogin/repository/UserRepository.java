package com.example.simpleformlogin.repository;

import com.example.simpleformlogin.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {

    Optional<Member> findUserByUsername(String username);
}
