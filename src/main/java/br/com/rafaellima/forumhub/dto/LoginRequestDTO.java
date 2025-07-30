package br.com.rafaellima.forumhub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDTO(
      @NotBlank(message = "O campo 'email' não pode estar vazio.")
      @Email(message = "O campo 'email' deve ser um endereço de email válido.")
      String email,

      @NotBlank(message = "O campo 'senha' não pode estar vazio.")
      @Pattern(
            message = "A senha deve ter no mínimo 3 dígitos e no máximo 6 dígitos.",
            regexp = "^[0-9]{3,6}$"
      )
      String senha
) {
}
