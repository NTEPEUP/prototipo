package shensei.prototipo.security.service;

import shensei.prototipo.security.dto.UsuarioRolDTO;

import java.util.List;

public interface UsuarioRolService {
    List<UsuarioRolDTO> findAll();
    UsuarioRolDTO create(UsuarioRolDTO dto);
    void delete(Integer idUsuario, Integer idRol);
}

