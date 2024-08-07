package com.bigdecimal.springbootbackend4nextjs.user;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.bigdecimal.springbootbackend4nextjs.security.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class UserManagementControllerTest {

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

  @BeforeEach
  void setup() {}

  @Test
  @WithMockUser(username = "admin", authorities = { "SCOPE_ADMIN" })
  void givenUserIsGrantedAdminRole_deleteUserShouldReturnOkAndValidJson()
    throws Exception {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("admin", null, List.of(new SimpleGrantedAuthority("ADMIN")));
    
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    Authentication authentication = SecurityContextHolder
      .getContext()
        .getAuthentication();
    System.out.println(
      "Authentication role is: " + authentication.getAuthorities()
    );
    String jwt = jwtService.accessToken(authentication);
    mockMvc
      .perform(delete("/users/john").header("Authorization", "Bearer " + jwt))
      .andDo(print())
      .andExpectAll(status().isOk(), jsonPath("$.success", is(true)));
  }
  //   private String asJsonString(Object object) throws JsonProcessingException {
  //     return objectMapper.writeValueAsString(object);
  //   }
}
