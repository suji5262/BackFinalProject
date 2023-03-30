package com.example.backfinalpriject.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginRequest {

    private String email;
    private String password;

}
