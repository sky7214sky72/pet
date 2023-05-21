package com.facilities.pet.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.domain.pet.PetCompanyRepository;
import com.facilities.pet.domain.pet.Status;
import com.facilities.pet.web.dto.PetCompanySaveRequestDto;
import com.facilities.pet.web.dto.PetCompanyUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class CompanyControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PetCompanyRepository petCompanyRepository;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  public void setUp() throws Exception {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @AfterEach
  public void tearDown() throws Exception {
    petCompanyRepository.deleteAll();
  }

  @Test
  @WithMockUser(username = "user", password = "user")
  void company_create_test() throws Exception {

    String name = "(주)더포에버";
    String address = "인천광역시 서구 설원로 79 (대곡동)";
    Status status = Status.SALES;
    double coordinatesX = 171202.3981;
    double coordinatesY = 458211.4774;

    PetCompanySaveRequestDto requestDto = PetCompanySaveRequestDto.builder()
        .name(name)
        .address(address)
        .status(status)
        .coordinatesX(coordinatesX)
        .coordinatesY(coordinatesY)
        .build();
    String url = "http://localhost:" + port + "/api/pet-companies";
    mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk());
    List<PetCompany> all = petCompanyRepository.findAll();
    assertThat(all.get(0).getName()).isEqualTo(name);
    assertThat(all.get(0).getAddress()).isEqualTo(address);
    assertThat(all.get(0).getStatusKey()).isEqualTo("STATUS_SALES");
  }

  @Test
  @WithMockUser(username = "user", password = "user")
  void company_list_test() throws Exception {
    String url = "http://localhost:" + port + "/api/pet-companies";
    mvc.perform(get(url))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", password = "user")
  void company_list_detail_test() throws Exception {
    String url = "http://localhost:" + port + "/api/pet-companies/1";
    mvc.perform(get(url))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", password = "user")
  void company_modify_test() throws Exception {

    String name = "더포에버";
    String address = "인천광역시 서구 설원로 78";
    Status status = Status.CLOSURE;

    PetCompanyUpdateRequestDto requestDto = PetCompanyUpdateRequestDto.builder()
        .name(name)
        .address(address)
        .status(status)
        .build();
    String url = "http://localhost:" + port + "/api/pet-companies/1";
    mvc.perform(put(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk());
    List<PetCompany> all = petCompanyRepository.findAll();
    assertThat(all.get(0).getName()).isEqualTo(name);
    assertThat(all.get(0).getAddress()).isEqualTo(address);
    assertThat(all.get(0).getStatusKey()).isEqualTo("STATUS_CLOSURE");
  }

  @Test
  @WithMockUser(username = "user", password = "user")
  void company_list_delete_test() throws Exception {
    String url = "http://localhost:" + port + "/api/pet-companies/1";
    mvc.perform(delete(url))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", password = "user")
  void testAll() throws Exception {
    company_create_test();
    company_list_test();
    company_list_detail_test();
    company_modify_test();
    company_list_delete_test();
  }
}
