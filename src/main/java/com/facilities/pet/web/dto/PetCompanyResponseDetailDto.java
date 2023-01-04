package com.facilities.pet.web.dto;

import com.facilities.pet.domain.pet.PetCompany;
import lombok.Getter;

@Getter
public class PetCompanyResponseDetailDto {
    private Long id;
    private String name;
    private String address;
    private String status;

    private double coordinatesX;
    private double coordinatesY;

    public PetCompanyResponseDetailDto(PetCompany petCompany) {
        this.id = petCompany.getId();
        this.name = petCompany.getName();
        this.address = petCompany.getAddress();
        this.status = petCompany.getStatus().getValue();
        this.coordinatesX = petCompany.getCoordinatesX();
        this.coordinatesY = petCompany.getCoordinatesY();
    }
}
