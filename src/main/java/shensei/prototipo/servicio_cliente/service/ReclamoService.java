package shensei.prototipo.servicio_cliente.service;

import shensei.prototipo.servicio_cliente.dto.ReclamoDTO;

import java.util.List;
import java.util.Optional;

public interface ReclamoService {
    List<ReclamoDTO> findAll();
    Optional<ReclamoDTO> findById(Integer id);
    ReclamoDTO create(ReclamoDTO dto);
    Optional<ReclamoDTO> update(Integer id, ReclamoDTO dto);
    void deleteById(Integer id);
}

