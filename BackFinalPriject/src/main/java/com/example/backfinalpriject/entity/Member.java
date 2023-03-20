package com.example.backfinalpriject.entity;

import com.example.backfinalpriject.dto.request.MemberSignupRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Setter @Column(name = "member_email")
    private String email;

    @Setter @Column(name = "member_password")
    private String password;

    @Setter @Column(name = "member_name")
    private String name;

    @Column(name = "role")
    private int role = 0;

    public static Member of(MemberSignupRequest request, String encryptedPwd){
        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(encryptedPwd);
        member.setName(request.getName());
        return member;
    }
}
