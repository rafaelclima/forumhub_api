package br.com.rafaellima.forumhub.service;

import br.com.rafaellima.forumhub.dto.UserRequestDTO;
import br.com.rafaellima.forumhub.dto.UserResponseDTO;
import br.com.rafaellima.forumhub.entity.Role;
import br.com.rafaellima.forumhub.entity.Usuario;
import br.com.rafaellima.forumhub.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

   public UserResponseDTO registerUser(@Valid UserRequestDTO dto) {

      var email = userRepository.findByEmail(dto.email());
      if (email.isPresent()) {
         throw new BadCredentialsException("Email já cadastrado.");
      }

      var user = new Usuario();
      user.setNome(dto.username());
      user.setEmail(dto.email());
      user.setSenha(passwordEncoder.encode(dto.password()));
      user.setRoles(Set.of(Role.USER));
      var userSaved = userRepository.save(user);
      return new UserResponseDTO(
            userSaved.getId(),
            userSaved.getNome(),
            userSaved.getEmail(),
            userSaved.getRoles()
                  .stream()
                  .map(Role::getNome)
                  .collect(Collectors.toSet())
      );
   }

}
