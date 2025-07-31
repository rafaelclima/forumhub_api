package br.com.rafaellima.forumhub.controller;

import br.com.rafaellima.forumhub.dto.LoginRequestDTO;
import br.com.rafaellima.forumhub.dto.LoginResponseDTO;
import br.com.rafaellima.forumhub.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

   private final LoginService loginService;

   @PostMapping("/login")
   public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {

      LoginResponseDTO token = loginService.autenticate(loginRequest);
      LoginResponseDTO response = new LoginResponseDTO(
            token.accessToken(),
            token.expiresIn()
      );
      return ResponseEntity.ok(response);

   }

}
