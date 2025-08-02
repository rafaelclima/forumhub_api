package br.com.rafaellima.forumhub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import br.com.rafaellima.forumhub.dto.RespostaRequestDTO;
import br.com.rafaellima.forumhub.dto.UpdateRespostaDTO;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Resposta")
@Table(name = "respostas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mensagem", nullable = false)
	private String mensagem;

	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(name = "solucao", nullable = false)
	private Boolean solucao = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topico_id", nullable = false)
	private Topico topico;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@Override
	public final boolean equals(Object o) {

		if (this == o)
			return true;
		if (o == null)
			return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o)
				.getHibernateLazyInitializer()
				.getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass =
				this instanceof HibernateProxy ? ((HibernateProxy) this)
						.getHibernateLazyInitializer()
						.getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass)
			return false;
		Resposta resposta = (Resposta) o;
		return getId() != null && Objects.equals(getId(), resposta.getId());
	}

	@Override
	public final int hashCode() {

		return this instanceof HibernateProxy ? ((HibernateProxy) this)
				.getHibernateLazyInitializer()
				.getPersistentClass()
				.hashCode() : getClass().hashCode();
	}

	public Resposta(RespostaRequestDTO respostaRequest, Topico topico,
			Usuario usuarioLogado) {
		this.mensagem = respostaRequest.mensagem();
		this.topico = topico;
		this.usuario = usuarioLogado;
	}

	public void updateResposta(UpdateRespostaDTO respostaRequest) {
		this.solucao = respostaRequest.solucao();
	}
}
