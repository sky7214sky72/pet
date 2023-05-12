package com.facilities.pet.web.dto;

import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.domain.pet.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * . PetCompanySaveRequestDto
 */
@Getter
@NoArgsConstructor
public class PetCompanySaveRequestDto {

  private String name;
  private String address;
  private Status status;
  private double coordinatesX;
  private double coordinatesY;

  /**
   * . PetCompanySaveRequestDto
   */
  @Builder
  public PetCompanySaveRequestDto(String name, String address, Status status, double coordinatesX,
      double coordinatesY) {
    this.name = name;
    this.address = address;
    this.status = status;
    this.coordinatesX = coordinatesX;
    this.coordinatesY = coordinatesY;
  }

  /**
   * . toEntity
   */
  public PetCompany toEntity() {
    return PetCompany.builder()
        .name(name)
        .address(address)
        .status(status)
        .coordinatesX(coordinatesX)
        .coordinatesY(coordinatesY)
        .build();
  }
}
