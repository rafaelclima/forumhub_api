package br.com.rafaellima.forumhub.service;

import br.com.rafaellima.forumhub.dto.RespostaRequestDTO;
import br.com.rafaellima.forumhub.dto.RespostaResponseDTO;
import br.com.rafaellima.forumhub.dto.TopicoDetailDTO;
import br.com.rafaellima.forumhub.dto.TopicoRequestDTO;
import br.com.rafaellima.forumhub.dto.TopicoResponseDTO;
import br.com.rafaellima.forumhub.dto.UpdateRespostaDTO;
import br.com.rafaellima.forumhub.dto.UpdateTopicoDTO;
import br.com.rafaellima.forumhub.entity.Resposta;
import br.com.rafaellima.forumhub.entity.Role;
import br.com.rafaellima.forumhub.entity.StatusTopico;
import br.com.rafaellima.forumhub.entity.Topico;
import br.com.rafaellima.forumhub.entity.Usuario;
import br.com.rafaellima.forumhub.repository.RespostaRepository;
import br.com.rafaellima.forumhub.repository.TopicoRepository;
import br.com.rafaellima.forumhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicoService {

	private final TopicoRepository topicoRepository;
	private final UserRepository userRepository;
	private final RespostaRepository respostaRepository;

	@Transactional
	public TopicoResponseDTO createTopico(TopicoRequestDTO topicoRequest,
			String userId) {

		if (topicoRepository.existsByTituloAndMensagem(topicoRequest.titulo(),
				topicoRequest.mensagem())) {
			throw new IllegalStateException("Tópico já existe");
		}

		Usuario user = userRepository.findById(Long.valueOf(userId))
				.orElseThrow(
						() -> new RuntimeException("Usuário não encontrado"));

		Topico topico = new Topico(topicoRequest, user);
		Topico savedTopico = topicoRepository.save(topico);

		return new TopicoResponseDTO(
				savedTopico.getId(),
				savedTopico.getTitulo(),
				savedTopico.getStatus().name(),
				savedTopico.getDataCriacao());
	}

	public Page<TopicoResponseDTO> getTopico(Pageable pageable) {
		return topicoRepository.findAll(pageable)
				.map(topico -> new TopicoResponseDTO(
						topico.getId(),
						topico.getTitulo(),
						topico.getStatus().name(),
						topico.getDataCriacao()));
	}

	public TopicoDetailDTO getTopicoById(Long id) {
		Topico topico = topicoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
		return new TopicoDetailDTO(
				topico.getId(),
				topico.getTitulo(),
				topico.getMensagem(),
				topico.getStatus().name(),
				topico.getUsuario().getNome(),
				topico.getUsuario().getEmail(),
				topico.getDataCriacao(),
				topico.getRespostas().stream()
						.map(resposta -> new RespostaResponseDTO(
								resposta.getId(),
								resposta.getMensagem(),
								resposta.getUsuario().getNome(),
								resposta.getDataCriacao(),
								resposta.getSolucao()))
						.collect(Collectors.toList()));
	}

	@Transactional
	public TopicoResponseDTO updateTopico(Long id, UpdateTopicoDTO topicoRequest,
			String subject) {

		Topico topico = topicoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

		var usuarioLogado = userRepository.findById(Long.valueOf(subject))
				.orElseThrow(
						() -> new RuntimeException("Usuário não encontrado"));

		var isOwner = usuarioLogado.getId().equals(topico.getUsuario().getId());
		var isAdmin = usuarioLogado.getRoles().contains(Role.ADMIN);

		if (!isAdmin && !isOwner) {
			throw new AccessDeniedException(
					"Apenas administradores ou o dono do tópico podem atualizar tópicos");
		}

		topico.updateTopico(topicoRequest);

		return new TopicoResponseDTO(
				topico.getId(),
				topico.getTitulo(),
				topico.getStatus().name(),
				topico.getDataCriacao());
	}

	@Transactional
	public void deleteTopico(Long id, String subject) {
		var topico = topicoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

		var usuarioLogado = userRepository.findById(Long.valueOf(subject))
				.orElseThrow(
						() -> new RuntimeException("Usuário não encontrado"));

		var isOwner = usuarioLogado.getId().equals(topico.getUsuario().getId());
		var isAdmin = usuarioLogado.getRoles().contains(Role.ADMIN);

		if (!isAdmin && !isOwner) {
			throw new AccessDeniedException(
					"Apenas administradores ou o dono do tópico podem excluir tópicos");
		}

		topicoRepository.delete(topico);
	}

	@Transactional
	public RespostaResponseDTO createResposta(Long id, RespostaRequestDTO respostaRequest,
			String subject) {
		var topico = topicoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

		var topicoFechado = topico.getStatus().equals(StatusTopico.FECHADO);
		var topicoSolucionado = topico.getStatus().equals(StatusTopico.SOLUCIONADO);

		if (topicoFechado || topicoSolucionado) {
			throw new IllegalStateException(
					"Só é possível criar respostas em tópicos não fechados ou não solucionados");
		}

		var usuarioLogado = userRepository.findById(Long.valueOf(subject))
				.orElseThrow(
						() -> new RuntimeException("Usuário não encontrado"));

		Resposta resposta = new Resposta(respostaRequest, topico, usuarioLogado);
		Resposta respostaSaved = respostaRepository.save(resposta);

		if (topico.getStatus() == StatusTopico.NAO_RESPONDIDO) {
			topico.setStatus(StatusTopico.NAO_SOLUCIONADO);
		}

		topico.addResposta(resposta);
		topicoRepository.save(topico);

		return new RespostaResponseDTO(
				respostaSaved.getId(),
				respostaSaved.getMensagem(),
				respostaSaved.getUsuario().getNome(),
				respostaSaved.getDataCriacao(),
				respostaSaved.getSolucao());
	}

	@Transactional
	public RespostaResponseDTO updateResposta(Long id, Long idResposta,
			UpdateRespostaDTO respostaRequest, String subject) {
		var topico = topicoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

		var resposta = respostaRepository.findById(idResposta)
				.orElseThrow(() -> new RuntimeException("Resposta não encontrada"));

		var usuarioLogado = userRepository.findById(Long.valueOf(subject))
				.orElseThrow(
						() -> new RuntimeException("Usuário não encontrado"));

		var isOwner = usuarioLogado.getId().equals(topico.getUsuario().getId());
		var isAdmin = usuarioLogado.getRoles().contains(Role.ADMIN);

		if (!isAdmin && !isOwner) {
			throw new AccessDeniedException(
					"Apenas administradores ou o dono do tópico podem atualizar respostas");
		}

		if (!resposta.getTopico().getId().equals(topico.getId())) {
			throw new AccessDeniedException("A resposta não pertence ao tópico");
		}

		if (respostaRequest.solucao()) {
			topico.getRespostas().stream().filter(Resposta::getSolucao).findFirst()
					.ifPresent(s -> s.setSolucao(false));
			resposta.setSolucao(true);
			topico.setStatus(StatusTopico.SOLUCIONADO);
		} else {
			resposta.setSolucao(false);
			topico.setStatus(StatusTopico.NAO_SOLUCIONADO);
		}

		Resposta savedResposta = respostaRepository.save(resposta);
		topicoRepository.save(topico);

		return new RespostaResponseDTO(
				savedResposta.getId(),
				savedResposta.getMensagem(),
				savedResposta.getUsuario().getNome(),
				savedResposta.getDataCriacao(),
				savedResposta.getSolucao());
	}
}
