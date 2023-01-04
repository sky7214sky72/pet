package com.facilities.pet.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 11, max = 11)
    private String phoneNumber;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @Builder
    public LoginDto(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
