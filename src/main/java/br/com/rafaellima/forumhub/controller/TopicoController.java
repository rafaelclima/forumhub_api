package br.com.rafaellima.forumhub.controller;

import br.com.rafaellima.forumhub.dto.RespostaRequestDTO;
import br.com.rafaellima.forumhub.dto.RespostaResponseDTO;
import br.com.rafaellima.forumhub.dto.TopicoDetailDTO;
import br.com.rafaellima.forumhub.dto.TopicoRequestDTO;
import br.com.rafaellima.forumhub.dto.TopicoResponseDTO;
import br.com.rafaellima.forumhub.dto.UpdateRespostaDTO;
import br.com.rafaellima.forumhub.dto.UpdateTopicoDTO;
import br.com.rafaellima.forumhub.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/topico")
@RequiredArgsConstructor
@Tag(name = "Tópico", description = "Endpoint para gerenciamento de tópicos.")
@SecurityRequirement(name = "Bearer Authentication")
public class TopicoController {

	private final TopicoService topicoService;

	@PostMapping
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@Operation(summary = "Cria um novo tópico.", description = "Endpoint para criar um novo tópico com o título, mensagem e ID da categoria. Retorna o tópico criado com seu ID.")
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

	@GetMapping
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@Operation(summary = "Lista todos os tópicos.", description = "Endpoint para listar todos os tópicos com paginação. Retorna uma página de tópicos.")
	public ResponseEntity<Page<TopicoResponseDTO>> getTopico(Pageable pageable) {
		return ResponseEntity.ok(topicoService.getTopico(pageable));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@Operation(summary = "Obtém um tópico por ID.", description = "Endpoint para obter um tópico específico por seu ID. Retorna os detalhes do tópico.")
	public ResponseEntity<TopicoDetailDTO> getTopicoById(@PathVariable Long id) {
		return ResponseEntity.ok(topicoService.getTopicoById(id));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@Operation(summary = "Atualiza um tópico por ID.", description = "Endpoint para atualizar um tópico específico por seu ID. Retorna o tópico atualizado.")
	public ResponseEntity<TopicoResponseDTO> updateTopico(@PathVariable Long id,
			@RequestBody UpdateTopicoDTO topicoRequest,
			@AuthenticationPrincipal Jwt jwt) {
		return ResponseEntity.ok(
				topicoService.updateTopico(id, topicoRequest, jwt.getSubject()));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@Operation(summary = "Exclui um tópico por ID.", description = "Endpoint para excluir um tópico específico por seu ID. Não retorna conteúdo.")
	public ResponseEntity<Void> deleteTopico(@PathVariable Long id,
			@AuthenticationPrincipal Jwt jwt) {
		topicoService.deleteTopico(id, jwt.getSubject());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/resposta")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@Operation(summary = "Cria uma resposta para um tópico.", description = "Endpoint para criar uma resposta para um tópico específico por seu ID. Retorna a resposta criada com seu ID.")
	public ResponseEntity<RespostaResponseDTO> createResposta(@PathVariable Long id,
			@RequestBody @Valid RespostaRequestDTO respostaRequest,
			@AuthenticationPrincipal Jwt jwt) {
		RespostaResponseDTO createdResposta = topicoService.createResposta(id,
				respostaRequest, jwt.getSubject());
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdResposta.id())
				.toUri();
		return ResponseEntity.created(location).body(createdResposta);
	}

	@PutMapping("/{id}/resposta/{idResposta}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@Operation(summary = "Atualiza uma resposta por ID.", description = "Endpoint para atualizar uma resposta específica por seu ID. Retorna a resposta atualizada.")
	public ResponseEntity<RespostaResponseDTO> updateResposta(@PathVariable Long id,
			@PathVariable Long idResposta,
			@RequestBody UpdateRespostaDTO respostaRequest,
			@AuthenticationPrincipal Jwt jwt) {
		return ResponseEntity.ok(
				topicoService.updateResposta(id, idResposta, respostaRequest,
						jwt.getSubject()));
	}

}
