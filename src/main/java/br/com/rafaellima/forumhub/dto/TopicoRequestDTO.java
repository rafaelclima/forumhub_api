package br.com.rafaellima.forumhub.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicoRequestDTO(
    @NotBlank(message = "O campo 'titulo' não pode estar vazio.") String titulo,
    @NotBlank(message = "O campo 'mensagem' não pode estar vazio.") String mensagem) {
}
