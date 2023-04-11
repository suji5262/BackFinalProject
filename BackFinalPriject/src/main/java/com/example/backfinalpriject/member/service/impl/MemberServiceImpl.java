package com.example.backfinalpriject.member.service.impl;

import com.example.backfinalpriject.member.dto.request.MemberSignupRequest;
import com.example.backfinalpriject.entity.Member;
import com.example.backfinalpriject.member.service.MemberService;
import com.example.backfinalpriject.member.repository.MemberRepository;
import com.example.backfinalpriject.member.dto.request.MemberLoginRequest;
import com.example.backfinalpriject.exception.ErrorCode;
import com.example.backfinalpriject.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    private final HttpSession session;

    public void login(MemberLoginRequest request ){
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(()
                -> new GlobalException(ErrorCode.USER_NOT_FOUND, "해당 아이디가 존재하지 않습니다"));
        if (!encoder.matches(request.getPassword(), member.getPassword())){
            throw new GlobalException(ErrorCode.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다");
        }
    }

    public void signup(MemberSignupRequest request){
        memberRepository.findByEmail(request.getEmail()).ifPresent(x -> {
            throw new GlobalException(ErrorCode.DUPLICATED_USER_EMAIL, request.getEmail()+" 는 이미 존재하는 아이디입니다");
            });
        String encryptedPwd = encoder.encode(request.getPassword());
        memberRepository.save(Member.createUser(request, encryptedPwd));
    }

    public void adminSignup(MemberSignupRequest request){
        memberRepository.findByEmail(request.getEmail()).ifPresent(x -> {
            throw new GlobalException(ErrorCode.DUPLICATED_USER_EMAIL, request.getEmail()+" 는 이미 존재하는 아이디입니다.");
        });
        String encryptedPwd = encoder.encode(request.getPassword());
        memberRepository.save(Member.createAdmin(request, encryptedPwd));
    }

    public void adminLogin(MemberLoginRequest request){
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(()
                -> new GlobalException(ErrorCode.USER_NOT_FOUND, "해당 아이디가 존재하지 않습니다"));
        if (!encoder.matches(request.getPassword(), member.getPassword())){
            throw new GlobalException(ErrorCode.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다");
        }else if (member.getRole() != 1){
            throw new GlobalException(ErrorCode.INVALID_PERMISSION, "관리자 외에 접근이 불가합니다.");
        }else{
            session.setAttribute("email",member.getEmail());
            session.setAttribute("id",member.getId());

        }

    }
}
