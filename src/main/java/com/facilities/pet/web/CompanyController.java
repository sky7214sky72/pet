package com.facilities.pet.web;

import com.facilities.pet.service.pet.PetService;
import com.facilities.pet.web.dto.PetCompanyResponseDetailDto;
import com.facilities.pet.web.dto.PetCompanyResponseDto;
import com.facilities.pet.web.dto.PetCompanySaveRequestDto;
import com.facilities.pet.web.dto.PetCompanyUpdateRequestDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * . 장묘업체 관련
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CompanyController {

  private final PetService petService;

  @GetMapping("/pet-companies")
  public Page<PetCompanyResponseDto> list(@ParameterObject Pageable pageable) {
    return petService.findAll(pageable);
  }

  @PostMapping("/pet-companies")
  public Long save(@RequestBody PetCompanySaveRequestDto requestDto) {
    return petService.save(requestDto);
  }

  @GetMapping("/pet-companies/{companyId}")
  public PetCompanyResponseDetailDto detail(@PathVariable Long companyId) {
    return petService.findByCompanyId(companyId);
  }

  @PutMapping("/pet-companies/{companyId}")
  public Long modify(@PathVariable Long companyId,
      @RequestBody PetCompanyUpdateRequestDto requestDto) {
    return petService.update(companyId, requestDto);
  }

  @DeleteMapping("/pet-companies/{companyId}")
  public Long delete(@PathVariable Long companyId) {
    petService.delete(companyId);
    return companyId;
  }

  @PostMapping("/excel")
  public ResponseEntity<String> excelSave(@RequestPart("file") MultipartFile file)
      throws IOException {
    petService.excelSave(file);
    return new ResponseEntity<>("", HttpStatus.OK);
  }
}
