package com.example.backfinalpriject.entity;

import com.example.backfinalpriject.member.dto.request.MemberSignupRequest;
import lombok.*;

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

    @Setter @Column(name = "role")
    private Integer role;

    public static Member createUser(MemberSignupRequest request, String encryptedPwd){
        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(encryptedPwd);
        member.setName(request.getName());
        member.setRole(0);
        return member;
    }

    public static Member createAdmin(MemberSignupRequest request, String encryptedPwd){
        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(encryptedPwd);
        member.setName(request.getName());
        member.setRole(1);
        return member;
    }
}
