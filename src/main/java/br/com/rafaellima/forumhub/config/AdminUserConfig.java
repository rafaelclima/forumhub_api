package br.com.rafaellima.forumhub.config;

import br.com.rafaellima.forumhub.entity.Role;
import br.com.rafaellima.forumhub.entity.Usuario;
import br.com.rafaellima.forumhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    public CommandLineRunner initAdminUser() {
        return args -> {

            userRepository
                  .findByEmail("admin@forumhub.com").ifPresentOrElse(
                user -> System.out.println("Usuário admin já existe." + " ROlE: " + user.getRoles()),
                () -> {
                    Usuario adminUser = new Usuario();
                    adminUser.setNome("Admin User");
                    adminUser.setEmail("admin@forumhub.com");
                    adminUser.setSenha(passwordEncoder.encode("123456"));
                    adminUser.setRoles(Set.of(Role.ADMIN));
                    userRepository.save(adminUser);
                    System.out.println("Usuário admin criado com sucesso!");
                }
            );
        };
    }
}
