package com.example.simpleformlogin.service;

import com.example.simpleformlogin.entity.CustomUserDetails;
import com.example.simpleformlogin.entity.Member;
import com.example.simpleformlogin.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = userRepository.findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Problem during authentication!"));

        return new CustomUserDetails(member);
    }
}
