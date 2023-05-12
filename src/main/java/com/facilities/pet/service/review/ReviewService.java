package com.facilities.pet.service.review;

import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.domain.pet.PetCompanyRepository;
import com.facilities.pet.domain.review.Review;
import com.facilities.pet.domain.review.ReviewRepository;
import com.facilities.pet.domain.user.User;
import com.facilities.pet.domain.user.UserRepository;
import com.facilities.pet.web.dto.ReviewResponseDto;
import com.facilities.pet.web.dto.ReviewSaveRequestDto;
import com.facilities.pet.web.dto.ReviewUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * . ReviewService
 */
@RequiredArgsConstructor
@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;

  private final PetCompanyRepository petCompanyRepository;

  private final UserRepository userRepository;

  /**
   * . findByCompanyId
   */
  @Transactional
  public Page<ReviewResponseDto> findByCompanyId(Pageable pageable, Long companyId) {
    List<ReviewResponseDto> reviewList = reviewRepository.findByPetCompany_Id(pageable, companyId)
        .stream()
        .map(ReviewResponseDto::new)
        .collect(Collectors.toList());
    return new PageImpl<>(reviewList);
  }

  /**
   * . findByReviewId
   */
  @Transactional
  public ReviewResponseDto findByReviewId(Long reviewId) {
    Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
        new IllegalArgumentException("해당 리뷰가 없습니다"));
    return new ReviewResponseDto(review);
  }

  /**
   * . save
   */
  @Transactional
  public Long save(ReviewSaveRequestDto reviewSaveRequestDto, User user, Long companyId) {
    PetCompany petCompany = petCompanyRepository.findById(companyId)
        .orElseThrow(() -> new IllegalArgumentException("해당 회사는 없습니다."));
    reviewSaveRequestDto.setPetCompany(petCompany);
    reviewSaveRequestDto.setUser(user);
    return reviewRepository.save(reviewSaveRequestDto.toEntity()).getId();
  }

  /**
   * . update
   */
  @Transactional
  public Long update(Long reviewId, ReviewUpdateRequestDto reviewUpdateRequestDto, User user) {
    Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
        new IllegalArgumentException("해당 리뷰가 없습니다"));
    if (review.getUser().getPhoneNumber() != user.getPhoneNumber()) {
      throw new IllegalArgumentException("리뷰 작성자만 업데이트가 가능합니다.");
    }
    review.update(reviewUpdateRequestDto.getTitle(), reviewUpdateRequestDto.getContent());
    return reviewId;
  }

  /**
   * . delete
   */
  @Transactional
  public void delete(Long reviewId) {
    Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
        new IllegalArgumentException("해당 리뷰가 없습니다"));
    reviewRepository.delete(review);
  }
}
