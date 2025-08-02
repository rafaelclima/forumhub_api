package br.com.rafaellima.forumhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rafaellima.forumhub.entity.Resposta;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {

}
