package br.com.rafaellima.forumhub.entity;

import br.com.rafaellima.forumhub.dto.LoginRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Set;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;

   @Column(name = "nome", nullable = false)
   String nome;

   @Column(name = "email", nullable = false, unique = true)
   String email;

   @Column(name = "senha", nullable = false)
   String senha;

   @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
   @CollectionTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"))
   @Enumerated(EnumType.STRING)
   @Column(name = "role", nullable = false)
   private Set<Role> roles;

   public boolean isLoginValido(LoginRequestDTO loginRequest, PasswordEncoder passwordEncoder) {
      return passwordEncoder.matches(loginRequest.senha(), this.senha);
   }

   @Override
   public final boolean equals(Object o) {

      if (this == o) return true;
      if (o == null) return false;
      Class<?> oEffectiveClass = o instanceof HibernateProxy ?
            ((HibernateProxy) o)
                  .getHibernateLazyInitializer()
                  .getPersistentClass() :
            o.getClass();
      Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
            ((HibernateProxy) this)
                  .getHibernateLazyInitializer()
                  .getPersistentClass() :
            this.getClass();
      if (thisEffectiveClass != oEffectiveClass) return false;
      Usuario usuario = (Usuario) o;
      return id != null && Objects.equals(id, usuario.id);
   }

   @Override
   public final int hashCode() {

      return this instanceof HibernateProxy ?
            ((HibernateProxy) this)
                  .getHibernateLazyInitializer()
                  .getPersistentClass()
                  .hashCode() :
            getClass().hashCode();
   }
}
