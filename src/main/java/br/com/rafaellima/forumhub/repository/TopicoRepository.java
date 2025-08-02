package br.com.rafaellima.forumhub.repository;

import br.com.rafaellima.forumhub.entity.Topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

  boolean existsByTituloAndMensagem(String titulo, String mensagem);
}
