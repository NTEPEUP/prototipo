package shensei.prototipo.servicio_cliente.service;

import shensei.prototipo.servicio_cliente.dto.HistorialAtencionDTO;

import java.util.List;
import java.util.Optional;

public interface HistorialAtencionService {
    List<HistorialAtencionDTO> findAll();
    Optional<HistorialAtencionDTO> findById(Integer id);
    HistorialAtencionDTO create(HistorialAtencionDTO dto);
    Optional<HistorialAtencionDTO> update(Integer id, HistorialAtencionDTO dto);
    void deleteById(Integer id);
    List<HistorialAtencionDTO> findByIdCliente(Integer idCliente);
}

