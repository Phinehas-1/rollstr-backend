package com.bigdecimal.springbootbackend4nextjs.auth.filter;

import com.bigdecimal.springbootbackend4nextjs.security.config.RSAKeysProperties;
import com.bigdecimal.springbootbackend4nextjs.shared.exception.InvalidOperationException;
// import com.bigdecimal.springbootbackend4nextjs.user.data.AppUserRepository;
import com.bigdecimal.springbootbackend4nextjs.user.model.AppUser;
import com.bigdecimal.springbootbackend4nextjs.user.model.UserRole;
import com.bigdecimal.springbootbackend4nextjs.user.service.UserManagementService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@RequiredArgsConstructor
@Slf4j
public class LogoutCheckFilter extends OncePerRequestFilter {

  private final UserManagementService userManagementService;
  private final RSAKeysProperties rsaKeysProperties;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;




  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    log.debug(
      "Will throw an exception if user is marked as logged out in database."
    );
    String username = getUsernameFromJwt(getJwtFromRequestHeader(request));
    log.debug("Username from JWT is: {}", username);
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    if (authentication != null && username != null) {
      AppUser user = userManagementService.findUserByUsername(username);
      log.debug("User role is: {}", user.getRole());
      try {
        if (UserRole.PARTICIPANT.equals(user.getRole())) {
          throw new InvalidOperationException(
            "Niooggaa, you've been logged out!"
          );
        }
      } catch (Exception e) {
        resolver.resolveException(request, response, null, e);
      }
    }

    filterChain.doFilter(request, response);
    return;
  }



  private JwtDecoder secondaryJwtDecoder() {
    return NimbusJwtDecoder
      .withPublicKey(rsaKeysProperties.publicKey())
      .build();
  }




  private String getJwtFromRequestHeader(HttpServletRequest request) {
    String jwt = null;
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      jwt = header.substring(7);
    }
    return jwt;
  }



  
  private String getUsernameFromJwt(String jwt) {
    String username = null;
    if (jwt != null) {
      JwtDecoder jwtDecoder = secondaryJwtDecoder();
      username = jwtDecoder.decode(jwt).getSubject();
    }
    return username;
  }
}
