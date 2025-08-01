package br.com.rafaellima.forumhub.controller;

import br.com.rafaellima.forumhub.dto.UserRequestDTO;
import br.com.rafaellima.forumhub.dto.UserResponseDTO;
import br.com.rafaellima.forumhub.service.UserService;
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
public class UserController {


   private final UserService userService;

   @PostMapping
      public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRequestDTO userRequest) {
           UserResponseDTO userResponse = userService.registerUser(userRequest);
           return ResponseEntity
                 .status(HttpStatus.CREATED).body(userResponse);
      }

   @GetMapping
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<Page<UserResponseDTO>> listUsers(Pageable pageable) {
      Page<UserResponseDTO> users = userService.listUsers(pageable);
      return ResponseEntity.ok(users);
   }

}
