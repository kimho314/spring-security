package com.example.simpleformlogin.config;

import com.example.simpleformlogin.entity.Algorithm;
import com.example.simpleformlogin.entity.Authority;
import com.example.simpleformlogin.entity.Currency;
import com.example.simpleformlogin.entity.Member;
import com.example.simpleformlogin.entity.Product;
import com.example.simpleformlogin.repository.AuthorityRepository;
import com.example.simpleformlogin.repository.ProductRepository;
import com.example.simpleformlogin.repository.UserRepository;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final ProductRepository productRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataInitCommandLineRunner(UserRepository userRepository,
        AuthorityRepository authorityRepository,
        ProductRepository productRepository,
        BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.productRepository = productRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Member member = Member.builder()
            .username("kim")
            .password(bCryptPasswordEncoder.encode("12345"))
            .algorithm(Algorithm.BCRYPT)
            .build();
        Member savedMember = userRepository.save(member);

        Authority authority = Authority.builder()
            .member(savedMember)
            .name("READ")
            .build();
        Authority savedAuthority = authorityRepository.save(authority);

        savedMember.setAuthorities(Arrays.asList(savedAuthority));

        Product product = Product.builder()
            .name("test1")
            .price(100000L)
            .currency(Currency.WON)
            .build();
        productRepository.save(product);
    }
}
