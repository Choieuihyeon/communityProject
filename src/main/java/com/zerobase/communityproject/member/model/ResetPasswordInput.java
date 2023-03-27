package com.zerobase.communityproject.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ResetPasswordInput {

    private String userId;
    private String userName;

    private String id;
    private String password;
}
