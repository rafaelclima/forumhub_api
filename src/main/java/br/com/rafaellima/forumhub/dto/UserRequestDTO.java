package br.com.rafaellima.forumhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record UserRequestDTO(
         @NotBlank(message = "Username is required")
         String username,
         @NotBlank(message = "Email is required")
         @Email(message = "Email must be a valid email address")
         String email,
         @NotBlank(message = "Password is required")
         @Pattern(
                   message = "Password must contain at least one letter, one number, and be at least 4 characters long",
                   regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{4,}$"
         )
         String password
) {
}
