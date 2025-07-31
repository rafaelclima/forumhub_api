package br.com.rafaellima.forumhub.service;

import br.com.rafaellima.forumhub.dto.LoginRequestDTO;
import br.com.rafaellima.forumhub.dto.LoginResponseDTO;
import br.com.rafaellima.forumhub.entity.Role;
import br.com.rafaellima.forumhub.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

   private final UserRepository userRepository;

   private final PasswordEncoder passwordEncoder;

   private final JwtEncoder jwtEncoder;

   @Value("${api.security.token.expiration-time}")
   private Long expirationTime;

   public LoginResponseDTO autenticate(@Valid LoginRequestDTO dto) {

      var usuario = userRepository
            .findByEmail(dto.email())
            .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos."));

      if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
         throw new BadCredentialsException("Email ou senha inválidos.");
      }

      var scopes = usuario
            .getRoles()
            .stream()
            .map(Role::getNome)
            .collect(Collectors.joining(" "));

      var momentoAtual = Instant.now();

      var claims = JwtClaimsSet
            .builder()
            .issuer("forumhub")
            .subject(usuario
                  .getId()
                  .toString())
            .expiresAt(momentoAtual.plusSeconds(expirationTime))
            .issuedAt(momentoAtual)
            .claim("scope", scopes)
            .build();

      var jwtValue = jwtEncoder
            .encode(JwtEncoderParameters.from(claims))
            .getTokenValue();
      return new LoginResponseDTO(jwtValue, expirationTime);
   }
}
