package com.facilities.pet.domain.pet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PetCompanyRepositoryTest {
    @Autowired
    PetCompanyRepository petCompanyRepository;

    @AfterEach
    public void cleanup() {
        petCompanyRepository.deleteAll();
    }

    @Test
    void petCompanyList() {
        String name = "(주)더포에버";
        String address = "인천광역시 서구 설원로 79 (대곡동)";
        Status status = Status.SALES;
        double coordinatesX = 171202.3981;
        double coordinatesY = 458211.4774;

        petCompanyRepository.save(PetCompany.builder()
                .name(name)
                .address(address)
                .status(status)
                .coordinatesX(coordinatesX)
                .coordinatesY(coordinatesY)
                .build());
        //when
        List<PetCompany> petCompanyList = petCompanyRepository.findAll();

        //then
        PetCompany petCompany = petCompanyList.get(0);
        System.out.println(petCompany);
        assertThat(petCompany.getName()).isEqualTo(name);
        assertThat(petCompany.getAddress()).isEqualTo(address);
        assertThat(petCompany.getStatusKey()).isEqualTo(status.getKey());
        assertThat(petCompany.getCoordinatesX()).isEqualTo(coordinatesX);
        assertThat(petCompany.getCoordinatesY()).isEqualTo(coordinatesY);

    }
}
