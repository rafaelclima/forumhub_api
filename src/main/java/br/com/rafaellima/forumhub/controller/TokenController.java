package br.com.rafaellima.forumhub.controller;

import br.com.rafaellima.forumhub.dto.LoginRequestDTO;
import br.com.rafaellima.forumhub.dto.LoginResponseDTO;
import br.com.rafaellima.forumhub.entity.Role;
import br.com.rafaellima.forumhub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

   private final JwtEncoder jwtEncoder;

   @Autowired
   private UsuarioRepository usuarioRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   @Value("${api.security.token.expiration-time}")
   private Long expirationTime;

   public TokenController(JwtEncoder jwtEncoder) {

      this.jwtEncoder = jwtEncoder;
   }

   @PostMapping("/api/login")
   public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {

      var usuario = usuarioRepository.findByEmail(loginRequest.email());

      if (usuario.isEmpty() || !usuario.get().isLoginValido(loginRequest, passwordEncoder)) {
         return ResponseEntity.status(401).build(); // Unauthorized
      }

      var scopes = usuario.get().getRoles()
            .stream()
            .map(Role::getNome)
            .collect(Collectors.joining(" "));

      var momentoAtual = Instant.now();

      var claims = JwtClaimsSet.builder()
            .issuer("forumhub")
            .subject(usuario.get().getId().toString())
            .expiresAt(momentoAtual.plusSeconds(expirationTime))
            .issuedAt(momentoAtual)
            .claim("scope", scopes)
            .build();

      var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

      return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expirationTime));


   }

}
