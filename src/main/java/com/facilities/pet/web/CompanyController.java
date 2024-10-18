package com.facilities.pet.web;

import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.service.pet.PetService;
import com.facilities.pet.web.dto.PetCompanyResponseDetailDto;
import com.facilities.pet.web.dto.PetCompanyResponseDto;
import com.facilities.pet.web.dto.PetCompanySaveRequestDto;
import com.facilities.pet.web.dto.PetCompanyUpdateRequestDto;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @ApiResponses
  @GetMapping("/pet-companies")
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  public ResponseEntity<Page<PetCompanyResponseDto>> list(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    System.out.println(page);
    return petService.companyList(PageRequest.of(page, size));
  }

  @PostMapping("/pet-companies")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<PetCompany> save(@RequestBody PetCompanySaveRequestDto requestDto) {
    return petService.save(requestDto);
  }

  @GetMapping("/pet-companies/{companyId}")
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  public ResponseEntity<PetCompanyResponseDetailDto> detail(@PathVariable Long companyId) {
    return petService.findByCompanyId(companyId);
  }

  @PutMapping("/pet-companies/{companyId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<PetCompany> modify(@PathVariable Long companyId,
      @RequestBody PetCompanyUpdateRequestDto requestDto) {
    return petService.update(companyId, requestDto);
  }

  @DeleteMapping("/pet-companies/{companyId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<?> delete(@PathVariable Long companyId) {
    return petService.delete(companyId);
  }

  @PostMapping(value = "/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<?> excelSave(@RequestPart("file") MultipartFile file)
      throws IOException {
    return petService.excelSave(file);
  }
}
