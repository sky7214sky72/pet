package com.facilities.pet.service.pet;

import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.domain.pet.PetCompanyRepository;
import com.facilities.pet.web.dto.PetCompanyResponseDetailDto;
import com.facilities.pet.web.dto.PetCompanyResponseDto;
import com.facilities.pet.web.dto.PetCompanySaveRequestDto;
import com.facilities.pet.web.dto.PetCompanyUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * . PetService
 */
@RequiredArgsConstructor
@Service
public class PetService {

  private final PetCompanyRepository petCompanyRepository;

  /**
   * . findAll
   */
  @Transactional
  public Page<PetCompanyResponseDto> findAll(Pageable pageable) {
    List<PetCompanyResponseDto> petCompanyList = petCompanyRepository.findAll(pageable).stream()
        .map(PetCompanyResponseDto::new)
        .collect(Collectors.toList());
    return new PageImpl<>(petCompanyList, pageable,
        petCompanyRepository.findAll(pageable).getTotalElements());
  }

  /**
   * . findByCompanyId
   */
  public PetCompanyResponseDetailDto findByCompanyId(Long companyId) {
    PetCompany petCompany = petCompanyRepository.findById(companyId).orElseThrow(() ->
        new IllegalArgumentException("해당 회사가 없습니다"));
    return new PetCompanyResponseDetailDto(petCompany);
  }

  /**
   * . save
   */
  @Transactional
  public Long save(PetCompanySaveRequestDto requestDto) {
    return petCompanyRepository.save(requestDto.toEntity()).getId();
  }

  /**
   * . update
   */
  @Transactional
  public Long update(Long companyId, PetCompanyUpdateRequestDto requestDto) {
    PetCompany petCompany = petCompanyRepository.findById(companyId).orElseThrow(() ->
        new IllegalArgumentException("해당회사가 없습니다"));
    petCompany.update(requestDto.getName(), requestDto.getAddress(), requestDto.getStatus());
    return companyId;
  }

  /**
   * . delete
   */
  @Transactional
  public void delete(Long companyId) {
    PetCompany petCompany = petCompanyRepository.findById(companyId).orElseThrow(() ->
        new IllegalArgumentException("해당회사가 없습니다"));
    petCompanyRepository.delete(petCompany);
  }
}
