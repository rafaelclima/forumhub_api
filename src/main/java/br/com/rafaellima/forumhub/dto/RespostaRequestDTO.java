package br.com.rafaellima.forumhub.dto;

import jakarta.validation.constraints.NotBlank;

public record RespostaRequestDTO(
    @NotBlank(message = "O campo 'mensagem' não pode estar vazio.") String mensagem

) {

}
