package com.bigdecimal.springbootbackend4nextjs.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(prePostEnabled = false, securedEnabled = true)
public class MethodLevelSecurityConfig {
}
