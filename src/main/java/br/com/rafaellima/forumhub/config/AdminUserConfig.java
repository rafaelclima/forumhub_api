package br.com.rafaellima.forumhub.config;

import br.com.rafaellima.forumhub.entity.Role;
import br.com.rafaellima.forumhub.entity.Usuario;
import br.com.rafaellima.forumhub.repository.RoleRepository;
import br.com.rafaellima.forumhub.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminUserConfig {

    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;

       this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    public CommandLineRunner initAdminUser() {
        return args -> {
            String adminRoleName = "ADMIN";

            Role adminRole = roleRepository.findByNome(adminRoleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setNome(adminRoleName);
                        return roleRepository.save(newRole);
                    });

            usuarioRepository.findByEmail("admin@forumhub.com").ifPresentOrElse(
                user -> System.out.println("Usuário admin já existe."),
                () -> {
                    Usuario adminUser = new Usuario();
                    adminUser.setNome("Admin User");
                    adminUser.setEmail("admin@forumhub.com");
                    adminUser.setSenha(passwordEncoder.encode("123456"));
                    adminUser.setRoles(Set.of(adminRole));
                    usuarioRepository.save(adminUser);
                    System.out.println("Usuário admin criado com sucesso!");
                }
            );
        };
    }
}
