package br.com.rafaellima.forumhub.dto;

import br.com.rafaellima.forumhub.entity.Role;

import java.util.Set;

public record LoginResponseDTO(
      String accessToken,
      Long expiresIn
) {
}
