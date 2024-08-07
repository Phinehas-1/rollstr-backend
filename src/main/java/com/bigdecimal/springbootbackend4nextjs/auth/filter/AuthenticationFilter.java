package com.bigdecimal.springbootbackend4nextjs.auth.filter;

import com.bigdecimal.springbootbackend4nextjs.auth.dto.LoginRequestPayload;
import com.bigdecimal.springbootbackend4nextjs.auth.dto.LoginResponsePayload;
import com.bigdecimal.springbootbackend4nextjs.security.service.JWTService;
import com.bigdecimal.springbootbackend4nextjs.shared.dto.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws AuthenticationException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      LoginRequestPayload loginRequestPayload = objectMapper.readValue(
        request.getReader(),
        LoginRequestPayload.class
      );
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        loginRequestPayload.username(),
        loginRequestPayload.password()
      );
      return authenticationManager.authenticate(authenticationToken);
    } catch (IOException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    return super.attemptAuthentication(request, response);
  }

  @Override
  protected void successfulAuthentication(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain,
    Authentication authResult
  ) throws IOException, ServletException {
    response.setContentType("application/json;charset=utf-8");
    try (PrintWriter writer = response.getWriter();) {
      writer.write(
        writeObjectAsJson(
          new ApiResponse(
            true,
            new LoginResponsePayload(jwtService.accessToken(authResult)),
            null
          )
        )
      );
    } catch (Exception e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  protected void unsuccessfulAuthentication(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException failed
  ) throws IOException, ServletException {
    response.setContentType("application/json;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    try (PrintWriter writer = response.getWriter();) {
      writer.write(
        writeObjectAsJson(
          new ApiResponse(false, null, failed.getMessage())
        )
      );
    } catch (Exception e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }

  private String writeObjectAsJson(Object object) {
    String json = null;
    if (object == null) return json;
    try {
      ObjectMapper mapper = new ObjectMapper();
      json = mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    return json;
  }
}
