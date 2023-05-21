package com.facilities.pet.domain.pet;

import com.facilities.pet.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * .PetCompany
 */
@Getter
@NoArgsConstructor
@Entity
public class PetCompany extends BaseTimeEntity {

  @JsonIgnore
  @Id
  @Column(name = "COMPANY_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String category;

  private String name;

  private String tel;

  @Column(columnDefinition = "TEXT")
  private String address;

  private int zipCode;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  private double coordinatesX;
  private double coordinatesY;

  /**
   * . PetCompany
   */
  @Builder
  public PetCompany(String category, String name, String tel, String address, int zipCode,
      Status status, double coordinatesX,
      double coordinatesY) {
    this.category = category;
    this.name = name;
    this.tel = tel;
    this.address = address;
    this.zipCode = zipCode;
    this.status = status;
    this.coordinatesX = coordinatesX;
    this.coordinatesY = coordinatesY;
  }

  public String getStatusKey() {
    return this.status.getKey();
  }

  /**
   * . getStatusValue
   */
  public String getStatusValue() {
    return this.status.getValue();
  }

  /**
   * . update
   */
  public void update(String name, String address, Status status) {
    this.name = name;
    this.address = address;
    this.status = status;
  }
}
