package Not.Delivered.auth.controller;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import Not.Delivered.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

  private static final Logger log = LoggerFactory.getLogger(AuthControllerTest.class);
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  UserRepository userRepository;

  @Test
  public void 회원가입_성공() throws Exception{

    //given
    String requestJson = """
        {
        "email":"semin123@gmail.com",
        "password":"semin123",
        "username":"semin",
        "userStatus":"OWNER",
        "address":{
        "city":"광주시",
        "state":"서구",
        "street":"금호동"
        }
        }
        """;

    //when & then
    mockMvc.perform(
        MockMvcRequestBuilders.post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
    ).andDo(result -> objectMapper.readValue(result.getResponse().getContentAsString(),Map.class))
        .andExpect(jsonPath("$.status").value("CREATED"))
        .andExpect(jsonPath("$.message").value("회원 가입 성공!"));

  }
}