package br.com.fabio.api_teste.controller.repository;

import br.com.fabio.api_teste.controller.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    List<Usuario> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}