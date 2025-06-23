package com.example.basicjwtauthserver.repository;

import com.example.basicjwtauthserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

}
