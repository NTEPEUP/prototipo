package shensei.prototipo.security.service;

import shensei.prototipo.security.dto.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<UsuarioDTO> findAll();
    Optional<UsuarioDTO> findById(Integer id);
    UsuarioDTO create(UsuarioDTO dto);
    Optional<UsuarioDTO> update(Integer id, UsuarioDTO dto);
    void deleteById(Integer id);
    Optional<UsuarioDTO> findByUsername(String username);
}

