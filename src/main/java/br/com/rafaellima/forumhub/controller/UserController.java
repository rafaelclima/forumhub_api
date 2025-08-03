package br.com.rafaellima.forumhub.controller;

import br.com.rafaellima.forumhub.dto.UserRequestDTO;
import br.com.rafaellima.forumhub.dto.UserResponseDTO;
import br.com.rafaellima.forumhub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Controller para gerenciamento de usuários")
public class UserController {

	private final UserService userService;

	@PostMapping
	@Operation(summary = "Registra um novo usuário.", description = "Endpoint para registrar um novo usuário com os dados fornecidos. Retorna o usuário registrado com seu ID.")
	public ResponseEntity<UserResponseDTO>
			registerUser(@RequestBody @Valid UserRequestDTO userRequest) {
		UserResponseDTO userResponse = userService.registerUser(userRequest);
		return ResponseEntity
				.status(HttpStatus.CREATED).body(userResponse);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Lista todos os usuários.", description = "Endpoint para listar todos os usuários cadastrados no sistema. Retorna uma página com a lista de usuários.")
	public ResponseEntity<Page<UserResponseDTO>> listUsers(Pageable pageable) {
		Page<UserResponseDTO> users = userService.listUsers(pageable);
		return ResponseEntity.ok(users);
	}

}
