package com.facilities.pet.web;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.facilities.pet.domain.user.UserRepository;
import com.facilities.pet.web.dto.LoginDto;
import com.facilities.pet.web.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  public void setUp() throws Exception {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @Test
  public void testAll() throws Exception {
    join_test();
    login_test();
  }

  @AfterEach
  public void tearDown() throws Exception {
    userRepository.deleteAll();
  }

  @Test
  void join_test() throws Exception {
    String userName = "cws";
    String phoneNumber = "01047347265";
    String password = "1234";

    UserDto userDto = UserDto.builder()
        .username(userName)
        .phoneNumber(phoneNumber)
        .password(password)
        .build();
    String url = "http://localhost:" + port + "/api/join";
    mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDto)))
        .andExpect(status().isOk());
  }

  @Test
  void login_test() throws Exception {
    String phoneNumber = "01047347265";
    String password = "1234";

    LoginDto loginDto = LoginDto.builder()
        .phoneNumber(phoneNumber)
        .password(password)
        .build();
    String url = "http://localhost:" + port + "/api/login";
    mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(loginDto)))
        .andExpect(status().isOk());
  }

}
