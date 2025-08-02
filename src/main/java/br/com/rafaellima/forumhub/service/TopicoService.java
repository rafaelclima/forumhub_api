package br.com.rafaellima.forumhub.service;

import br.com.rafaellima.forumhub.dto.TopicoRequestDTO;
import br.com.rafaellima.forumhub.dto.TopicoResponseDTO;
import br.com.rafaellima.forumhub.entity.Topico;
import br.com.rafaellima.forumhub.entity.Usuario;
import br.com.rafaellima.forumhub.repository.TopicoRepository;
import br.com.rafaellima.forumhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicoService {

   private final TopicoRepository topicoRepository;
   private final UserRepository userRepository;

   @Transactional
   public TopicoResponseDTO createTopico(TopicoRequestDTO topicoRequest, String userId) {

      if (topicoRepository.existsByTituloAndMensagem(topicoRequest.titulo(),
            topicoRequest.mensagem())) {
         throw new IllegalStateException("Tópico já existe");
      }

      Usuario user = userRepository.findById(Long.valueOf(userId))
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

      Topico topico = new Topico(topicoRequest, user);
      Topico savedTopico = topicoRepository.save(topico);

      return new TopicoResponseDTO(
            savedTopico.getId(),
            savedTopico.getTitulo(),
            savedTopico.getStatus().name(),
            savedTopico.getDataCriacao());
   }
}
