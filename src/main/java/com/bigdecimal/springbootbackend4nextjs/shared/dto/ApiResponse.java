package com.bigdecimal.springbootbackend4nextjs.shared.dto;

public record ApiResponse(Boolean success, Object data, String message) {
}
