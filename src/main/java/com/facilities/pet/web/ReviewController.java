package com.facilities.pet.web;

import com.facilities.pet.domain.user.User;
import com.facilities.pet.service.review.ReviewService;
import com.facilities.pet.service.user.UserService;
import com.facilities.pet.web.dto.ReviewResponseDto;
import com.facilities.pet.web.dto.ReviewSaveRequestDto;
import com.facilities.pet.web.dto.ReviewUpdateRequestDto;
import com.facilities.pet.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/reviews/{companyId}")
    public Page<ReviewResponseDto> list(Pageable pageable, @PathVariable Long companyId){
        return reviewService.findByCompanyId(pageable, companyId);
    }

    @PostMapping("/reviews/{companyId}")
    public Long create(@PathVariable Long companyId, @RequestBody ReviewSaveRequestDto reviewSaveRequestDto) {
        User user = userService.getMyUserWithAuthorities().orElse(null);
        return reviewService.save(reviewSaveRequestDto, user, companyId);
    }

    @GetMapping("/reviews/detail/{reviewId}")
    public ReviewResponseDto detail(@PathVariable Long reviewId) {
        return reviewService.findByReviewId(reviewId);
    }

    @PutMapping("/reviews/{reviewId}")
    public Long modify(@PathVariable Long reviewId, @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto) {
        User user = userService.getMyUserWithAuthorities().orElse(null);
        return reviewService.update(reviewId, reviewUpdateRequestDto, user);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public Long delete(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
        return reviewId;
    }
}
