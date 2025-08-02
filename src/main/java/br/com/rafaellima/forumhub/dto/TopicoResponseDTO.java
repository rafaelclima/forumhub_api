package br.com.rafaellima.forumhub.dto;

import java.time.LocalDateTime;

public record TopicoResponseDTO(
            Long id,
            String titulo,
            String status,
            LocalDateTime dataCriacao) {
}
