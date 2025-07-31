package br.com.rafaellima.forumhub.dto;

import java.util.Set;

public record UserResponseDTO(
      Long id,
      String username,
      String email,
      Set<String> roles
) {
}
