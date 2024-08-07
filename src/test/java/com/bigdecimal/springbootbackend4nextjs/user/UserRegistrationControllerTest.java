package com.bigdecimal.springbootbackend4nextjs.user;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bigdecimal.springbootbackend4nextjs.security.service.JWTService;
import com.bigdecimal.springbootbackend4nextjs.user.dto.UserRegistrationRequestPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class UserRegistrationControllerTest {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  JWTService jwtService;

  @Autowired
  MockMvc mockMvc;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
    "postgres:16.2-alpine"
  );

  @Container
  @ServiceConnection
  static RedisContainer redis = new RedisContainer("redis:alpine");

  UserRegistrationRequestPayload userRegistrationRequestPayload = new UserRegistrationRequestPayload(
    "danny",
    "password",
    "ADMIN"
  );

  @BeforeEach
  void setup() {}

  @Test
  void givenUserIsNotAuthencticated_addUserShouldReturnOk() throws Exception {
    mockMvc
      .perform(
        post("/users")
          .content(asJsonString(userRegistrationRequestPayload))
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andDo(print())
      .andExpectAll(status().isOk(), jsonPath("$.username", is("danny")));
  }

  private String asJsonString(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }
}
