package com.facilities.pet.web.dto;

import com.facilities.pet.domain.pet.PetCompany;
import lombok.Getter;

@Getter
public class PetCompanyResponseDto {

    private Long id;
    private String name;
    private String address;
    private String status;

    public PetCompanyResponseDto(PetCompany petCompany) {
        this.id = petCompany.getId();
        this.name = petCompany.getName();
        this.address = petCompany.getAddress();
        this.status = petCompany.getStatus().getValue();
    }
}
