package com.facilities.pet.service.pet;

import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.domain.pet.PetCompanyRepository;
import com.facilities.pet.domain.pet.Status;
import com.facilities.pet.web.dto.PetCompanyResponseDetailDto;
import com.facilities.pet.web.dto.PetCompanyResponseDto;
import com.facilities.pet.web.dto.PetCompanySaveRequestDto;
import com.facilities.pet.web.dto.PetCompanyUpdateRequestDto;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * . PetService
 */
@RequiredArgsConstructor
@Service
@Slf4j
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

  /**
   * . excel Save
   */
  public void excelSave(MultipartFile file) throws IOException {
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    if (!extension.equals("xlsx") && !extension.equals("xls")) {
      throw new IOException("only excel file upload please");
    }

    Workbook workbook = null;

    if (extension.equals("xlsx")) {
      workbook = new XSSFWorkbook(file.getInputStream());
    } else {
      workbook = new HSSFWorkbook(file.getInputStream());
    }

    Sheet worksheet = workbook.getSheetAt(0);
    for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
      Row row = worksheet.getRow(i);
      String tel = null;
      if (row.getCell(3) != null) {
        tel = row.getCell(3).getCellType() == CellType.NUMERIC ? String.valueOf(
            row.getCell(3).getNumericCellValue()) : row.getCell(3).getStringCellValue();
      }

      if (row.getCell(0) == null) {
        throw new NullPointerException("업무구분명은 필수값입니다.");
      }

      if (row.getCell(1) == null) {
        throw new NullPointerException("업체이름은 필수값입니다.");
      }
      PetCompany petCompany = PetCompany.builder()
          .category(row.getCell(0).getStringCellValue())
          .name(row.getCell(1).getStringCellValue())
          .tel(tel)
          .status(row.getCell(2).getStringCellValue().equals("정상") ? Status.SALES : Status.CLOSURE)
          .address(row.getCell(4) != null ? row.getCell(4).getStringCellValue() : "")
          .zipCode(row.getCell(5) != null ? (int) row.getCell(5).getNumericCellValue() : 0)
          .coordinatesX(row.getCell(6) != null ? row.getCell(6).getNumericCellValue() : 0L)
          .coordinatesY(row.getCell(7) != null ? row.getCell(7).getNumericCellValue() : 0L)
          .build();
      petCompanyRepository.save(petCompany);
    }
  }
}
