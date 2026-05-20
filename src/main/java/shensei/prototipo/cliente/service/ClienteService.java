package shensei.prototipo.cliente.service;

import shensei.prototipo.cliente.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> findAll();
    Optional<Cliente> findById(Integer id);
    Cliente save(Cliente cliente);
    void deleteById(Integer id);
    Optional<Cliente> findByCodigoCliente(String codigoCliente);
    Optional<Cliente> findByDpi(String dpi);
}

