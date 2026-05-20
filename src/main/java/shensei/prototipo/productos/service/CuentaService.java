package shensei.prototipo.productos.service;

import shensei.prototipo.productos.dto.CuentaDTO;

import java.util.List;
import java.util.Optional;

public interface CuentaService {
    List<CuentaDTO> findAll();
    Optional<CuentaDTO> findById(Integer id);
    CuentaDTO create(CuentaDTO dto);
    Optional<CuentaDTO> update(Integer id, CuentaDTO dto);
    void deleteById(Integer id);
    Optional<CuentaDTO> findByNumeroCuenta(String numeroCuenta);
}

