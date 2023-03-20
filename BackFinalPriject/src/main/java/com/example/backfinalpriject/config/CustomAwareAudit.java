package com.example.backfinalpriject.config;

import com.example.backfinalpriject.entity.Member;
import com.example.backfinalpriject.exception.ErrorCode;
import com.example.backfinalpriject.exception.GlobalException;
import com.example.backfinalpriject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CustomAwareAudit implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if(authentication != null) {
            // 현재 로그인한 사용자의 정보를 조회하여 사용자의 이름을 등록자와 수정자로 지정한다.
            email = authentication.getName();
        }
        return Optional.of(email);
    }
}
