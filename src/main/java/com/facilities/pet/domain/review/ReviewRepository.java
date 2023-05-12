package com.facilities.pet.domain.review;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * . ReviewRepository
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByPetCompany_Id(Pageable pageable, Long companyId);
}
