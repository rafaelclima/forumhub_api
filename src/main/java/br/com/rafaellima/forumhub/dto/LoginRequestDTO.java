package br.com.rafaellima.forumhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
      @NotBlank(message = "O campo 'email' não pode estar vazio.")
      @Email(message = "O campo 'email' deve ser um endereço de email válido.")
      String email,

      @NotBlank(message = "O campo 'senha' não pode estar vazio.")
      String senha
) {
}
