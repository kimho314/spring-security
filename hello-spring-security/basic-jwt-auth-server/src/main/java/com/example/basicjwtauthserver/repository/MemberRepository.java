package com.example.basicjwtauthserver.repository;

import com.example.basicjwtauthserver.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByUsername(String username);
}
