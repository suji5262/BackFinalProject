package com.example.backfinalpriject.member.dto.request;

import lombok.Data;

@Data
public class MemberSignupRequest {

    private String email;
    private String password;
    private String name;

}
