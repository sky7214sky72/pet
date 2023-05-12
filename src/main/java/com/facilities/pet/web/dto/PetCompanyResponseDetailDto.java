package com.facilities.pet.web.dto;

import com.facilities.pet.domain.pet.PetCompany;
import lombok.Getter;

/**
 * . PetCompanyResponseDetailDto
 */
@Getter
public class PetCompanyResponseDetailDto {

  private final Long id;
  private final String name;
  private final String address;
  private final String status;

  private final double coordinatesX;
  private final double coordinatesY;

  /**
   * . PetCompanyResponseDetailDto
   */
  public PetCompanyResponseDetailDto(PetCompany petCompany) {
    this.id = petCompany.getId();
    this.name = petCompany.getName();
    this.address = petCompany.getAddress();
    this.status = petCompany.getStatus().getValue();
    this.coordinatesX = petCompany.getCoordinatesX();
    this.coordinatesY = petCompany.getCoordinatesY();
  }
}
