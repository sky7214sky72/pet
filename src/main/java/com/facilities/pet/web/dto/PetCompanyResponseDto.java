package com.facilities.pet.web.dto;

import com.facilities.pet.domain.pet.PetCompany;
import lombok.Getter;

/**
 * . PetCompanyResponseDto
 */
@Getter
public class PetCompanyResponseDto {

  private final Long id;
  private final String name;
  private final String address;
  private final String status;

  /**
   * . PetCompanyResponseDto
   */
  public PetCompanyResponseDto(PetCompany petCompany) {
    this.id = petCompany.getId();
    this.name = petCompany.getName();
    this.address = petCompany.getAddress();
    this.status = petCompany.getStatus().getValue();
  }
}
