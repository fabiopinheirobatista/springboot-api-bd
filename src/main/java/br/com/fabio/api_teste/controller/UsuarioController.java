package br.com.fabio.api_teste.controller;

import br.com.fabio.api_teste.controller.dto.UsuarioDTO;
import br.com.fabio.api_teste.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuariosmocados")
    public List<UsuarioDTO> listarUsuariosMocados() {
        return usuarioService.listarUsuariosMocados();
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/listar-usuarios-ordenados-nome")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosOrdenadosPorNome(@RequestParam(defaultValue = "0") int page) {
        List<UsuarioDTO> usuariosOrdenados = usuarioService.listarUsuariosOrdenadosPorNome(page);
        return ResponseEntity.ok(usuariosOrdenados);
    }

    @GetMapping("/buscar-usuarios-por-nome")
    public ResponseEntity<List<UsuarioDTO>> buscarUsuariosPorNome(@RequestParam String nome) {
        List<UsuarioDTO> usuarios = usuarioService.buscarUsuariosPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/cadastrar-usuarios")
    public ResponseEntity <UsuarioDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioDTO);
        return ResponseEntity.ok(usuarioCadastrado);
    }

    @PatchMapping("/alterar-usuarios/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.atualizarUsuario(id, usuarioDTO);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletar-usuario/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }
}