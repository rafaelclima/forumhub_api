package br.com.rafaellima.forumhub.controller;

import br.com.rafaellima.forumhub.dto.TopicoRequestDTO;
import br.com.rafaellima.forumhub.dto.TopicoResponseDTO;
import br.com.rafaellima.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/topico")
@RequiredArgsConstructor
public class TopicoController {

      private final TopicoService topicoService;

      @PostMapping
      @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
      public ResponseEntity<TopicoResponseDTO> createTopico(
                  @RequestBody @Valid TopicoRequestDTO topicoRequest,
                  @AuthenticationPrincipal Jwt jwt) {

            TopicoResponseDTO createdTopico = topicoService.createTopico(topicoRequest,
                        jwt.getSubject());
            URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdTopico.id())
                        .toUri();
            return ResponseEntity
                        .created(location)
                        .body(createdTopico);
      }
}
