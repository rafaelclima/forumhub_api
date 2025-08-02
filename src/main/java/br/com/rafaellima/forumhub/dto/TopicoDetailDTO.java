package br.com.rafaellima.forumhub.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TopicoDetailDTO(
      Long id,
      String titulo,
      String mensagem,
      String status,
      String nomeUsuario,
      String emailUsuario,
      LocalDateTime dataCriacao,
      List<RespostaResponseDTO> respostas
) {
}
