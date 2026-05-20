package shensei.prototipo.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shensei.prototipo.productos.entity.Cuenta;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    java.util.List<Cuenta> findByIdCliente(Integer idCliente);
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}

