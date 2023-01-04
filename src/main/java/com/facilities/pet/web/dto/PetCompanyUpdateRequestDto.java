package com.facilities.pet.web.dto;

import com.facilities.pet.domain.pet.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PetCompanyUpdateRequestDto {
    private String name;
    private String address;
    private Status status;

    @Builder
    public PetCompanyUpdateRequestDto(String name, String address, Status status) {
        this.name = name;
        this.address = address;
        this.status = status;
    }
}
