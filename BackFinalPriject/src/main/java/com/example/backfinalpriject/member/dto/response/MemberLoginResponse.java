package com.example.backfinalpriject.member.dto.response;

import com.example.backfinalpriject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginResponse {

    private String email;
    private String name;

    public static MemberLoginResponse toDto(Member member){
        return new MemberLoginResponse(
                member.getEmail(),
                member.getName());
    }
}
