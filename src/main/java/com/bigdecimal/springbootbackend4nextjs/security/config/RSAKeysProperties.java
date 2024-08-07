package com.bigdecimal.springbootbackend4nextjs.security.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa")
public record RSAKeysProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
