package shensei.prototipo.productos.service;

import shensei.prototipo.productos.dto.TipoProductoDTO;

import java.util.List;
import java.util.Optional;

public interface TipoProductoService {
    List<TipoProductoDTO> findAll();
    Optional<TipoProductoDTO> findById(Integer id);
    TipoProductoDTO create(TipoProductoDTO dto);
    Optional<TipoProductoDTO> update(Integer id, TipoProductoDTO dto);
    void deleteById(Integer id);
    Optional<TipoProductoDTO> findByNombre(String nombre);
}

