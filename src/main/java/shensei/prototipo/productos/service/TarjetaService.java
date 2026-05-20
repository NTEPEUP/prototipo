package shensei.prototipo.productos.service;

import shensei.prototipo.productos.dto.TarjetaDTO;

import java.util.List;
import java.util.Optional;

public interface TarjetaService {
    List<TarjetaDTO> findAll();
    Optional<TarjetaDTO> findById(Integer id);
    TarjetaDTO create(TarjetaDTO dto);
    Optional<TarjetaDTO> update(Integer id, TarjetaDTO dto);
    void deleteById(Integer id);
    List<TarjetaDTO> findByIdCliente(Integer idCliente);
    Optional<TarjetaDTO> findByNumeroTarjeta(String numeroTarjeta);
}

