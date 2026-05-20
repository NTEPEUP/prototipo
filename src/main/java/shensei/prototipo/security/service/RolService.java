package shensei.prototipo.security.service;

import shensei.prototipo.security.dto.RolDTO;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<RolDTO> findAll();
    Optional<RolDTO> findById(Integer id);
    RolDTO create(RolDTO dto);
    Optional<RolDTO> update(Integer id, RolDTO dto);
    void deleteById(Integer id);
    Optional<RolDTO> findByNombre(String nombre);
}

