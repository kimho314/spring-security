package com.example.basicauthorization.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GoodByeController {

    @GetMapping("/bye")
    @Async // enpoint가 async가 되면 메서드를 실행하는 스레드와 요청을 수행하는 스레드가 서로 다르다
    public void goodBye(){
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        // NPE 발생: 메서드가 보안 컨텍스트를 상속하지 않는 다른 스레드에서 실행되기 때문에 발생
        // MODE_INHERITABLETHREADLOCAL으로 해당 이슈 해결 가능
        // 해당 전략을 설정하면 요청의 원래 스레드에 있는 세부 정보를 비동기 메서드의 새로 생성된 스레드로 복사함
        log.info("name: " + name);
    }
}
