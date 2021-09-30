package smu.earthranger.dto.member;

import lombok.Getter;

@Getter
public class MemberLoginDto {

    String email;
    String password;

    public MemberLoginDto(String email, String password){
        this.email = email;
        this.password = password;
    }
}

