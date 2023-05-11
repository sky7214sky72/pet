package com.facilities.pet.domain.pet;

import com.facilities.pet.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PetCompany extends BaseTimeEntity {

    @JsonIgnore
    @Id
    @Column(name = "COMPANY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private double coordinatesX;
    private double coordinatesY;

    @Builder
    public PetCompany(String name, String address, Status status, double coordinatesX, double coordinatesY) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
    }

    public String getStatusKey(){
        return this.status.getKey();
    }

    public String getStatusValue(){
        return this.status.getValue();
    }

    public void update(String name, String address, Status status) {
        this.name = name;
        this.address = address;
        this.status = status;
    }
}
