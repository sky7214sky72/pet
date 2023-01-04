package com.facilities.pet.web.dto;

import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.domain.review.Review;
import com.facilities.pet.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public ReviewUpdateRequestDto(String title, String content, User user, PetCompany petCompany) {
        this.title = title;
        this.content = content;
    }
}
