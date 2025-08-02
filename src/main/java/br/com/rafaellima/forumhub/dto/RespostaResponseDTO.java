package br.com.rafaellima.forumhub.dto;

import java.time.LocalDateTime;

public record RespostaResponseDTO(
    Long id,
    String mensagem,
    String nomeUsuario,
    LocalDateTime dataCriacao,
    Boolean solucao) {

}
