package com.bigdecimal.springbootbackend4nextjs.security.config;

import com.bigdecimal.springbootbackend4nextjs.auth.filter.AuthenticationFilter;
import com.bigdecimal.springbootbackend4nextjs.auth.filter.LogoutCheckFilter;
import com.bigdecimal.springbootbackend4nextjs.security.service.JWTService;
import com.bigdecimal.springbootbackend4nextjs.user.data.AppUserRepository;
import com.bigdecimal.springbootbackend4nextjs.user.model.AppUser;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
// import java.security.KeyPair;
// import java.security.KeyPairGenerator;
// import java.security.interfaces.RSAPrivateKey;
// import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class GlobalSecurityConfig {

  private final AppUserRepository appUserRepository;
  private final LogoutCheckFilter logoutCheckFilter;
  private final RSAKeysProperties rsaKeysProperties;
  // private final KeyPair keyPair;
  // private final RSAPublicKey publicKey;
  // private final RSAPrivateKey privateKey;

  public GlobalSecurityConfig(AppUserRepository appUserRepository, LogoutCheckFilter logoutCheckFilter, RSAKeysProperties rsaKeysProperties) {
    this.appUserRepository = appUserRepository;
    this.logoutCheckFilter = logoutCheckFilter;
    // this.keyPair = generateRsaKey();
    // this.publicKey = (RSAPublicKey) keyPair.getPublic();
    // this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    this.rsaKeysProperties = rsaKeysProperties;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity security)
    throws Exception {
    security.csrf(customizer -> customizer.disable());
    security.sessionManagement(customizer ->
      customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    );
    AuthenticationFilter authenticationFilter = new AuthenticationFilter(
      authenticationManager(),
      jwtService(jwtEncoder())
    );
    authenticationFilter.setFilterProcessesUrl("/auth/signin");
    security.addFilterAt(
      authenticationFilter,
      UsernamePasswordAuthenticationFilter.class
    );
    security.addFilterBefore(logoutCheckFilter, ExceptionTranslationFilter.class);
    security.oauth2ResourceServer(configurer ->
      configurer.jwt(Customizer.withDefaults())
    );
    security.authorizeHttpRequests(customizer -> {
      customizer
        .requestMatchers(HttpMethod.POST, "/users")
        .permitAll();
      customizer.anyRequest().authenticated();
    });
    return security.build();
  }

  @Bean
  AuthenticationManager authenticationManager() {
    return new ProviderManager(Arrays.asList(authenticationProvider()));
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService());
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow();
        return new User(
          user.getUsername(),
          user.getPassword(),
          List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
      }
    };
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Primary
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeysProperties.publicKey()).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    RSAKey rsaKey = new RSAKey.Builder(rsaKeysProperties.publicKey())
      .privateKey(rsaKeysProperties.privateKey())
      .keyID(UUID.randomUUID().toString())
      .build();
    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(
      new JWKSet(rsaKey)
    );
    return new NimbusJwtEncoder(jwkSource);
  }

  @Bean
  JWTService jwtService(JwtEncoder jwtEncoder) {
    return new JWTService(jwtEncoder);
  }

  // private static KeyPair generateRsaKey() {
  //   KeyPair keyPair;
  //   try {
  //     KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
  //     keyPairGenerator.initialize(2048);
  //     keyPair = keyPairGenerator.generateKeyPair();
  //   } catch (Exception ex) {
  //     throw new IllegalStateException(ex);
  //   }
  //   return keyPair;
  // }
}
