package com.facilities.pet.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.facilities.pet.domain.pet.Status;
import com.facilities.pet.domain.review.Review;
import com.facilities.pet.domain.review.ReviewRepository;
import com.facilities.pet.web.dto.PetCompanySaveRequestDto;
import com.facilities.pet.web.dto.ReviewSaveRequestDto;
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
class ReviewControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ReviewRepository reviewRepository;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

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
    }

    @AfterEach
    public void tearDown() throws Exception {
        reviewRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void review_create_test() throws Exception {
        ReviewSaveRequestDto reviewSaveRequestDto = ReviewSaveRequestDto.builder()
                .title("test")
                .content("test")
                .build();

        String url = "http://localhost:" + port + "/api/reviews/1";
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reviewSaveRequestDto)))
                .andExpect(status().isOk());
        List<Review> all = reviewRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("test");
        assertThat(all.get(0).getPetCompany().getId()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void review_list_test() throws Exception{
        review_create_test();
        String url = "http://localhost:" + port + "/api/reviews/1";
        mvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void review_detail_list_test() throws Exception{
        review_create_test();
        List<Review> all = reviewRepository.findAll();
        String url = "http://localhost:" + port + "/api/reviews/detail/"+all.get(0).getId();
        mvc.perform(get(url))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", password = "user")
    void review_delete_test() throws Exception {
        review_create_test();

        String url = "http://localhost:" + port + "/api/reviews/1";

        mvc.perform(delete(url))
                .andExpect(status().isOk());
    }
}
