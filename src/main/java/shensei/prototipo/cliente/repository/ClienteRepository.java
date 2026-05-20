package shensei.prototipo.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.cliente.entity.Cliente;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCodigoCliente(String codigoCliente);
    Optional<Cliente> findByDpi(String dpi);
}

