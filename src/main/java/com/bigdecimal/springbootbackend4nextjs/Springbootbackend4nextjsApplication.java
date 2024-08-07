package com.bigdecimal.springbootbackend4nextjs;

import com.bigdecimal.springbootbackend4nextjs.security.config.RSAKeysProperties;
import com.bigdecimal.springbootbackend4nextjs.user.data.AppUserRepository;
import com.bigdecimal.springbootbackend4nextjs.user.model.AppUser;
import com.bigdecimal.springbootbackend4nextjs.user.model.UserRole;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties(RSAKeysProperties.class)
@EnableCaching
@SpringBootApplication
public class Springbootbackend4nextjsApplication {

  public static void main(String[] args) {
    SpringApplication.run(Springbootbackend4nextjsApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(
    AppUserRepository appUserRepository, PasswordEncoder passwordEncoder
  ) {
    return args -> {
      if (appUserRepository.count() == 0) {
        var users = List.of(
          new AppUser("admin", passwordEncoder.encode("password"), UserRole.ADMIN),
          new AppUser("john", passwordEncoder.encode("password"), UserRole.PARTICIPANT),
          new AppUser("peter", passwordEncoder.encode("password"), UserRole.PARTICIPANT)
        );
        appUserRepository.saveAll(users);
      }
    };
  }
}
