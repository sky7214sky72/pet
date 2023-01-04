package com.facilities.pet.web.dto;

import com.facilities.pet.domain.review.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long id;
    private String title;
    private String content;
    private String petCompany;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.petCompany = review.getPetCompany().getName();
    }
}
