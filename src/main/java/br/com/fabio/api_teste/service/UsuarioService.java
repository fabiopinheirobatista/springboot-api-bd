package br.com.fabio.api_teste.service;

import br.com.fabio.api_teste.controller.repository.UsuarioRepository;
import br.com.fabio.api_teste.controller.dto.UsuarioDTO;
import br.com.fabio.api_teste.controller.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listarUsuariosMocados() {
        return Arrays.asList(
                new UsuarioDTO(1L, "Fabio", "fabio@fabio.com", 45),
                new UsuarioDTO(2L, "Vanessa", "vanessa@vanessa.com", 39),
                new UsuarioDTO(3L, "Giovanna", "giovanna@giovanna.com", 11),
                new UsuarioDTO(4L, "Lívia", "livia@livia.com", 8)
        );
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getIdade()))
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> listarUsuariosOrdenadosPorNome(int page) {
        Pageable pageable = PageRequest.of(page, 4, Sort.by(Sort.Direction.ASC, "nome"));
        List<Usuario> usuarios = usuarioRepository.findAll(pageable).getContent();
        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getIdade()))
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> buscarUsuariosPorNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getIdade()))
                .collect(Collectors.toList());
    }

    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
        if (!StringUtils.hasText(usuarioDTO.getNome()) || !StringUtils.hasText(usuarioDTO.getEmail()) || usuarioDTO.getIdade() == null) {
            throw new IllegalArgumentException("Todos os parâmetros (nome, email, idade) devem ser informados.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setIdade(usuarioDTO.getIdade());

        return new UsuarioDTO(usuarioRepository.save(usuario).getId(), usuario.getNome(), usuario.getEmail(), usuario.getIdade());
    }

    public boolean verificarUsuarioESenha(String email, String senha) {
        return usuarioRepository.existsByEmailAndSenha(email, senha);
    }

    public void atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado."));

        if (StringUtils.hasText(usuarioDTO.getEmail()) && usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail " + usuarioDTO.getEmail() + " já cadastrado.");
        }

        if (StringUtils.hasText(usuarioDTO.getNome())) {
            usuario.setNome(usuarioDTO.getNome());
        }

        if(StringUtils.hasText(usuarioDTO.getEmail())) {
            usuario.setEmail(usuarioDTO.getEmail());
        }

        if(StringUtils.hasText(String.valueOf(usuarioDTO.getIdade()))) {
            usuario.setIdade(usuarioDTO.getIdade());
        }
    }

    public void excluirUsuarioPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário com ID " + id + " não foi encontrado.");
        }

        usuarioRepository.deleteById(id);
    }

    public List<Usuario> ListarUsuarios() {
        return usuarioRepository.findAll();
    }
}