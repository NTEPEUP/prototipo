package shensei.prototipo.productos.service;

import shensei.prototipo.productos.dto.PrestamoDTO;

import java.util.List;
import java.util.Optional;

public interface PrestamoService {
    List<PrestamoDTO> findAll();
    Optional<PrestamoDTO> findById(Integer id);
    PrestamoDTO create(PrestamoDTO dto);
    Optional<PrestamoDTO> update(Integer id, PrestamoDTO dto);
    void deleteById(Integer id);
    List<PrestamoDTO> findByIdCliente(Integer idCliente);
}


