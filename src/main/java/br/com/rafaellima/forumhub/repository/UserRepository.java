package br.com.rafaellima.forumhub.repository;

import br.com.rafaellima.forumhub.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
   Optional<Usuario> findByEmail(String email);
}