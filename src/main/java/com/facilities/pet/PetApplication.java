package com.facilities.pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * . PetApplication
 */
@EnableJpaAuditing
@SpringBootApplication
public class PetApplication {

  public static void main(String[] args) {
    SpringApplication.run(PetApplication.class, args);
  }

}
